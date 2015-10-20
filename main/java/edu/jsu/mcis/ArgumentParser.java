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
		namedArguments=new LinkedHashMap<String,Argument>();
		positionalArguments=new LinkedHashMap<String,Argument>();
		userPositionalArguments=new ArrayList<String>();
		userNamedArguments=new ArrayList<String>();
		helpMessage=0;
		missingArgumentWidthAndHeightMessage=0;
		missingArgumentHeightMessage=0;
		unrecognizedArgumentsMessage=0;
		incorrectDataTypeMessage=0;
	}

	public void addPositionalArgument(String x){
		Argument temp = new Argument();
		temp.setDataType(Argument.DATATYPE.STRING);
		temp.setPositionalName(x);
		positionalArguments.put(x,temp);
	}

	public void addPositionalArgument(String x,DATATYPE t){
		Argument temp = new Argument();
		temp.setDataType(t);
		temp.setPositionalName(x);
		positionalArguments.put(x,temp);
	}

	public void addNamedArgument(String x){
		Argument temp = new Argument();
		temp.setnamedName(x);
		namedArguments.put(x,temp);
	}

	public void checkUserInputSize() throws ArrayIndexOutOfBoundsException{
		if(userPositionalArguments.size()==1 && (!userPositionalArguments.contains("--h") && !userPositionalArguments.contains("-h"))){
			try{
				missingArgumentWidthAndHeightMessage();
				}
			catch(NullPointerException ex){

			}


		}



		else if(userPositionalArguments.size()==2){
			try{
				 missingArgumentHeightMessage();
			}
			catch(NullPointerException ex){

			}
		}
		else if(userPositionalArguments.size()>positionalArguments.size()){
				for(int m=3; m < userPositionalArguments.size(); m++){
					//temp.setUnrecognizedArgument(userPositionalArguments.get(m));
					try{
						 unrecognizedArgumentsMessage();
				    	System.out.println("usage: java VolumeCalculator length width height" + "\n" + "VolumeCalcultor.java: error: unrecognized arguments: " + userPositionalArguments.get(m));
				    }
				    catch(NullPointerException ex){

				    }
				}

		 }
	}

	public void matchNamedArguments(){
		for(String s: namedArguments.keySet()){
			Argument temp=namedArguments.get(s);

				if(temp.getnamedName()=="Type"){
					String box="Box";
					temp.setNamedValue(box);
					namedArguments.put(s,temp);

				}

				else if(temp.getnamedName()=="Digits"){
					String digit="4";
					temp.setNamedValue(digit);
					namedArguments.put(s,temp);
				}
			}
					int j=1;
					for(int i=0; i < userNamedArguments.size(); i+=2){
						Argument temp2=new Argument();
						if((i % 2==0) && (j % 2==1)){
							temp2.setNamedValue(userNamedArguments.get(j));
							namedArguments.put(userNamedArguments.get(i),temp2);
							j+=2;
						}
					}
	}

	public void matchPositionalArguments(){
		int i=0;

			 if(userPositionalArguments.size()==positionalArguments.size()){


					for(String s: positionalArguments.keySet()){
						Argument temp=positionalArguments.get(s);

						if(temp.getDataType()==Argument.DATATYPE.INT){
							temp.setPositionalValue(userPositionalArguments.get(i));
							try{

								Integer.parseInt(temp.getPositionalValue());
							}
							catch(NumberFormatException ex){
								temp.setIncorrectDataType(temp.getPositionalValue());
								incorrectDataTypeMessage();
							}

							i++;
						}

						else if(temp.getDataType()==Argument.DATATYPE.FLOAT){
							temp.setPositionalValue(userPositionalArguments.get(i));

							try{

								Float.parseFloat(temp.getPositionalValue());

							}
							catch(NumberFormatException ex){
								temp.setIncorrectDataType(temp.getPositionalValue());
								incorrectDataTypeMessage();

							i++;
						}

						else if(temp.getDataType()==Argument.DATATYPE.BOOLEAN){
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

   		if(positionalArguments.get(name) != null){
   			Argument temp=new Argument();
   			temp= positionalArguments.get(name);
   			if(temp.getDataType()==Argument.DATATYPE.INT){
   				int num=Integer.parseInt(temp.getPositionalValue());
   				return (T) new Integer (num);
   			}

   			else if(temp.getDataType()==Argument.DATATYPE.FLOAT){
   				float num=Float.parseFloat(temp.getPositionalValue());
   				return (T) new Float (num);
   			}

   			else if(temp.getDataType()==Argument.DATATYPE.BOOLEAN){
   				boolean num=Boolean.parseBoolean(temp.getPositionalValue());
   				return (T) Boolean.valueOf(num);
   			}
   			else{
   				return (T) new String(temp.getPositionalValue());
   			}
   		}


   			Argument temp= new Argument();
   			temp=namedArguments.get(name);
   			return (T) temp.getNamedValue();
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
