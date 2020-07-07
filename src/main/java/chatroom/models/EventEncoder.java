package chatroom.models;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class EventEncoder implements Encoder.Text<Event>{
    @Override
	public void init(EndpointConfig config) {
        // Create encoder
	}

	@Override
	public void destroy() {
        // destroy encoder
	}

	@Override
	public String encode(Event event) throws EncodeException {
		return event.toJson().toString();
	}
}