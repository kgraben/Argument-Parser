package edu.jsu.mcis;

public class UnknownArgumentException extends RuntimeException {
	public UnknownArgumentException (String message) {
    	super (message);
	/*
	private String argumentName;
	public UnknownArgumentException(String argName) {
<<<<<<< HEAD
		super("I do not recognize " + argumentName);
    	argumentName = argName;
=======
		super("I do not recognize " + argName);
    argumentName = argName;
>>>>>>> e7b2a73465e5564ad435fee53a4d462477d6167d
  }

	public String getArgumentName() {
		return argumentName;
	}
<<<<<<< HEAD

	*/
	}



=======
>>>>>>> e7b2a73465e5564ad435fee53a4d462477d6167d
}
