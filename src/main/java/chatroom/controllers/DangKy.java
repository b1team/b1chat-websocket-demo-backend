package chatroom.controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import javax.json.JsonObject;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.bson.Document;
import com.mongodb.client.FindIterable;
import chatroom.models.*;
import chatroom.utils.Hash;

@ServerEndpoint(value = "/dang-ky", encoders = { EventEncoder.class }, decoders = { EventDecoder.class })
public class DangKy {
	private Logger logger = Logger.getLogger("DangKyLogger");

	@OnMessage
	public void onMessage(Event event, Session session) throws IOException, EncodeException {
		if(event.getAction().equals("register")){
			Database db = new Database("users");
			Document account = new Document();
			JsonObject eventPayload = event.getPayload();
	
			String username = eventPayload.getString("username");
			String md5Password = Hash.getMd5(eventPayload.getString("password"));
			String token = Hash.getMd5(username.concat(eventPayload.getString("password")).toString());
			
			FindIterable<Document> result = db.findOne("username", username);
			Response response = new Response(); 
			response.setType("register");
			if(result.first() != null) {
				response.setCode(1);
				response.setMessage("Tên tài khoản đã được sử dụng");
			}
			else {
				account.append("username", username);
				account.append("password", md5Password);
				account.append("token", token);
				account.append("createAt", dateTime());
				db.insert(account);

				response.setCode(0);
				response.setMessage("Đăng ký thành công!");
			}
			EventSender.send(session, Event.createResponseEvent(response));
		}
	}

	public String dateTime() {
		LocalDateTime currentDateTime = LocalDateTime.now();  
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");  
		String datetime = currentDateTime.format(format);   
		return datetime;
	}

	@OnOpen
	public void onOpen() {
		logger.info("Client connected");
	}

	@OnClose
	public void onClose() {
		logger.info("Connection closed");
	}
}
