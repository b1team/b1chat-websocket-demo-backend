package chatroom.controllers;

import java.io.IOException;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonObject;
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
import chatroom.models.*;
import chatroom.utils.*;

@ServerEndpoint(value = "/phong-chat", encoders = { ResponseEncoder.class, MessageEncoder.class }, decoders = {
		EventDecoder.class })
public class PhongChat {
	Logger logger = Logger.getLogger("PhongChatLog");

	@OnMessage
	public void onMessage(Event event, Session session) throws IOException, EncodeException {
		try (MongoClient client = MongoClients.create("mongodb://b1corp:1@45.124.94.20:27017")) {
			if (event.getAction().equals("login")) {
				MongoDatabase db = client.getDatabase("login_test");
				MongoCollection<Document> col = db.getCollection("users");
				JsonObject payload = event.getPayload();
				String username = payload.getString("username");
				String md5Password = Hash.getMd5(payload.getString("password"));
				FindIterable<Document> result = col
						.find(Filters.and(Filters.eq("username", username), Filters.eq("password", md5Password)));
				
				Response response = new Response();
				String tokenKeyName = "token";
				Document doc = result.first();
				if (doc != null) {
					String token = doc.getString(tokenKeyName);
					if (token != null) {
						response.setStatus("success");
						response.setCode(0);
						response.setMessage("Đăng nhập thành công");
						JsonObject responsePayload = Json.createObjectBuilder().add("token", token).build();
						response.setPayload(responsePayload);
					} else {
						response.setStatus("failed");
						response.setCode(1);
						response.setMessage("Khong tim thay token");
					}
				} else {
					response.setStatus("failed");
					response.setCode(1);
					response.setMessage("Sai tên đăng nhập hoặc mật khẩu");
				}
				
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