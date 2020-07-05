package chatroom.controllers;

import java.io.IOException;
import java.util.HashMap;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.bson.Document;
import com.mongodb.client.FindIterable;
import chatroom.Config.DataBase;
import chatroom.models.*;

@ServerEndpoint(value = "/dang-ky", encoders = { ResponseEncoder.class }, decoders = { UserDecoder.class })
public class DangKy {
	@OnMessage
	public void onMessage(User user, Session session) throws IOException, EncodeException {
		HashMap<String,Object> account = new HashMap<String,Object>();
		DataBase db = new DataBase();
		db.init();;
		FindIterable<Document> result = db.findOne("username", user.getUsername());
		if(result.first() != null) {
			ResponseSender.response(session, "error", 1, "Tên tài khoản đã được sử dụng");
			return;
		}
		else {
			ResponseSender.response(session, "success", 0, "Đăng ký thành công!");
			account.put("token", user.getToken());
			account.put("createAt", user.getDatetime());
			account.put("password", user.getPassword());
			account.put("username", user.getUsername());
			db.insert(account);
		}
	}
	  

	@OnOpen
	public void onOpen() {
		System.out.println("Client connected");
	}

	@OnClose
	public void onClose() {
		System.out.println("Connection closed");
	}
}
