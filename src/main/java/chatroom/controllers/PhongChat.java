package chatroom.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;

import javax.json.JsonObject;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import chatroom.models.*;

@ServerEndpoint(value = "/phong-chat", encoders = { ResponseEncoder.class, MessageEncoder.class,
		EventEncoder.class }, decoders = { EventDecoder.class })
public class PhongChat {
	private Logger logger = Logger.getLogger("PhongChatLogger");
	private static HashMap<String, Session> onlineUsers = new HashMap<>();

	@OnMessage
	public void onMessage(Event event, Session session) throws IOException, EncodeException {
		Response response = new Response();
		try {
			Database userDB = new Database("users");
			if (event.getAction().equals("login")) {
				JsonObject eventPayload = event.getPayload();
				DangNhap.dangNhap(session, userDB, eventPayload);
			} else if (event.getAction().equals("send_message")) {
				// TODO: Logic gui tin nhan
			} else if (event.getAction().equals("join_chat")) {
				JsonObject eventPayload = event.getPayload();
				if (eventPayload != null) {
					String token = eventPayload.getString("token");
					response = JoinChat.join(userDB, onlineUsers, token, session);
				} else {
					response.setMessage("Invalid payload");
				}
			}
		} catch (Exception exception) {
			logger.info(exception.toString());
			response.setMessage("An error occurred while parse payload");
		}
		session.getBasicRemote().sendObject(response);
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