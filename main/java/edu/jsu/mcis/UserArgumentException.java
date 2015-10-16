package edu.jsu.mcis;

public class UserArgumentException{

	private String incorrectArgument="";
	private String unrecognizedArgument="";

	public void setIncorrectDataType(String name){
		incorrectArgument=name;
	}

	public String getIncorrectDataType(){
		return incorrectArgument;
	}

	public void setUnrecognizedArgument(String name){
		unrecognizedArgument=name;
	}

	public String getUnrecognizedArgument(){
		return unrecognizedArgument;
	}
}
