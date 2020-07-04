package chatroom.controllers;

import java.io.IOException;

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
				if (result.first() != null) {
					response.setStatus("success");
					response.setCode(0);
					response.setMessage("Đăng nhập thành công");
					// Tra token o day
				} else {
					response.setStatus("failed");
					response.setCode(1);
					response.setMessage("Sai tên đăng nhập hoặc mật khẩu");
				}
				try {
					session.getBasicRemote().sendObject(response);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (EncodeException e) {
					e.printStackTrace();
				}
			} else if (event.getAction().equals("send_message")) {

			}
		} catch (Exception exception) {
			System.out.println(exception);
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