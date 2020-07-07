package chatroom.models;

import javax.json.Json;
import javax.json.JsonObject;

public class Response {
	private String message;
	private int code;
	private String type;
	private JsonObject payload;

	public Response() {
		type = "invalid";
		code = 1;
		payload = Json.createObjectBuilder().build();
		message = "";
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public JsonObject getPayload() {
		return payload;
	}

	public void setPayload(JsonObject payload) {
		this.payload = payload;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public JsonObject toJson() {
		JsonObject jsonObject = Json.createObjectBuilder().add("message", this.getMessage())
				.add("response_payload", this.getPayload()).add("code", this.getCode()).add("type", this.getType()).build();
		return jsonObject;
	}
}
