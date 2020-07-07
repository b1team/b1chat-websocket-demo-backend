package chatroom.models;

import java.io.IOException;
import javax.websocket.EncodeException;
import javax.websocket.Session;

public class EventSender {
	public static void send(Session session, Event event){
		try {
			session.getBasicRemote().sendObject(event);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (EncodeException e) {
			e.printStackTrace();
		}
	}
}