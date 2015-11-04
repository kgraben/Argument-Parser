package edu.jsu.mcis;

import java.util.*;
import java.io.*;


public class Argument{

	private String positionalValue;
	private String positionalName;
	private String namedArgumentValue;
	private String namedArgumentName;
	private String incorrectArgument="";
	private String unrecognizedArgument="";
	private String type;
	private String digit;
	private String namedDefaultValue;
	private Type data;
	public enum Type{INT,FLOAT,BOOLEAN,STRING};
  
	public Argument(){
		positionalValue="";
		positionalName="";
		namedArgumentValue="";
		type="";
		digit="";
		namedDefaultValue="";
		data=Type.STRING;
	}

	public void setPositionalValue(String s){
		positionalValue=s;
	}

	public String getPositionalValue(){
		return positionalValue;
	}

	public void setType(Type T){
		data=T;
	}

	public Type getType(){
		return data;
	}

	public void setPositionalName(String s){
		positionalName=s;
	}

	public String getPositionalName(){
		return positionalName;
	}

	public void setnamedArgumentName(String s){
		namedArgumentName=s;
	}

	public String getnamedArgumentName(){
		return namedArgumentName;
	}

	public void setNamedArgumentValue(String s){
		namedArgumentValue=s;
	}

	public String getNamedArgumentValue(){
		return namedArgumentValue;
	}
	
	public void setDefaultValue(String value){
		
		namedDefaultValue=value;
	
	
	}
	
	public String getDefaultValue(){
	
		return namedDefaultValue;
	}
	
	

	public void setIncorrectType(String name){
		incorrectArgument=name;
	}

	public String getIncorrectType(){
		return incorrectArgument;
	}

	public void setUnrecognizedArgument(String name){
		unrecognizedArgument=name;
	}

	public String getUnrecognizedArgument(){
		return unrecognizedArgument;
	}
}