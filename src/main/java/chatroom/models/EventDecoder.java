package chatroom.models;

import java.io.StringReader;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class EventDecoder implements Decoder.Text<Event> {
	private Logger logger = Logger.getLogger("EventEncoderLogger");

	@Override
	public void init(EndpointConfig config) {
		logger.info("Created Event Encoder");
	}

	@Override
	public void destroy() {
		logger.info("Deleted Event Encoder");
	}

	@Override
	public Event decode(String jsonMessage) throws DecodeException {
		Event event = new Event();
		try (JsonReader reader = Json.createReader(new StringReader(jsonMessage))) {
			JsonObject jsonObject = reader.readObject();
			String actionString = jsonObject.getString("action");
			JsonObject payload = jsonObject.getJsonObject("payload");
			event.setAction(actionString);
			event.setPayload(payload);
			return event;
		}
	}

	@Override
	public boolean willDecode(String jsonMessage) {
        try (JsonReader reader = Json.createReader(new StringReader(jsonMessage))) {
			reader.readObject();
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			logger.info("");
		}
	}

}