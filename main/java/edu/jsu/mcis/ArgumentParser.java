package edu.jsu.mcis;

import java.util.*;
import java.io.*;
import java.lang.*;

/**
*
* ArgumentParser is a library for taking command line arguments and returning
* user defined values. ArgumentParser can take positional Arguments and named Arguments.
* ArgumentParser also has the capablility of loading and
* saving arguments from an XML file. Below is an example of the positional Arguments.
* <br>
* <br>
* {@code ArgumentParser ap = new ArgumentParser();} <br>
* {@code ap.addPositionalArgument(name, Argument.type, description);} <br>
* <br>
* <br>
* Named Arguments can have double dash(--) or single dash(-) in front
* of the name the user enters.
* <br>
* <br>
* {@code ArgumentParser ap = new ArgumentParser();} <br>
* {@code ap.addNamedArgument(name, shortName, Argument.type, defaultValue);} <br>
* <br>
* <br>
* The parse() method is suppose to be called, after adding all the arguments. The parse()
* method job is to store all the user arguments.
* <br>
* <br>
* {@code ArgumentParser ap = new ArgumentParser();} <br>
* {@code ap.addPositionalArgument(name, Argument.type, description);} <br>
* {@code ap.addNamedArgument(name, shortName, Argument.type, description);} <br>
* {@code ap.parse(userInput);} <br>
* <br>
* <br>
* In order for to get the user input data the getValue() method should be called.
* The getValue() method returns the value based on the generic type.
* Below is a complete example of how ArgumentParser() works.
* <br>
* <br>
* {@code ArgumentParser ap = new ArgumentParser();} <br>
* {@code ap.addPositionalArgument("length", Argument.type.FLOAT, "This is the length of the object");} <br>
* {@code ap.addPositionalArgument("width", Argument.type.FLOAT, "This is the width of the object");} <br>
* {@code ap.addPositionalArgument("height", Argument.type.FLOAT, "This is the height of the object");} <br>
* {@code ap.addNamedArgument("Pizza", "p", Argument.type.STRING, "pepperoni");} <br>
* {@code String[] userArray = { "5", "--Pizza", "cheese", "7", "8" };} <br>
* {@code ap.parse(userArray);} <br>
* {@code float length = ap.getValue("length");} <br>
* {@code String pizza = ap.getValue("Pizza");} <br>
*
* <br>
* <br>
*
* @author {TeamName}
* @author Christopher Burdette (cbgithub)
* @author Kurtis Graben (kurtdawg24)
* @author Khoi Phan (kphanjsu)
* @author Hui Wang (wanghuida0)
*
*/
public class ArgumentParser {

  private Map<String, Argument> arguments;
  protected Map<String, NamedArgument> namedArguments;
  private List<String> positionalList;
  private List<String> nameList;
  private String programName;
  private String programDescription;
  private String fileName;


	public ArgumentParser() {
    	arguments = new LinkedHashMap<String,Argument>();
   		namedArguments = new LinkedHashMap<String,NamedArgument>();
    	positionalList = new ArrayList<String>();
  	}


  /**
  * Adds the positional argument "name" to the hashmap
  *
  * @param name Takes in the name
  */
	public void addPositionalArgument(String name) {
    addPositionalArgument(name, Argument.Type.STRING, "");
	}

  /**
  * Adds the positional argument "name" and "type" to the hashmap
  *
  * @param name Takes in the name
  * @param type Takes in the type
  */
	public void addPositionalArgument(String name, Argument.Type type) {
    addPositionalArgument(name, type, "");
	}

  /**
  * Adds the positional argument "name", "type" and "description" to the hashmap
  *
  * @param name Takes in the name
  * @param type Takes in the type
  * @param description Takes in the description
  */
	public void addPositionalArgument(String name, Argument.Type type, String description) {
		positionalList.add(name);
  		Argument temp = new Argument();
  		temp.setType(type);
  		temp.setName(name);
  		temp.setDescription(description);
  		arguments.put(name, temp);
	}


	public void addNamedArgument(String name, String shortName, Argument.Type type, String defaultValue) {
		NamedArgument temp = new NamedArgument(name);
		temp.setShortName(shortName);
		temp.setType(type);
		temp.setValue(defaultValue);
		arguments.put(name, temp);
		arguments.put(shortName,temp);
		namedArguments.put(name, temp);

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

    				if(a==null){
    					throw new UnknownArgumentException(name);
    				}

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


	private String getHelpMessage() {
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
    if(list.size() == 1) {
      throw new MissingArgumentException(getMissingArgumentsMessage(list.size(), positionalList));
    }
    else if(list.size() == 2) {
      throw new MissingArgumentException(getMissingArgumentsMessage(list.size(), positionalList));
    }
  }

  private String getMissingArgumentsMessage(int size, List<String> list){
    String missingArguments = "";
    for (int i = size; i < list.size(); i++){
      missingArguments = missingArguments + " " + list.get(i).toString();
    }
    return "usage: java " + programName + " " + buildArgumentUsage() + "\n" +
    programName + ".java error: The following arguments are required " +
    missingArguments;
  }

  private void checkUserDataType(List<String> list){
    for(int i = 0; i < list.size(); i++) {
      try {
        String positional = positionalList.get(i);
        for(String name : arguments.keySet()) {
          Argument a = arguments.get(name);
          if(positional == a.getName()) {
            a.setValue(list.get(i));
            if(a.getType() == Argument.Type.INT) {
              try {
                int num = Integer.parseInt(a.getValue());
                arguments.put(name,a);
              }
              catch(NumberFormatException e) {
                throw new IncorrectDataTypeException(getIncorrectDataTypeMessage(name, list.get(i), a.getType().toString()));
              }
            }
            else if(a.getType() == Argument.Type.FLOAT) {
              try {
                float num = Float.parseFloat(a.getValue());
                arguments.put(name,a);
              }
              catch(NumberFormatException e){
                throw new IncorrectDataTypeException(getIncorrectDataTypeMessage(name, list.get(i), a.getType().toString()));
              }
            }


          }
        }
      }
      catch(IndexOutOfBoundsException e) {
        throw new UnknownArgumentException(getUnknownArgumentsMessage(list.get(i)).toString());
      }
    }
  }

  private String getUnknownArgumentsMessage(String unknownArgument) {
    return "usage: java " + programName + " " + buildArgumentUsage() +
    "\n" + programName + ".java: error: unrecognized arguments: " + unknownArgument;
  }

  private String getIncorrectDataTypeMessage(String argName, String incorrectValue, String type) {
    return "usage: java " + programName + " " + buildArgumentUsage() + "\n" +
    programName + ".java: error: argument " + argName + ": invalid " + type
    + " value: " + incorrectValue;
  }


  protected List<String> getPositionalList(){
  	return positionalList;
  }


  protected Map<String,Argument> getArgument(){
  	return arguments;
  }

   protected Map<String,NamedArgument> getNamedArgument(){
  	return namedArguments;
  }

  protected String getProgramName() {
    return programName;
  }

  public void assignProgramName(String name) {
    programName = name;
  }

  public void assignProgramDescription(String description) {
    programDescription = description;
  }

  protected String getProgramDescription() {
    return programDescription;
  }

}
