package edu.jsu.mcis;


public class Argument{

	private String positionalValue;
	private String positionalName;
	private String namedValue;
	private String namedName;
	private String incorrectArgument="";
	private String unrecognizedArgument="";
	private String type;
	private String digit;
	public enum DATATYPE{INT,FLOAT,BOOLEAN,STRING};
  public DATATYPE data;

	public Argument(){
		positionalValue="";
		positionalName="";
		namedValue="";
		type="";
		digit="";
		data=DATATYPE.STRING;
	}

	public void setPositionalValue(String s){
		positionalValue=s;
	}

	public String getPositionalValue(){
		return positionalValue;
	}

	public void setDataType(DATATYPE T){
		data=T;
	}

	public DATATYPE getDataType(){
		return data;
	}

	public void setPositionalName(String s){
		positionalName=s;
	}

	public String getPositionalName(){
		return positionalName;
	}

	public void setnamedName(String s){
		namedName=s;
	}

	public String getnamedName(){
		return namedName;
	}

	public void setNamedValue(String s){
		namedValue=s;
	}

	public String getNamedValue(){
		return namedValue;
	}

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
