package chatroom.controllers;

import java.io.IOException;
import java.util.logging.Logger;

import javax.json.JsonObject;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import chatroom.models.*;

@ServerEndpoint(value = "/phong-chat", encoders = { ResponseEncoder.class, MessageEncoder.class }, decoders = {
		EventDecoder.class })
public class PhongChat {
	private Logger logger = Logger.getLogger("PhongChatLogger");

	@OnMessage
	public void onMessage(Event event, Session session) throws IOException, EncodeException {
		try (MongoClient client = MongoClients.create("mongodb://b1corp:1@45.124.94.20:27017")) {
			if (event.getAction().equals("login")) {
				MongoDatabase db = client.getDatabase("login_test");
				MongoCollection<Document> userCollection = db.getCollection("users");
				JsonObject eventPayload = event.getPayload();
				Response loginResponse = DangNhap.dangNhap(userCollection, eventPayload);
				System.out.println(loginResponse);
				session.getBasicRemote().sendObject(loginResponse);
			} else if (event.getAction().equals("send_message")) {
				logger.info("recieved message");
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			logger.info(exception.getMessage());
		}
	}

	@OnOpen
	public void onOpen() {
		logger.info("Client connected");
	}

	@OnClose
	public void onClose() {
		logger.info("Connection closed");
	}
}