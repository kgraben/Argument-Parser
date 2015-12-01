package edu.jsu.mcis;
/**
* Exception thrown when user requests the help message invoked by -h or --help.
*/
public class HelpMessageException extends RuntimeException {
  /**
  * @param message Help message is passed in and displayed when exception is thrown.
  */
    	public HelpMessageException (String message) {
    	super (message);
   	 }
}
