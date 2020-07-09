package chatroom.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;

import org.bson.Document;

import chatroom.models.*;

@ServerEndpoint(value = "/phong-chat", encoders = { EventEncoder.class }, decoders = { EventDecoder.class })
public class PhongChat {
	private Logger logger = Logger.getLogger("PhongChatLogger");
	private static HashMap<String, Session> onlineUsers = new HashMap<>();

	@OnMessage
	public void onMessage(Event event, Session session) throws IOException, EncodeException {
		Response response = new Response();
		Response lastestMessageResponse = new Response();
		try {
			Database userDB = new Database("users");
			Database MessageDB = new Database("messages");
			if (event.getAction().equals("login")) { // event
				JsonObject eventPayload = event.getPayload();
				response = DangNhap.dangNhap(userDB, eventPayload);
			} else if (event.getAction().equals("send_message")) {
				JsonObject eventPayload = event.getPayload();
				String token = eventPayload.getString("token");
				String content = eventPayload.getString("content");
				response = Chat.send(userDB, MessageDB, onlineUsers, token, content);
			} else if (event.getAction().equals("join_chat")) {
				JsonObject eventPayload = event.getPayload();
				if (eventPayload != null) {
					String token = eventPayload.getString("token");
					if(onlineUsers.containsKey(token)){
						response.setType("join_chat");
						response.setMessage("join chat is already");
					}else{
						response = JoinChat.join(userDB, onlineUsers, token, session);
					}
				} else {
					response.setMessage("Invalid payload");
				}
				if(response.getCode() == 0){
					JsonArrayBuilder messagesBuilder = Json.createArrayBuilder();
					FindIterable<Document> cursor = MessageDB.getCollection().find().sort(new BasicDBObject("createAt", -1))
							.limit(50);
					MongoCursor<Document> iterator = cursor.iterator();
					Integer num_of_messages = 0;
					while(iterator.hasNext()){
						num_of_messages += 1;
						Document doc = iterator.next();
						messagesBuilder = messagesBuilder.add(Json.createObjectBuilder()
												.add("username", doc.getString("username"))
												.add("content", doc.getString("content"))
												.add("createAt", doc.getString("createAt"))
												.build());
					}
					JsonObject lastestMessagePayload = Json.createObjectBuilder()
														   .add("count", num_of_messages)
														   .add("messages", messagesBuilder.build())
														   .build();
					lastestMessageResponse.setPayload(lastestMessagePayload);
					lastestMessageResponse.setCode(0);
					lastestMessageResponse.setType("lastest_messages");
				}
			}
		} catch (Exception exception) {
			logger.info(exception.toString());
			response.setMessage("An error occurred while parse payload");
		}
		EventSender.send(session, Event.createResponseEvent(response));
		if(lastestMessageResponse.getCode() == 0){
			EventSender.send(session, Event.createResponseEvent(lastestMessageResponse));
		}
	}

	@OnOpen
	public void onOpen() {
		logger.info("Client connected");
	}

	@OnClose
	public void onClose(Session closedSession) {
		String token = "";
		for (Map.Entry<String, Session> entry : onlineUsers.entrySet()) {
			Session onlineSession = entry.getValue();
			if (onlineSession.getId().equals(closedSession.getId())) {
				token = entry.getKey();
			}
		}

		if (!token.equals("")) {
			Message message = new Message();
			String username = "Someone";
			Database userDB = new Database("users");
			FindIterable<Document> result = userDB.findOne("token", token);
			if(result.first() != null) {
				Document doc = result.first();
				username = doc.getString("username");
			}
			onlineUsers.remove(token);
			message.setContent(String.format("%s đã rời khỏi nhóm chat", username));
			message.setUsername("System");
					
			//broadcast
			MessageSender.broadcast(message, onlineUsers.values());
			logger.info(String.format("%s disconnected", username));
		}

	}

}