package chatroom.DangKy;
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

@ServerEndpoint(value = "/dang-ky", encoders = { ResponseEncoder.class }, decoders = { UserDecoder.class } )
public class DangKy {
	@OnMessage
	public void onMessage(User user, Session session) throws IOException, EncodeException {
	 
		MongoClient mongoClient = MongoClients.create("mongodb://b1corp:1@45.124.94.20:27017");
		MongoDatabase db = mongoClient.getDatabase("login_test");
	    MongoCollection<Document> col = db.getCollection("users");
		
	    String username = user.getUsername();
		FindIterable<Document> findUsername = col.find(Filters.eq("username", username));
		Response res = new Response();
		if(findUsername.first() != null) {
			res.setStatus("error");
			res.setCode(1);
			res.setMessage("Tên tài khoản đã được sử dụng");
			try {
				session.getBasicRemote().sendObject(res);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (EncodeException e) {
				e.printStackTrace();
			}
			return;
		}
		else {
			res.setStatus("success");
			res.setCode(0);
			res.setMessage("Đăng ký thành công!");
			try {
				session.getBasicRemote().sendObject(res);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (EncodeException e) {
				e.printStackTrace();
			}
			Document doc = new Document();
			doc.append("username", user.getUsername());
			doc.append("password", user.getPassword());
			doc.append("token", user.getToken());
			doc.append("createAt", user.getDatetime());
			InsertOneResult result = col.insertOne(doc);
			System.out.println(result.toString());
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
