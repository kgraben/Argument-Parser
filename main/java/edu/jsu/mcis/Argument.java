package edu.jsu.mcis;

public class Argument{

	public enum Type {INT, FLOAT, BOOLEAN, STRING};
	private String value;
	private String name;
	private Type type;

	public Argument(){
		value = "0";
		name = "";
		type = Type.STRING;
	}

	public void setValue(String s){
		value = s;
	}

	public String getValue(){
		return value;
	}

	public void setType(Type t){
		type = t;
	}

	public Type getType(){
		return type;
	}

	public void setName(String s){
		name = s;
	}

	public String getName(){
		return value;
	}
}