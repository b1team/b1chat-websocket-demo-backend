package chatroom.models;

import javax.json.Json;
import javax.json.JsonObject;

public class Response {
	private String status;
	private String message;
	private int code;
	private JsonObject payload;

	public Response() {
		code = 1;
		status = "failed";
		payload = Json.createObjectBuilder().build();
	    message = "";
	}

	public String getStatus() {
		return status;
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

	public void setStatus(String status) {
		this.status = status;
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

	public String toString() {
		JsonObject jsonObject = Json.createObjectBuilder().add("status", this.getStatus())
				.add("message", this.getMessage()).add("payload", this.getPayload()).add("code", this.getCode())
				.build();
		return jsonObject.toString();
	}
}
