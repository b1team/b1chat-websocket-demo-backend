package chatroom.models;



public class Response {
	private String status;
	private int code;
	private String message;
	
	public String getStatus() {
		return status;
	}
	
	public Integer getCode() {
		return code;
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
	
	public void setCode(int code) {
		this.code = code;
	}

}
