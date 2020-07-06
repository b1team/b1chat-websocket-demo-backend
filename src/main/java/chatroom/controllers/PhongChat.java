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

import chatroom.models.*;

@ServerEndpoint(value = "/phong-chat", encoders = { ResponseEncoder.class, MessageEncoder.class }, decoders = {
		EventDecoder.class })
public class PhongChat {
	private Logger logger = Logger.getLogger("PhongChatLogger");

	@OnMessage
	public void onMessage(Event event, Session session) throws IOException, EncodeException {
		try {
			Database db = new Database("users");
			if (event.getAction().equals("login")) {
				JsonObject eventPayload = event.getPayload();
				DangNhap.dangNhap(session, db, eventPayload);
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