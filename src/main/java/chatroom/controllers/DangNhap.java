package chatroom.controllers;

import java.util.HashMap;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.Session;

import com.mongodb.client.FindIterable;

import org.bson.Document;

import chatroom.models.Response;
import chatroom.utils.Hash;

public class DangNhap {
    private Logger logger = Logger.getLogger("DangNhapLogger");

    public void init() {
        logger.info("Created DangNhap instance");
    }

    public static Response dangNhap(Database db, JsonObject eventPayload, HashMap<String, Session> onlineUsers) {
        Response response = new Response();
        response.setType("login");
        String username = eventPayload.getString("username");
        String md5Password = Hash.getMd5(eventPayload.getString("password"));
        FindIterable<Document> result = db.findDouble("username", username, "password", md5Password);
        String tokenKeyName = "token";
        Document doc = result.first();
        if (doc != null) {
            String token = doc.getString(tokenKeyName);
            if (token != null) {
                if(onlineUsers.containsKey(token)){
                    response.setCode(1);
                    response.setMessage("Tài khoản đã đăng nhập ở nơi khác");
                }else{
                    JsonObject responsePayload = Json.createObjectBuilder().add("token", token).build();
                    response.setCode(0);
                    response.setMessage("Đăng nhập thành công");
                    response.setPayload(responsePayload);
                }
            } else {
                response.setCode(1);
                response.setMessage("Khong tim thay token");
            }
        } else {
            response.setMessage("Sai tên đăng nhập hoặc mật khẩu");
        }
        return response;
    }
}