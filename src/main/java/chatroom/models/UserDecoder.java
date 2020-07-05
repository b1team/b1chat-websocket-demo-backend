package chatroom.models;

import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class UserDecoder implements Decoder.Text<User> {

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
		try (JsonReader reader = Json.createReader(new StringReader(jsonMessage))) {
			JsonObject jsonObject = reader.readObject();
			User user = new User();
			user.setUsername(jsonObject.getString("username"));
			user.setPassword(jsonObject.getString("password"));
			user.setRaw(jsonMessage);
			return user;
		}
	}

	@Override
	public boolean willDecode(String jsonMessage) {
		try (JsonReader reader = Json.createReader(new StringReader(jsonMessage))) {
			reader.readObject();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
