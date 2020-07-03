package chatroom.models;

import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class EventDecoder implements Decoder.Text<Event> {

	@Override
	public void init(EndpointConfig config) {

	}

	@Override
	public void destroy() {

	}

	@Override
	public Event decode(String jsonMessage) throws DecodeException {
		Event event = new Event();
		JsonObject jsonObject = Json.createReader(new StringReader(jsonMessage)).readObject();
		String actionString = jsonObject.getString("action");
		JsonObject payload = jsonObject.getJsonObject("payload");
		event.setAction(actionString);
		event.setPayload(payload);
		return event;
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