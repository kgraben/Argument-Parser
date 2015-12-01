package edu.jsu.mcis;
/**
 * Class description: Exception class for Missing Argument. Extend RuntimeException class.
 *
 *
 *
 */
public class MissingArgumentException extends RuntimeException {
     /**
     * Constructs a new MissingArgumentException with a detail message
     * @Param message - the detail message.
     *
     */
    public MissingArgumentException (String message) {
    	super (message);
   	 }
   	 
  
}
