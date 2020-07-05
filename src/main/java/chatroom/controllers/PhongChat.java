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


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import chatroom.models.*;

@ServerEndpoint(value = "/phong-chat", encoders = { ResponseEncoder.class, MessageEncoder.class }, decoders = {
		EventDecoder.class })
public class PhongChat {
	private Logger logger = Logger.getLogger("PhongChatLogger");

	@OnMessage
	public void onMessage(Event event, Session session) throws IOException, EncodeException {
		try (MongoClient client = MongoClients.create("mongodb://b1corp:1@45.124.94.20:27017")) {
			MongoDatabase db = client.getDatabase("login_test");
			if (event.getAction().equals("login")) {
				String usersCollectionName = "users";
				JsonObject eventPayload = event.getPayload();
				Response response = DangNhap.dangNhap(db, eventPayload, usersCollectionName);
				session.getBasicRemote().sendObject(response);
			} else if (event.getAction().equals("send_message")) {
				// TODO: Logic gui tin nhan
			}
		} catch (Exception exception) {
			logger.info(exception.toString());
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