package chatroom.models;

import javax.json.JsonObject;

public class Event {
	private String action;
	private JsonObject payload;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public JsonObject getPayload() {
		return payload;
	}

	public void setPayload(JsonObject payload) {
		this.payload = payload;
	}
}