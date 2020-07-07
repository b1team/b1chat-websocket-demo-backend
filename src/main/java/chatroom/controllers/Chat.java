package chatroom.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import javax.websocket.Session;

import com.mongodb.client.FindIterable;

import org.bson.Document;

import chatroom.models.Message;
import chatroom.models.Response;

public class Chat {
    private Chat() {
        // create chat
    }
// Phai la event pan aj ben phong chat minh de event maf
    public static Response send(Database userDB, Database messageDB, Map<String, Session> onlineUsers,
                                String token, String content) {
        Response response = new Response();
        response.setType("send_message");
        Document mess = new Document();
        FindIterable<Document> result = userDB.findOne("token", token);
        if (result.first() != null) {
            String defaultUsername = "Someone";
            Document doc = result.first();
            String username = doc.getString("username");
            if (username == null) {
                username = defaultUsername;
            }
            Message message = new Message();
            message.setUsername(username);
            message.setContent(content);

            mess.append(username, content);
            mess.append("createAt", dateTime());
            messageDB.insert(mess);
            MessageSender.broadcast(message, onlineUsers.values());

            response.setCode(0);
            response.setMessage("Message sent");
        } else {
            response.setMessage("Invalid Token");
        }
        return response;
    }

    public static String dateTime() {
		LocalDateTime currentDateTime = LocalDateTime.now();  
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");  
		String datetime = currentDateTime.format(format);   
		return datetime;
	}
}
