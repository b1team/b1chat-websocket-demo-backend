package chatroom.controllers;

import java.util.Collection;

import javax.websocket.Session;

import chatroom.models.Event;
import chatroom.models.EventSender;
import chatroom.models.Message;

public class MessageSender {
    private MessageSender(){
        // create messsage sender
    }

	public static void broadcast(Message message, Collection<Session> sessions) {
		for (Session session : sessions) {
			try {
				EventSender.send(session, Event.createMessageEvent(message));
			} catch (Exception e) {
				// logging here and continue
			}
		}
	}
}