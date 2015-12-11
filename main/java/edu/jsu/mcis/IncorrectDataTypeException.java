package edu.jsu.mcis;
/**
* Exception class for Incorrect type. Extends RuntimeException class.
*/
public class IncorrectDataTypeException extends RuntimeException {
  /**
  * Constructs a new IncorrectDataTypeException with a detail message
  * @param message - the detail message.
  */
  public IncorrectDataTypeException (String message) {
    super (message);
  }
}
