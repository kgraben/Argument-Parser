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
    PositionalArgument positionalTemp = new PositionalArgument();
    temp.setType(type);
    temp.setName(name);
    positionalTemp.setDescription(description);
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
		if(arguments.size()==1 && (!positionalList.contains("--h") && !positionalList.contains("-h"))){
				throw new MissingArgumentException("usage: java VolumeCalculator length width heigh. VolumeCalculator.java: error: the following arguments are required: width height");
		}
		else if(arguments.size()==2){
			throw new MissingArgumentException("usage: java VolumeCalculator length width heigh. VolumeCalculator.java: error: the following arguments are required: width height");
		}
		else if(arguments.size()>positionalList.size()){
				for(int m=3; m < arguments.size(); m++){
					List<String> unrecognizedArguments = new ArrayList<String>();
					throw new UnknownArgumentException("usage: java "+ programName +
                    buildArgumentUsage() + "\n" + programName +
                    ".java: error: unrecognized arguments: "); //+ unrecognized args );
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

	 	if(arguments.size() == arguments.size()){
			for(String s: arguments.keySet()){
				Argument temp = arguments.get(s);
				if(temp.getType()==Argument.Type.INT){
					temp.setValue(arguments.get(i));
					try {
						Integer.parseInt(temp.getValue());
					}

          //others used a for loop (decreasing) checking for the last going to the first
					catch(NumberFormatException ex) {
            List<String> incorrectType = new ArrayList<String>();
            incorrectType.add(temp.getValue());
						throw new IncorrectDataTypeException("usage: java "+ getProgramName() + buildArgumentUsage() + "\n" + getProgramName() + ": error: argument width: invalid "+ temp.getType() + " value: " + incorrectType.toString());
					}
					i++;
				}
				else if(temp.getType()==Argument.Type.FLOAT){
					temp.setValue(arguments.get(i));
					try {
						Float.parseFloat(temp.getValue());
					}
					catch(NumberFormatException ex){
						temp.setIncorrectType(temp.getPositionalValue());
						throw new IncorrectDataTypeException("usage: java "+ getProgramName() +" "+ buildArgumentUsage() + "\n" + getProgramName() + ".java: error: argument width: invalid float value: " + temp.getIncorrectType());
					}
					i++;
				}
				else if(temp.getType()==Argument.Type.BOOLEAN){
					temp.setValue(arguments.get(i));
					i++;
				}

				else{
					temp.setValue(arguments.get(i));
					i++;
				}
			}
		}
	}

	protected String getProgramName(){
		return programName;
	}

	public void assignProgramName(String name){
		programName = name;
	}
}
