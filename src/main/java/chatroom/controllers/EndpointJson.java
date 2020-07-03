package chatroom.controllers;
import java.io.IOException;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertOneResult;
import chatroom.models.*;

@ServerEndpoint(value = "/hellojson", encoders = { ResponseEncoder.class }, decoders = { UserDecoder.class } )
public class EndpointJson {
	@OnMessage
	public void onMessage(User user, Session session) throws IOException, EncodeException {
	    System.out.println(user.getUsername() + " | " + user.getPassword());
	    // Echo the received message back to the client
	    MongoClient client = MongoClients.create("mongodb://b1corp:1@45.124.94.20:27017");
		MongoDatabase db = client.getDatabase("login_test");
		MongoCollection<Document> col = db.getCollection("users");
		String username = user.getUsername();
		FindIterable<Document> findIterable = col.find(Filters.eq("username", username));
		if(findIterable.first() != null) {
			return;
		}
		else {
			Document doc = Document.parse(user.getRaw());
			InsertOneResult result = col.insertOne(doc);
			System.out.println(result.toString());
		}
		Response res = new Response();
		res.setStatus("okok");
		res.setMessage("ok ban oi");
		try {
			session.getBasicRemote().sendObject(res);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (EncodeException e) {
			e.printStackTrace();
		}
	}

	  

	  @OnOpen
	  public void onOpen() {
		System.out.println("Client connected");
	    
	    
	  }

	  @OnClose
	  public void onClose() {
	    System.out.println("Connection closed");
	  }
}
