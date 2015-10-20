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

	
	private LinkedHashMap<String,Argument> positionalArguments;
	private LinkedHashMap<String,Argument> namedArguments;
	private ArrayList<String> userPositionalArguments; //ex. user types in 7 5 3. goes into array like. < 7, 5, 2>;
	private ArrayList<String> userNamedArguments; //ex. user types in --Type Box --Digits 1. goes into array like. <--Type,Box,--Digits,1>;
    private Argument argument;
    private int helpMessage; //To check the amount of times the method getHelpMessage is called.
    private int missingArgumentHeightMessage;
    private int missingArgumentWidthAndHeightMessage;
    private int unrecognizedArgumentsMessage;
    private int incorrectDataTypeMessage;
    //private UserArgumentException temp; 
	
	public ArgumentParser(){
		namedArguments=new LinkedHashMap<String,Argument>();
		positionalArguments=new LinkedHashMap<String,Argument>();
		userPositionalArguments=new ArrayList<String>(); // stores the pos. Args
		userNamedArguments=new ArrayList<String>(); // stores the named. Args
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


	
	public void addPositionalArgument(String x,Argument.DATATYPE t){
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
		
			
			
			
			
			
		
		
				
			
			
		
				//Nested loop is not matching the named arguments right??? Need help
				//<color,blue,type,box,pizza,cheese>
				//Got it fixed @2:24am. Only problem now is the acceptance test. 
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
								//intArguments.put(s,argument.getPositionalValue());
							}
							catch(NumberFormatException ex){
								//UserArgumentException temp=new UserArgumentException();
								temp.setIncorrectDataType(temp.getPositionalValue());
								incorrectDataTypeMessage();
								//throw new IncorrectDataTypeException("usage: java VolumeCalculator length width height\nVolumeCalcultor.java: error: argument width: invalid Integer value: " + temp.getIncorrectDataType());
							}
							
							i++;	
						}
						
						else if(temp.getDataType()==Argument.DATATYPE.FLOAT){
							temp.setPositionalValue(userPositionalArguments.get(i));
							
							try{
							
								Float.parseFloat(temp.getPositionalValue());
								//floatArguments.put(s,argument.getPositionalValue());
							
							}
							catch(NumberFormatException ex){
								//UserArgumentException temp=new UserArgumentException();
								temp.setIncorrectDataType(temp.getPositionalValue());
								incorrectDataTypeMessage();
								//throw new IncorrectDataTypeException("usage: java VolumeCalculator length width height\nVolumeCalcultor.java: error: argument width: invalid float value: " + temp.getIncorrectDataType());
							}
							
							i++;
						}
							
						else if(temp.getDataType()==Argument.DATATYPE.BOOLEAN){
							temp.setPositionalValue(userPositionalArguments.get(i));
							//booleanArguments.put(s,argument.getPositionalArg());
							i++;
						}
						
						else{
							temp.setPositionalValue(userPositionalArguments.get(i));
							//stringArguments.put(s,argument.getPositionalArg());
							i++;
						}
					
						
						
						
						
						
					}
				}
	
	
	}
	


	public void parse(String[] args){
		//Major problem if userInput is: < "7", "2", "--Type", "square", "Digits", "2", "3">
		for(int i=0; i<args.length; i++){
			userPositionalArguments.add(args[i]);
		}
		//userPositionalArguments equals <"7","2", "--Type", "square", "Digits", "3"> can't add duplicates
		//Therefore we should probably use a LinkedList??? 
		
		for(int j=0; j < userPositionalArguments.size(); j++){
		
			int k=0;
		
			if(userPositionalArguments.contains("--h") ||userPositionalArguments.contains("-h") ){
				getHelpMessage();
				
				break;
				//userArg.remove("--h");
			}
		
			//finally got it working(11:45am). just changed if statement to while. Also I put the user arguments into an arraylist first.
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
   		
   
   			
   		/*
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
		*/	 		
   	
   	
   	
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
   		return "usage: java VolumeCalculator length width height" + "\n" + "VolumeCalcultor.java: error: unrecognized arguments: ";
   	
   	}
   	
   	public String incorrectDataTypeMessage(){
   		incorrectDataTypeMessage++;
   		return "usage: java VolumeCalculator length width height\nVolumeCalcultor.java: error: argument width: invalid float value: ";
   	
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
