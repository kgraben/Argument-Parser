package edu.jsu.mcis;

public class Argument{

	public enum Type {INT, FLOAT, BOOLEAN, STRING};
	private String value;
	private String name;
	private String shortName;
	private Type type;
	private String description;

	public Argument(){
		value = "0";
		name = "";
		shortName = "";
		type = Type.STRING;
		description = "";
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
		return name;
	}

	public void setShortName(String n){
		shortName = n;

	}
	public String getShortName(){
		return shortName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String desc) {
		description = desc;
	}
}
