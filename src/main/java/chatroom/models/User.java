package chatroom.models;

public class User {
	private String username;
	private String password;
	private String raw;
	
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}

	
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getRaw() {
		return raw;
	}

	public void setRaw(String raw) {
		this.raw = raw;
	}
}
