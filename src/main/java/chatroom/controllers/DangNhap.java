package chatroom.controllers;

import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonObject;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import org.bson.Document;

import chatroom.models.Response;
import chatroom.utils.Hash;

public class DangNhap {
    private Logger logger = Logger.getLogger("DangNhapLogger");
    public void init(){
        logger.info("Created DangNhap instance");
    }

    public static Response dangNhap(MongoCollection<Document> collection, JsonObject eventPayload) {
        String username = eventPayload.getString("username");
        String md5Password = Hash.getMd5(eventPayload.getString("password"));
        FindIterable<Document> result = collection
                .find(Filters.and(Filters.eq("username", username), Filters.eq("password", md5Password)));
        Response response = new Response();
        if (result != null) {
            String tokenFieldName = "token";
            Document doc = result.first();
            if (doc.containsKey(tokenFieldName)) {
                response.setCode(0);
                response.setMessage("Đăng nhập thành công");
                response.setStatus("success");
                String token = doc.get(tokenFieldName).toString();
                JsonObject responsePayload = Json.createObjectBuilder().add(tokenFieldName, token).build();
                response.setPayload(responsePayload);
            } else {
                response.setCode(1);
                response.setMessage("Không tìm thấy token");
                response.setStatus("failed");
            }
        } else {
            response.setStatus("failed");
            response.setCode(1);
            response.setMessage("Sai tên đăng nhập hoặc mật khẩu");
        }
        return response;
    }
}