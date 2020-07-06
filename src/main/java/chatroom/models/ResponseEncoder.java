package chatroom.models;

import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class ResponseEncoder implements Encoder.Text<Response> {
	private Logger logger = Logger.getLogger("ResponseEncoderLogger");

	@Override
	public void init(EndpointConfig config) {
		logger.info("Init response encoder");
	}

	@Override
	public void destroy() {
		logger.info("Distroyed Response Encoder");
	}

	@Override
	public String encode(Response response) throws EncodeException {
		try {
			JsonObjectBuilder builder = Json.createObjectBuilder().add("status", response.getStatus())
					.add("message", response.getMessage()).add("code", response.getCode());
			JsonObject payload = response.getPayload();
			if (payload != null) {
				builder.add("payload", payload);
			}
			JsonObject jsonObject = builder.build();
			return jsonObject.toString();
		} catch (Exception ex) {
			logger.info(ex.toString());
			return "";
		}
	}

}
