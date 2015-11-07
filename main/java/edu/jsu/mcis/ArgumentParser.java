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

  private Map<String, Argument> arguments;
  private List<String> positionalList;
  private String programName;
  private String programDescription;

	public ArgumentParser(){
		arguments = new LinkedHashMap<String,Argument>();
		positionalList = new ArrayList<String>();
	}

	public void addPositionalArgument(String x){
    addPositionalArgument(x, Argument.Type.STRING, "");
	}

	public void addPositionalArgument(String x, Argument.Type t) {
    addPositionalArgument(x, t, "");
	}

  public void addPositionalArgument(String name, Argument.Type type, String description) {
  	positionalList.add(name);
    PositionalArgument temp = new PositionalArgument();
    temp.setType(type);
    temp.setName(name);
    temp.setDescription(description);
    arguments.put(name, temp);
  }

	public void addNamedArgument(String name,Argument.Type type, String defaultValue) {
		Argument temp = new Argument();
		temp.setName(name);
		temp.setType(type);
		temp.setValue(defaultValue);
		arguments.put(name, temp);
	}

  public void parse(String[] args) {
    List<String> userArgs = new ArrayList<String>();
    List<String> posArgs= new ArrayList<String>();
    	for(int i=0; i<args.length; i++){
    		userArgs.add(args[i]);
    	}
		if(userArgs.contains("-h") || userArgs.contains("--help") ){
				userArgs.remove("--help");
				userArgs.remove("-h");
        		System.out.println(getHelpMessage());
       		 	System.exit(0);
				//throw new HelpMessageException(getHelpMessage());
		}
		System.out.println(userArgs);
		for(int i = 0; i < userArgs.size(); i++) {
			
      		String arg = userArgs.get(i);
      		if(arg.startsWith("--") || arg.startsWith("-")) {
      			System.out.println(arg);
        		String name = (arg.startsWith("--"))? arg.substring(2) : arg.substring(1);
        		Argument a = arguments.get(name);
        		// If boolean argument, then don't get the next value
        		a.setValue(userArgs.get(i+1));
        		arguments.put(userArgs.get(i),a);
        		userArgs.set(i, "");
				userArgs.set(i+1, "");
				
        		
			}
			
        }
        
        for(int k=0; k< userArgs.size(); k++){
			if(userArgs.get(k)!=""){
			posArgs.add(userArgs.get(k));
			}
		}
		
			for(int i=0; i < positionalList.size(); i++){
				for(int j=0; j<posArgs.size(); j++){
					String name=positionalList.get(i);
					Argument a=arguments.get(name);
					a.setValue(posArgs.get(i));
					arguments.put(name,a);
				}
		
			}
		
		System.out.println(userArgs);
		System.out.println(posArgs);
		checkUserInputSize(posArgs);
		//matchPositionalArguments(posArgs);
		
		
		
		
		//matchNamedArguments();
	}

  public String getHelpMessage() {
 		return "usage: java " + programName + " " + buildArgumentUsage() + "\n" +
            programDescription + "\n" + "positional arguments:\n" +
            buildPositionalArguments();
 	}

  private String buildArgumentUsage() {
    return "";
  }

  private String buildPositionalArguments() {
    return "";
  }

	@SuppressWarnings("unchecked")
	public <T> T getValue(String name){
    Argument arg = arguments.get(name);
		if(arg != null) {
      	String val = arg.getValue();
			if(arg.getType()==Argument.Type.INT){
				int num=Integer.parseInt(val);
				return (T) new Integer (num);
			}
			else if(arg.getType()==Argument.Type.FLOAT){
				float num=Float.parseFloat(val);
				return (T) new Float (num);
			}
			else if(arg.getType()==Argument.Type.BOOLEAN){
				boolean num=Boolean.parseBoolean(val);
				return (T) Boolean.valueOf(num);
			}
			else{
				return (T) new String(val);
			}
		}
    	else {
      		throw new UnknownArgumentException(name);
    	}
	}


	private void checkUserInputSize(List<String> list) {
		if(list.size()==1 && (!list.contains("--h") && !list.contains("-h"))){
				throw new MissingArgumentException("usage: java VolumeCalculator length width heigh. VolumeCalculator.java: error: the following arguments are required: width height");
		}
		else if(list.size()==2){
			throw new MissingArgumentException("usage: java VolumeCalculator length width heigh. VolumeCalculator.java: error: the following arguments are required: width height");
		}
		else if(list.size()>arguments.size()){
				for(int m=3; m < list.size(); m++){
					throw new UnknownArgumentException("usage: java VolumeCalculator length width height" + "\n" + "VolumeCalcultor.java: error: unrecognized arguments: " );
					}
		}

	}
	



 	protected int getSizeOfHashMap(){
 		return arguments.size();
 	}


	protected String getProgramName(){
		return programName;
	}

	public void assignProgramName(String name){
		programName = name;
	}

	
	public void assignProgramDescription(String description){
		programDescription = description;
	}
	
	protected String getProgramDescription(){
		return programDescription;
	
	}


  	private String buildMissingArguments() {
  	 	String helpMessageArguments="";
    	String[] helpMessageArgumentsArray;
    	helpMessageArgumentsArray = helpMessageArguments.split("\\s+");
    	String missingArguments = "";
    	for (int i = 0; i < helpMessageArgumentsArray.length; i++){
      		missingArguments = missingArguments + helpMessageArgumentsArray[i].toString();
    	}
    	return missingArguments;
  	}

  	protected String getMissingArguments() {
    	return buildMissingArguments();
  	}


}
