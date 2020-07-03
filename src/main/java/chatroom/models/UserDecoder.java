package chatroom.models;
import chatroom.utils.*;
import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.time.LocalDateTime;  
import java.time.format.DateTimeFormatter; 
public class UserDecoder implements Decoder.Text<User>{

	@Override
	public void init(EndpointConfig config) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
	public String dateTime() {
		LocalDateTime currentDateTime = LocalDateTime.now();  
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");  
		String datetime = currentDateTime.format(format);   
		return datetime;
	}
	
	@Override
	public User decode(String jsonMessage) throws DecodeException {
		JsonObject jsonObject = Json
		        .createReader(new StringReader(jsonMessage)).readObject();
			User user = new User();
			String username = jsonObject.getString("username");
			String password = jsonObject.getString("password");
			String datetime = dateTime();

			user.setUsername(username);
			user.setPassword(Hash.getMd5(password).toString());
			user.setToken(Hash.getMd5(username.concat(password).toString()));
			user.setDatetime(datetime);
			return user;
	}

	@Override
	public boolean willDecode(String jsonMessage) {
		try {
		      // Check if incoming message is valid JSON
		      Json.createReader(new StringReader(jsonMessage)).readObject();
		      return true;
		    } catch (Exception e) {
		      return false;
		    }
		  }
}

