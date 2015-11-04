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

  private Map<String,Argument> positionalArguments;
  private Map<String,Argument> namedArguments;
  private List<String> userPositionalArguments;
  private List<String> userNamedArguments;
  private String name;
  private String programName;
  private String output="";
  private String helpMessageArguments="";
  private String[] helpMessageArgumentsArray;
  private String programDescription;
	private Boolean helpMessageCalled;

	public ArgumentParser(){
		this.namedArguments=new LinkedHashMap<String,Argument>();
		this.positionalArguments=new LinkedHashMap<String,Argument>();
		this.userPositionalArguments=new ArrayList<String>();
		this.userNamedArguments=new ArrayList<String>();
		this.helpMessageCalled = false;
	}

	public void addPositionalArgument(String x){
		Argument temp = new Argument();
		temp.setType(Argument.Type.STRING);
		temp.setPositionalName(x);
    helpMessageArguments = helpMessageArguments + " [" + x + "]";
		positionalArguments.put(temp.getPositionalName(),temp);
	}

	public void addPositionalArgument(String x,Argument.Type t){
		Argument temp = new Argument();
		temp.setType(t);
		temp.setPositionalName(x);
    helpMessageArguments = helpMessageArguments + " [" + x + "]";
		positionalArguments.put(temp.getPositionalName(),temp);
	}

	public void addNamedArgument(String x, Argument.Type t, String value){
		Argument temp = new Argument();
		temp.setnamedArgumentName(x);
		temp.setType(t);
		temp.setNamedArgumentValue(value);
		namedArguments.put(x,temp);
	}

	private void checkUserInputSize(){
		if(userPositionalArguments.size()==1 && (!userPositionalArguments.contains("--h") && !userPositionalArguments.contains("-h"))){
				throw new MissingArgumentException("usage: java VolumeCalculator length width heigh. VolumeCalculator.java: error: the following arguments are required: width height");
		}
		else if(userPositionalArguments.size()==2){
			throw new MissingArgumentException("usage: java VolumeCalculator length width heigh. VolumeCalculator.java: error: the following arguments are required: width height");
		}
		else if(userPositionalArguments.size()>positionalArguments.size()){
				for(int m=3; m < userPositionalArguments.size(); m++){
					Argument temp= new Argument();
					temp.setUnrecognizedArgument(userPositionalArguments.get(m));
					throw new UnknownArgumentException("usage: java VolumeCalculator length width height" + "\n" + "VolumeCalcultor.java: error: unrecognized arguments: " + temp.getUnrecognizedArgument());
				}
		 }
	}

	private void matchNamedArguments(){
	
		for(String s: namedArguments.keySet()){
            Argument temp=namedArguments.get(s);
            for(int i=0; i < userNamedArguments.size(); i++){
           		if(temp.getnamedArgumentName()==userNamedArguments.get(i)){
                	temp.setNamedArgumentValue(userNamedArguments.get(i+1));
                	namedArguments.put(s,temp);
            	}
            }
        }
            
            
		int j=1;
		for(int i=0; i < userNamedArguments.size(); i+=2){
			Argument temp2=new Argument();
			if((i % 2==0) && (j % 2==1)){
				temp2.setnamedArgumentName(userNamedArguments.get(i));
				temp2.setNamedArgumentValue(userNamedArguments.get(j));
				temp2.setDefaultValue(userNamedArguments.get(j));
				namedArguments.put(userNamedArguments.get(i),temp2);
				j+=2;
			}
		}
	}

	private void matchPositionalArguments(){
		int i=0;

	 	if(userPositionalArguments.size()==positionalArguments.size()){
			for(String s: positionalArguments.keySet()){
				Argument temp=positionalArguments.get(s);
				if(temp.getType()==Argument.Type.INT){
					temp.setPositionalValue(userPositionalArguments.get(i));
					try {
						Integer.parseInt(temp.getPositionalValue());
					}

          //used a for loop decreasing checking for the last going to the first
					catch(NumberFormatException ex) {
						temp.setIncorrectType(temp.getPositionalValue());
						throw new IncorrectDataTypeException("usage: java "+ getProgramName() + buildMissingArguments() + "\n" + getProgramName() + ": error: argument width: invalid Integer value: " + temp.getIncorrectType());
					}
					i++;
				}
				else if(temp.getType()==Argument.Type.FLOAT){
					temp.setPositionalValue(userPositionalArguments.get(i));
					try {
						Float.parseFloat(temp.getPositionalValue());
					}
					catch(NumberFormatException ex){
						temp.setIncorrectType(temp.getPositionalValue());
						throw new IncorrectDataTypeException("usage: java "+ getProgramName() +" "+ buildMissingArguments() + "\n" + getProgramName() + ".java: error: argument width: invalid float value: " + temp.getIncorrectType());
					}
					i++;
				}
				else if(temp.getType()==Argument.Type.BOOLEAN){
					temp.setPositionalValue(userPositionalArguments.get(i));
					i++;
				}

				else{
					temp.setPositionalValue(userPositionalArguments.get(i));
					i++;
				}
			}
		}
	}

	public void parse(String[] args) {
		for(int i=0; i<args.length; i++){
			userPositionalArguments.add(args[i]);
		}
		if(userPositionalArguments.contains("--h") ||userPositionalArguments.contains("-h") || userPositionalArguments.contains("--help") ){
				userPositionalArguments.remove("--h");
				userPositionalArguments.remove("--help");
				userPositionalArguments.remove("-h");
				throw new HelpMessageException(getHelpMessage());
		}
		for(int j=0; j < userPositionalArguments.size(); j++){
			int k=0;
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
		if(positionalArguments.get(name) != null){
			Argument temp=new Argument();
			temp= positionalArguments.get(name);
			if(temp.getType()==Argument.Type.INT){
				int num=Integer.parseInt(temp.getPositionalValue());
				return (T) new Integer (num);
			}
			else if(temp.getType()==Argument.Type.FLOAT){
				float num=Float.parseFloat(temp.getPositionalValue());
				return (T) new Float (num);
			}
			else if(temp.getType()==Argument.Type.BOOLEAN){
				boolean num=Boolean.parseBoolean(temp.getPositionalValue());
				return (T) Boolean.valueOf(num);
			}
			else{
				return (T) new String(temp.getPositionalValue());
			}
		}
		/*
		 if(namedArguments.get(name) != null){
			Argument temp=new Argument();
			temp= namedArguments.get(name);
			if(temp.getType()==Argument.Type.INT){
				int num=Integer.parseInt(temp.getNamedArgumentValue());
				return (T) new Integer (num);
			}
			else if(temp.getType()==Argument.Type.FLOAT){
				float num=Float.parseFloat(temp.getNamedArgumentValue());
				return (T) new Float (num);
			}
			else if(temp.getType()==Argument.Type.BOOLEAN){
				boolean num=Boolean.parseBoolean(temp.getNamedArgumentValue());
				return (T) Boolean.valueOf(num);
			}
			else{
				return (T) new String(temp.getNamedArgumentValue());
			}
		  }
		  
		  */
		
		
		
			
			for(int i=0; i < userNamedArguments.size(); i++){
				if(userNamedArguments.get(i).equals(name.substring(0,1).toLowerCase()) || userNamedArguments.get(i).equals(name.substring(0,1)) || userNamedArguments.get(i).equals(name)){
        			output=userNamedArguments.get(i+1);
				}
			}
    	return (T) new String(output);
	}

 	protected int getSizeOfHashMap(){
 		return positionalArguments.size();
 	}

	protected String getProgramName(){
		return programName;
	}

	public void assignProgramName(String name){
		programName = name;
	}

	public void assignProgramDescription(String description) {
		programDescription = description;
	}

	protected String getProgramDescription() {
		return programDescription;
	}

  private String buildMissingArguments() {
    this.helpMessageArgumentsArray = helpMessageArguments.split("\\s+");
    String missingArguments = "";
    for (int i = 0; i < helpMessageArgumentsArray.length; i++){
      missingArguments = missingArguments + helpMessageArgumentsArray[i].toString();
    }
    return missingArguments;
  }

  protected String getMissingArguments() {
    return buildMissingArguments();
  }

 	public String getHelpMessage(){
    //"("+ argumentType.toString() + ").";
 		return "usage: java " + getProgramName() +" " + buildMissingArguments() + "\n"  + getProgramDescription() + "\n" + "positional arguments:" + "\n" +   "length the length of the box (float)"  + "\n" + "width the width of the box(float)" + "\n" + "height the height of the box(float)";
 	}

 	public boolean isHelpMessageCalled(){
 		return helpMessageCalled;
 	}
}
