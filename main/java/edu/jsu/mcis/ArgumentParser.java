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
    Argument temp = new Argument();
    temp.setType(type);
    temp.setName(name);
    temp.setDescription(description);
    arguments.put(name, temp);
  }

	public void addNamedArgument(String name) {
		Argument temp = new Argument();
		temp.setName(name);
		arguments.put(name, temp);
	}

  public void parse(String[] args) {
    List<String> userArgs = Arrays.asList(args);
		if(userArgs.contains("-h") || userArgs.contains("--help") ){
				userArgs.remove("--help");
				userArgs.remove("-h");
        System.out.println(getHelpMessage());
        System.exit(0);
				//throw new HelpMessageException(getHelpMessage());
		}
		for(int i = 0; i < userArgs.size(); i++) {
      String arg = userArgs.get(i);
			if(arg.startsWith("--") || arg.startsWith("-")) {
        String name = (arg.startsWith("--"))? arg.substring(2) : arg.substring(1);
        Argument a = arguments.get(name);
        // If boolean argument, then don't get the next value
        a.setValue(userArgs.get(++i));
			}
		}
		checkUserInputSize();
		matchPositionalArguments();
		matchNamedArguments();
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

	private void checkUserInputSize() {
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
		int j=1;
		for(int i=0; i < userNamedArguments.size(); i+=2){
			Argument temp2=new Argument();
			if((i % 2==0) && (j % 2==1)){
				temp2.setnamedArgumentName(userNamedArguments.get(i));
				temp2.setNamedArgumentValue(userNamedArguments.get(j));
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

          //others used a for loop (decreasing) checking for the last going to the first
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


 	protected int getSizeOfHashMap(){
 		return positionalArguments.size();
 	}

	protected String getProgramName(){
		return programName;
	}

	public void assignProgramName(String name){
		programName = name;
	}

  private String buildMissingArguments() {
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


 	public boolean isHelpMessageCalled(){
 		return helpMessageCalled;
 	}
}
