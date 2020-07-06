package chatroom.controllers;

import java.util.Collection;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.Session;

import chatroom.models.Event;
import chatroom.models.Message;

public class MessageSender {
    private MessageSender(){
        // create messsage sender
    }

	public static void broadcast(Message message, Collection<Session> sessions) {
		for (Session session : sessions) {
			Event event = new Event();
			event.setAction("send_message");
			JsonObject payload = Json.createObjectBuilder().add("message", message.toJson()).build();
			event.setPayload(payload);
			try {
				session.getBasicRemote().sendObject(event);
			} catch (Exception e) {
				// logging here and continue
			}
		}
	}
}