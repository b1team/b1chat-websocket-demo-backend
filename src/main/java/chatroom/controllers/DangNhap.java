package chatroom.controllers;

import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonObject;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import org.bson.Document;

import chatroom.models.Response;
import chatroom.utils.Hash;

public class DangNhap {
    private Logger logger = Logger.getLogger("DangNhapLogger");

    public void init() {
        logger.info("Created DangNhap instance");
    }

    public static Response dangNhap(MongoDatabase db, JsonObject eventPayload, String usersCollectionName) {
        MongoCollection<Document> collection = db.getCollection(usersCollectionName);
        String username = eventPayload.getString("username");
        String md5Password = Hash.getMd5(eventPayload.getString("password"));
        FindIterable<Document> result = collection
                .find(Filters.and(Filters.eq("username", username), Filters.eq("password", md5Password)));
        Response response = new Response();
        String tokenKeyName = "token";
        Document doc = result.first();
        if (doc != null) {
            String token = doc.getString(tokenKeyName);
            if (token != null) {
                response.setStatus("success");
                response.setCode(0);
                response.setMessage("Đăng nhập thành công");
                JsonObject responsePayload = Json.createObjectBuilder().add("token", token).build();
                response.setPayload(responsePayload);
            } else {
                response.setStatus("failed");
                response.setCode(1);
                response.setMessage("Khong tim thay token");
            }
        } else {
            response.setStatus("failed");
            response.setCode(1);
            response.setMessage("Sai tên đăng nhập hoặc mật khẩu");
        }
        return response;
    }
}