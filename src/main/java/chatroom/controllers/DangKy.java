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
import chatroom.models.*;

@ServerEndpoint(value = "/dang-ky", encoders = { ResponseEncoder.class }, decoders = { UserDecoder.class })
public class DangKy {
	@OnMessage
	public void onMessage(User user, Session session) throws IOException, EncodeException {
		Database db = new Database();
		db.init();
		HashMap<String,Object> account = new HashMap<String,Object>();
		FindIterable<Document> result = db.findOne("username", user.getUsername());
		Response response = new Response();
		if(result.first() != null) {
			response.setStatus("error");
			response.setCode(1);
			response.setMessage("Tên tài khoản đã được sử dụng");
			ResponseSender.send(session, response);
			return;
		}
		else {
			response.setStatus("success");
			response.setCode(0);
			response.setMessage("Đăng ký thành công!");
			ResponseSender.send(session, response);

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
