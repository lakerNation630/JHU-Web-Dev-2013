package jhu.web.java.exceptions;

import jhu.web.java.models.User;

/**
 * This exception indicates that an item requested for purchase is not currently available
 */
public class UserNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public UserNotFoundException(String user){
		super(String.format("The following username could not be found: [%s]",user));
	}
}
