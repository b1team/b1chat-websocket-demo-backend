package chatroom.models;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

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

	public static void broadcast(Event event, Map<String, Session> users, Set<String> blackList){
		for(Entry<String, Session> entry: users.entrySet()){
			String token = entry.getKey();
			if(blackList.contains(token)){
				// bo qua
				continue;
			}
			Session session = entry.getValue();
			EventSender.send(session, event);
		}
	}
}