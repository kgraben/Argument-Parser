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
	public enum DATATYPE{INT,FLOAT,BOOLEAN,STRING};
    public DATATYPE data;
	

	
	
	public Argument(){
	
		positionalValue="";
		positionalName="";
		namedArgumentValue="";
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