package chatroom.models;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class MessageEncoder implements Encoder.Text<Message> {
	@Override
	public void init(EndpointConfig config) {
		// create message encoder
	}

	@Override
	public void destroy() {
		// destroy message encoder
	}

	@Override
	public String encode(Message message) throws EncodeException {
		return message.toJson().toString();
	}
}
