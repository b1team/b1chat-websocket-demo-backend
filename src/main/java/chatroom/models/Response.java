package chatroom.models;



public class Response {
	private String status;
	private String message;
	private int code;
	
	public String getStatus() {
		return status;
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

}
