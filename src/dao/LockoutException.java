package dao;

public class LockoutException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4424499974654888535L;
	private String message;
	private int minutes;
	
	public LockoutException() {
		super();
	}
	
	public LockoutException(String message, int minutes) {
		super();
		this.message = message;
		this.minutes = minutes;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}
}
