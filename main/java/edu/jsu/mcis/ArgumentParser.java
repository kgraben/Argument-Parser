/*This is the first file
By: Christopher Burdette
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
    private String fileName;

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

	public void addNamedArgument(String name, String shortName, Argument.Type type, String defaultValue) {
		Argument temp = new Argument();
		temp.setName(name);
		temp.setShortName(shortName);
		temp.setType(type);
		temp.setValue(defaultValue);
		arguments.put(name, temp);
		arguments.put(shortName,temp);
	}

	public void parse(String[] args) {
		List<String> userArgs = new ArrayList<String>();
        for(int i=0; i<args.length; i++){
            userArgs.add(args[i]);
        }
		if(userArgs.contains("-h") || userArgs.contains("--help") ){
			userArgs.remove("--help");
			userArgs.remove("-h");
			System.out.println(getHelpMessage());
			throw new HelpMessageException(getHelpMessage());
		}
		for(int i = 0; i < userArgs.size(); i++) {
			String arg = userArgs.get(i);
            if(arg.startsWith("--") || arg.startsWith("-")) {
                String name = (arg.startsWith("--"))? arg.substring(2) : arg.substring(1);
                if(name.equals("h")){
                    userArgs.remove("h");
                    throw new HelpMessageException(getHelpMessage());
                }
                Argument a = arguments.get(name);
                a.setValue(userArgs.get(i+1));
                arguments.put(userArgs.get(i),a);
                userArgs.set(i, "");
                userArgs.set(i+1, "");
			}
   		}
    	List<String> posArgs = new ArrayList<String>();
    	for(int k = 0; k< userArgs.size(); k++){
			if(userArgs.get(k)!= ""){
				posArgs.add(userArgs.get(k));
			}
		}
		checkUserDataType(posArgs);
		checkUserInputSize(posArgs);
	}

  	public String getHelpMessage() {
 		return "usage: java " + programName + " " + buildArgumentUsage() + "\n" +
            programDescription + "\n" + "positional arguments:\n" +
            buildPositionalArguments();
 	}

    private String buildArgumentUsage() {
        String s = "";
        for(String name : arguments.keySet()) {
            s += "[" + name + "]";
        }
        return s;
    }

	protected String getMissingArguments() {
	   return buildArgumentUsage();
    }

	private String buildPositionalArguments() {
        String s = "";
        for(String name : arguments.keySet()) {
            Argument a = arguments.get(name);
            s += a.getName() + " " + a.getDescription() + " (" + a.getType() + ")\n";
        }
        return s.substring(0, s.length()-1);
    }

	@SuppressWarnings("unchecked")
	public <T> T getValue(String name){
        Argument arg = arguments.get(name);
		if(arg != null) {
            String val = arg.getValue();
			if(arg.getType()==Argument.Type.INT){
				int num = Integer.parseInt(val);
				return (T) new Integer (num);
			}
			else if(arg.getType()==Argument.Type.FLOAT){
				float num = Float.parseFloat(val);
				return (T) new Float (num);
			}
			else if(arg.getType()==Argument.Type.BOOLEAN){
				boolean num = Boolean.parseBoolean(val);
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
		if(list.size()==1){
			throw new MissingArgumentException("usage: java VolumeCalculator length width heigh. VolumeCalculator.java: error: the following arguments are required: width height");
		}
		else if(list.size()==2){
			throw new MissingArgumentException("usage: java VolumeCalculator length width heigh. VolumeCalculator.java: error: the following arguments are required: width height");
		}
	}

	private void checkUserDataType(List<String> list){
		for(int i = 0; i < list.size(); i++) {
            try {
                String positional = positionalList.get(i);
                for(String name : arguments.keySet()) {
                    Argument a = arguments.get(name);
                    if(positional == a.getName()) {
                        a.setValue(list.get(i));
                        if(a.getType() == Argument.Type.INT){
                            try {
                                int num = Integer.parseInt(a.getValue());
                                arguments.put(name,a);
                            }
                            catch(NumberFormatException e) {
                                throw new IncorrectDataTypeException(getIncorrectDataTypeMessage(name, list.get(i), a.getType().toString()));
                            }
                        }
                        else if(a.getType()==Argument.Type.FLOAT){
                            try {
                                float num = Float.parseFloat(a.getValue());
                                arguments.put(name,a);
                            }
                            catch(NumberFormatException e){
                                throw new IncorrectDataTypeException(getIncorrectDataTypeMessage(name, list.get(i), a.getType().toString()));
                            }
                        }
                        else if(a.getType()==Argument.Type.BOOLEAN){
                            try{
                                boolean num = Boolean.parseBoolean(a.getValue());
                                arguments.put(name,a);
                            }
                            catch(NumberFormatException e){
                                throw new IncorrectDataTypeException(getIncorrectDataTypeMessage(name, list.get(i), a.getType().toString()));
                            }
                        }
                    }
                }
            }
            catch(IndexOutOfBoundsException e){
                throw new UnknownArgumentException("usage: java VolumeCalculator [length][width][height]\nVolumeCalculator.java: error: unrecognized arguments: " + list.get(i));
            }
        }
    }

 	private String getIncorrectDataTypeMessage(String argName, String incorrectValue, String type) {
        return "usage: java " + programName + " " + buildArgumentUsage() + "\n" +
        programName + ".java: error: argument " + argName + ": invalid " + type
        + " value: " + incorrectValue;
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
}
