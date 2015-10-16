	/*This is the first file
By: Christopher Burdette
    Nathan Chaney
    Kurtis Graben
    Khoi Phan
    Hui Wang
*/
package edu.jsu.mcis;

import java.util.*;
import java.io.*;

public class ArgumentParser{

	private LinkedHashMap<String,DATATYPE> positionalArguments;
	private LinkedHashMap<String,String> namedArguments;
	private LinkedHashMap<String,String> intArguments;
	private LinkedHashMap<String,String> floatArguments;
	private LinkedHashMap<String,String> booleanArguments;
	private LinkedHashMap<String,String> stringArguments;
	private ArrayList<String> userInput;
	private ArrayList<String> userPositionalArguments;
	private ArrayList<String> userNamedArguments;
  private Argument argument;
  private int helpMessage;
  private int missingArgumentHeightMessage;
  private int missingArgumentWidthAndHeightMessage;
  private int unrecognizedArgumentsMessage;
  private int incorrectDataTypeMessage;
  public enum DATATYPE{INT,FLOAT,BOOLEAN,STRING};
  public DATATYPE data;
  private UserArgumentException temp;

	public ArgumentParser(){
		namedArguments=new LinkedHashMap<String,String>();
		positionalArguments=new LinkedHashMap<String,DATATYPE>();
		intArguments=new LinkedHashMap<String,String>();
		floatArguments=new LinkedHashMap<String,String>();
		booleanArguments=new LinkedHashMap<String,String>();
		stringArguments=new LinkedHashMap<String,String>();
		argument=new Argument();
		userInput=new ArrayList<String>();
		userPositionalArguments=new ArrayList<String>();
		userNamedArguments=new ArrayList<String>();
		data=DATATYPE.STRING;
		helpMessage=0;
		missingArgumentWidthAndHeightMessage=0;
		missingArgumentHeightMessage=0;
		unrecognizedArgumentsMessage=0;
		incorrectDataTypeMessage=0;
		temp= new UserArgumentException();
	}

	public void addPositionalArgument(String x){
		positionalArguments.put(x,data);
	}

	public void addPositionalArgument(String x,DATATYPE d){
		positionalArguments.put(x,d);
	}

	public void addNamedArgument(String x){
		namedArguments.put(x,x);
	}

	public void checkUserInputSize() throws ArrayIndexOutOfBoundsException{
		if(userPositionalArguments.size()==1 && (!userPositionalArguments.contains("--h") && !userPositionalArguments.contains("-h"))){
				missingArgumentWidthAndHeightMessage();
			}
		else if(userPositionalArguments.size()==2){
				missingArgumentHeightMessage();
			}
		else if(userPositionalArguments.size()>positionalArguments.size()){
			for(int m=3; m < userPositionalArguments.size(); m++){
				temp.setUnrecognizedArgument(userPositionalArguments.get(m));
			    unrecognizedArgumentsMessage();
			}
	 	}
	}

	public void matchNamedArguments(){
		for(String s: namedArguments.keySet()){
			String d=namedArguments.get(s);
			if(d.equals("Type")){
				String box="Box";
				namedArguments.put(s,box);
			}
			else if(d.equals("Digits")){
				String digit="4";
				namedArguments.put(s,digit);
			}
			int j=1;
			for(int i=0; i < userNamedArguments.size(); i+=2){
				if((i % 2==0) && (j % 2==1)){
					namedArguments.put(userNamedArguments.get(i), userNamedArguments.get(j));
					j+=2;
				}
			}
		}
	}

	public void matchPositionalArguments(){
		int i=0;

		if(userPositionalArguments.size()==positionalArguments.size()){
			for(String s: positionalArguments.keySet()){
				DATATYPE d=positionalArguments.get(s);
				if(d==data.INT){
					argument.setUserArg(userPositionalArguments.get(i));
					try{
						Integer.parseInt(argument.getUserArg());
						intArguments.put(s,argument.getUserArg());
					}
					catch(NumberFormatException ex){
						temp.setIncorrectDataType(argument.getUserArg());
						incorrectDataTypeMessage();
					}
					i++;
				}
				else if(d==data.FLOAT){
					argument.setUserArg(userPositionalArguments.get(i));
					try{
						Float.parseFloat(argument.getUserArg());
						floatArguments.put(s,argument.getUserArg());
					}
					catch(NumberFormatException ex){
						temp.setIncorrectDataType(argument.getUserArg());
						incorrectDataTypeMessage();
					}
					i++;
				}
				else if(d==data.BOOLEAN){
					argument.setUserArg(userPositionalArguments.get(i));
					booleanArguments.put(s,argument.getUserArg());
					i++;
				}
				else{
					argument.setUserArg(userPositionalArguments.get(i));
					stringArguments.put(s,argument.getUserArg());
					i++;
				}
			}
		}
	}

	public void parse(String[] args){
		for(int i=0; i<args.length; i++){
			userPositionalArguments.add(args[i]);
		}
		for(int j=0; j < userPositionalArguments.size(); j++){
			int k=0;
			if(userPositionalArguments.contains("--h") ||userPositionalArguments.contains("-h") ){
				getHelpMessage();
				break;
			}
			while(userPositionalArguments.get(j).charAt(k)=='-'){
				userNamedArguments.add(userPositionalArguments.get(j).substring(userPositionalArguments.get(j).lastIndexOf("-")+1));
				userNamedArguments.add(userPositionalArguments.get(j+1));
				userPositionalArguments.remove(userPositionalArguments.get(j+1));
				userPositionalArguments.remove(userPositionalArguments.get(j));
				j=0;
			}
		}

		checkUserInputSize();
		matchPositionalArguments();
		matchNamedArguments();
	}

 	@SuppressWarnings("unchecked")
 	public <T> T getValue(String name){
 		for(String namedArg: namedArguments.keySet()){
			if(name.equals(namedArg)){
				return (T) namedArguments.get(name);
			}
		}
 		for(String intName: intArguments.keySet()){
			if(name.equals(intName)){
				int num=Integer.parseInt(intArguments.get(name));
				return (T) new Integer(num);
			}
		}
		for(String booleanName: booleanArguments.keySet()){
			if(name.equals(booleanName)){
				boolean num=Boolean.parseBoolean(booleanArguments.get(name));
				return (T) Boolean.valueOf(num);
			}
		}
		for(String floatName: floatArguments.keySet()){
			if(name.equals(floatName)){
				float num=Float.parseFloat(floatArguments.get(name));
				return (T) new Float(num);
			}
		}
		return (T) stringArguments.get(name);
 	}

 	public int getSizeOfHashMap(){
 		return positionalArguments.size();
 	}

 	public String getHelpMessage(){
 		helpMessage++;
 		return "usage: java VolumeCalculator length width height" + "\n" + "Calcuate the volume of a box." + "\n" + "positional arguments:" + "\n" +   "length the length of the box (float)"  + "\n" +   "width the width of the box(float)" + "\n" + "height the height of the box(float)";
 	}

 	public String missingArgumentWidthAndHeightMessage(){
 		missingArgumentWidthAndHeightMessage++;
 		return "usage: java VolumeCalculator length width heigh. VolumeCalculator.java: error: the following arguments are required: width height";
 	}

 	public String missingArgumentHeightMessage(){
 		 missingArgumentHeightMessage++;
 		return "usage: java VolumeCalculator length width heigh. VolumeCalculator.java: error: the following arguments are required: width height";
 	}

 	public String unrecognizedArgumentsMessage(){
 		unrecognizedArgumentsMessage++;
 		return "usage: java VolumeCalculator length width height" + "\n" + "VolumeCalcultor.java: error: unrecognized arguments: " + temp.getUnrecognizedArgument();
 	}

 	public String incorrectDataTypeMessage(){
 		incorrectDataTypeMessage++;
 		return "usage: java VolumeCalculator length width height\nVolumeCalcultor.java: error: argument width: invalid float value: " + temp.getIncorrectDataType();
 	}

 	public boolean isHelpMessageCalled(){
 		if(helpMessage>0){
 			helpMessage=0;
 			return true;
 		}
 		else
 			return false;
 	}

 	public boolean isMissingArgumentWidthAndHeightMessageCalled(){
		if(missingArgumentWidthAndHeightMessage>0){
 			return true;
 		}
 		else
 			return false;
 	}

 	public boolean isMissingArgumentHeightMessageCalled(){
 		if(missingArgumentHeightMessage>0){
 			return true;
 		}
 		else
 			return false;
 	}

 	public boolean isUnrecognizedArgumentsMessageCalled(){
 		if(unrecognizedArgumentsMessage>0){
 			return true;
 		}
 		else
 			return false;
 	}

 	public boolean isIncorrectDataTypeMessageCalled(){
 		if(incorrectDataTypeMessage>0){
 			return true;
 		}

 		else
 			return false;
 	}
}
