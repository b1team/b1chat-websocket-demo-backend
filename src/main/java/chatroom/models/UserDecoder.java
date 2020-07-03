package chatroom.models;

import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class UserDecoder implements Decoder.Text<User>{

	@Override
	public void init(EndpointConfig config) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User decode(String jsonMessage) throws DecodeException {
		JsonObject jsonObject = Json
		        .createReader(new StringReader(jsonMessage)).readObject();
			User user = new User();
			
			user.setUsername(jsonObject.getString("username"));
			user.setPassword(jsonObject.getString("password"));
			user.setRaw(jsonMessage);
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

