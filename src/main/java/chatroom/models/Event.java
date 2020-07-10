package chatroom.models;

import javax.json.Json;
import javax.json.JsonObject;

public class Event {
	private String action;
	private JsonObject payload;

	public Event() {
		action = "undefined";
		payload = Json.createObjectBuilder().build();
	}

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

	public JsonObject toJson() {
		return Json.createObjectBuilder().add("action", action).add("payload", payload).build();
	}

	public static Event createResponseEvent(Response response) {
		Event event = new Event();
		event.action = "response";
		event.payload = response.toJson();
		return event;
	}

	public static Event createMessageEvent(Message message) {
		Event event = new Event();
		event.action = "send_message";
		event.payload = message.toJson();
		return event;
	}
} 