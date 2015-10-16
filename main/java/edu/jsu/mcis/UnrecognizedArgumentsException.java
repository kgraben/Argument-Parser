package edu.jsu.mcis;

public class UnrecognizedArgumentsException extends RuntimeException {
	public static final boolean ROBOT_SUPPRESS_NAME=true;
	public UnrecognizedArgumentsException (String message) {
    	super(message);
	}
}
