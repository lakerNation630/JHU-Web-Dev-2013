package jhu.web.java.exceptions;

import jhu.web.java.models.User;

/**
 * This exception indicates that an item requested for purchase is not currently available
 */
public class InvalidPassowrdException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public InvalidPassowrdException(User user){
		super(String.format("The supplied password is invalid for the user: [%s]",user.getUsername()));
	}
}
