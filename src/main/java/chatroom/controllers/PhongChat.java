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


import chatroom.Config.DataBase;
import chatroom.models.*;
import chatroom.utils.*;

@ServerEndpoint(value = "/phong-chat", encoders = { ResponseEncoder.class, MessageEncoder.class }, decoders = {
		EventDecoder.class })
public class PhongChat {
	@OnMessage
	public void onMessage(Event event, Session session) throws IOException, EncodeException {
		DataBase db = new DataBase();
		if (event.getAction().equals("login")) {
			db.init();;
			JsonObject payload = event.getPayload();
			String username = payload.getString("username");
			String md5Password = Hash.getMd5(payload.getString("password"));
			FindIterable<Document> result = db.findDouble("username", username, "password", md5Password);
			if(result.first() != null) {
				ResponseSender.response(session, "success", 0, "Đăng nhập thành công");
				// Tra token o day
			}
			else {
				ResponseSender.response(session, "failed", 1, "Sai tên đăng nhập hoặc mật khẩu");
			}
		} else if (event.getAction().equals("send_message")) {

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