package chatroom.models;

import java.io.IOException;

import javax.websocket.EncodeException;
import javax.websocket.Session;

public class ResponseSender {
    public static void response(Session session, String status, int code, String message){
		Response res = new Response();
		res.setStatus(status);
		res.setCode(code);
		res.setMessage(message);
		try {
			session.getBasicRemote().sendObject(res);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (EncodeException e) {
			e.printStackTrace();
		}
	}
}