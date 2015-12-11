package edu.jsu.mcis;
/**
* Exception thrown when there is a problem with reading or writing a file.
* Problems include opening a file, reading arguments or if the file is blank.
* Extends RuntimeException class.
*/
public class FileErrorException extends RuntimeException {
  /**
  * @param message brings in a message and the problem file name.
  */
  public FileErrorException (String message) {
    super (message);
  }
}
