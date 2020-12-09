package it.mirea.marketing.exceptions;

public class CredentialsException extends Exception{
	// field to define the version of a particular class while serializing
	// &  deserializing. Because when we'll update an entity then all the previous
	// versions (which are serialized) cannot be casted or de-serialized to the new 
	// one & we'll get an exception
	private static final long serialVersionUID = 1L;
	
	public CredentialsException(String msn) {
		super(msn);
	}
}
