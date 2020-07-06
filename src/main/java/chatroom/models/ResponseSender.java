package chatroom.models;

import java.io.IOException;
import javax.websocket.EncodeException;
import javax.websocket.Session;

public class ResponseSender {
	public static void send(Session session, Response response){
		try {
			session.getBasicRemote().sendObject(response);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (EncodeException e) {
			e.printStackTrace();
		}
	}
}