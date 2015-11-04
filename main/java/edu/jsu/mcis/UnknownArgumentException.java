package edu.jsu.mcis;

public class UnknownArgumentException extends RuntimeException {
	private String argumentName;
	public UnknownArgumentException(String argName) {
		super("I do not recognize " + argumentName);
    argumentName = argName;
  }

	public String getArgumentName() {
		return argumentName;
	}




}
