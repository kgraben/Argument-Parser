package edu.jsu.mcis;
/**
* Exception class for an Unknown Argument. Extends RuntimeException class.
*/
public class UnknownArgumentException extends RuntimeException {
	/**
	* Constructs a new UnknownArgumentException with a detail message
	* @param message the detail message.
	*/
	public UnknownArgumentException (String message) {
		super (message);
	}
}
