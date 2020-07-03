package chatroom.models;

import java.time.LocalDateTime;

import javax.json.Json;
import javax.json.JsonObject;

public class Message {
	private String username;
	private String content;
	private LocalDateTime created_at;
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
		JsonObject jsonObject = Json.createObjectBuilder()
									.add("username", this.username)
									.add("message", this.content)
									.add("created_at", this.created_at.toString())
									.build();
		return jsonObject;
	}
}
