package chatroom.models;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class ResponseEncoder implements Encoder.Text<Response>{

	@Override
	public void init(EndpointConfig config) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String encode(Response response) throws EncodeException {
		JsonObject jsonObject = Json.createObjectBuilder().add("status", response.getStatus()).add("code", response.getCode())
				.add("message", response.getMessage()).build();
		return jsonObject.toString();
	}

}
