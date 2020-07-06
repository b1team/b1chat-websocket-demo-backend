package chatroom.controllers;

import java.util.Map;

import javax.websocket.Session;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import org.bson.Document;

import chatroom.models.Message;
import chatroom.models.Response;

public class JoinChat {
    private JoinChat() {
        // create join chat
    }

    public static Response join(Map<String, Object> config, Map<String, Session> onlineUsers, String token, Session session) {
        Map<String, Object> usersConfig = (Map<String, Object>) config.get("users");
        String uri = (String) usersConfig.get("uri");
        String dbname = (String) usersConfig.get("dbname");
        String collectionName = (String) usersConfig.get("collection");
        Response response = new Response();
        try (MongoClient client = MongoClients.create(uri)) {

            MongoDatabase db = client.getDatabase(dbname);
            MongoCollection collection = db.getCollection(collectionName);
            FindIterable<Document> result = collection.find(Filters.eq("token", token));
            if (result.first() != null) {
                String defaultUsername = "Someone";
                Document doc = result.first();
                String username = doc.getString("username");
                if (username == null) {
                    username = defaultUsername;
                }

                Message message = new Message();
                message.setUsername(username);
                message.setContent(String.format("%s đã tham gia nhóm chat", username));

                MessageSender.broadcast(message, onlineUsers.values());
                onlineUsers.put(token, session);
                // tao response thanh cong
                response.setCode(0);
                response.setStatus("success");
                response.setMessage("Tham gia nhóm chat thành công");
            } else {
                response.setMessage("Invalid Token");
            }
        }
        return response;
    }
}