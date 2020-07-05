package chatroom.controllers;

import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.Session;

import com.mongodb.client.FindIterable;

import org.bson.Document;

import chatroom.Config.DataBase;
import chatroom.models.ResponseSender;
import chatroom.utils.Hash;

public class DangNhap {
    private Logger logger = Logger.getLogger("DangNhapLogger");

    public void init() {
        logger.info("Created DangNhap instance");
    }

    public static void dangNhap(Session session, DataBase db, JsonObject eventPayload) {
        db.init();
        String username = eventPayload.getString("username");
        String md5Password = Hash.getMd5(eventPayload.getString("password"));
        FindIterable<Document> result = db.findDouble("username", username, "password", md5Password);
        String tokenKeyName = "token";
        Document doc = result.first();
        if (doc != null) {
            String token = doc.getString(tokenKeyName);
            if (token != null) {
                JsonObject responsePayload = Json.createObjectBuilder().add("token", token).build();
                ResponseSender.reswithpayload(session,"success", 0, "Đăng nhập thành công", responsePayload);
            } else {
                ResponseSender.response(session, "failed", 1, "Khong tim thay token");
            }
        } else {
            ResponseSender.response(session, "failed", 1, "Sai tên đăng nhập hoặc mật khẩu");
        }
    }
}