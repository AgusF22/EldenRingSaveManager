package main.exceptions;

/**
 * Used to signal that a save file name is not valid
 * @author AgusF
 */
public class InvalidNameException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new InvalidNameException
	 * @param msg A message describing the exception
	 */
	public InvalidNameException(String msg) {
		super(msg);
	}
	
}
