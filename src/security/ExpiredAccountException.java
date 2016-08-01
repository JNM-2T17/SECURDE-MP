package security;

public class ExpiredAccountException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6252851189629646115L;

	public ExpiredAccountException() {
		super("Expired Account");
	}
}
