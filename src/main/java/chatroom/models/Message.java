package chatroom.models;

import java.time.LocalDateTime;

import javax.json.Json;
import javax.json.JsonObject;

public class Message {
	private String username;
	private String content;
	private LocalDateTime created_at;

	public Message() {
		username = "undefined";
		content = "undefined";
		created_at = LocalDateTime.now();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public LocalDateTime getCreated_at() {
		return created_at;
	}

	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public JsonObject toJson() {
		return Json.createObjectBuilder().add("username", this.username).add("content", this.content).build();
	}
}
