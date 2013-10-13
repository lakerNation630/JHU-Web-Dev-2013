package jhu.web.java.exceptions;

import jhu.web.java.models.Item;

/**
 * This exception indicates that an item requested for purchase is not currently available
 */
public class ItemNotAvailableException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public ItemNotAvailableException(Item item){
		super(String.format("The following item is not available: [%s]",item.getName()));
	}
}
