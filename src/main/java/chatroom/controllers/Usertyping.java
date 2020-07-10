package chatroom.controllers;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.Session;

import com.mongodb.client.FindIterable;

import org.bson.Document;

import chatroom.models.Event;
import chatroom.models.EventSender;
import chatroom.models.Response;

public class Usertyping {
    private Usertyping(){

    }
    public static Response typingHandle(Database userDB, Map<String, Session> onlineUsers, String token, int typing, Session session) {
        Response response = new Response();
        response.setType("typing");

        FindIterable<Document> result = userDB.findOne("token", token);
		if(result.first() != null){
            Set<String> blackList = new HashSet<String>();
            blackList.add(token);

			Document doc = result.first();
            String username = doc.getString("username");
            JsonObject payload = Json.createObjectBuilder().add("typing", typing)
                                                            .add("username", username).build();
                                              
            Event event = new Event();
            event.setAction("user-typing");
            event.setPayload(payload);
            EventSender.broadcast(event, onlineUsers, blackList);

            response.setCode(0);
            response.setMessage("success");
            
        }
        else{
            response.setCode(1);
            response.setMessage("failed");
        }
        return response;
    }
}