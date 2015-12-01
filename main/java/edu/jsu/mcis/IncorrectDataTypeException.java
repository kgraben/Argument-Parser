package edu.jsu.mcis;
/**
 * Class description: Exception class for Incorrect type. Extend RuntimeException class.
 *
 *
 *
 */
public class IncorrectDataTypeException extends RuntimeException {
    /**
     * Constructs a new IncorrectDataTypeException with a detail message
     * @Param message - the detail message.
     *
     */
    	public IncorrectDataTypeException (String message) {
    	super (message);
   	 }	
}
