package chatroom.models;


public class User {
	private String token;
	private String username;
	private String password;
	private String datetime;

	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	
	public String getToken() {
		return token;
	}
	public String getDatetime() {
		return datetime;
	}
	
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
