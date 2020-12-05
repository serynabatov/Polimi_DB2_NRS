package exceptions;

public class EmailCredentialsException extends Exception{
	
	private static final long serialVersionUID = 1L;
	
	public EmailCredentialsException(String msn) {
		super(msn);
	}

}
