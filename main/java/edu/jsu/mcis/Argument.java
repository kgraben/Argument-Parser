package edu.jsu.mcis;


public class Argument{

	private String userArgument;
	private String positionalValue;

	public Argument(){
		positionalValue="";
		userArgument="";
	}

	public void setUserArg(String s){
		userArgument=s;
	}

	public String getUserArg(){
		return userArgument;
	}
}
