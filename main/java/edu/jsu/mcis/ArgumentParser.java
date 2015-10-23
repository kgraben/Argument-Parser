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
    private String name;
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
		name="";
	}
	
	public void addPositionalArgument(String x){
		Argument temp = new Argument();
		temp.setDataType(Argument.DATATYPE.STRING);
		temp.setPositionalName(x);
		positionalArguments.put(temp.getPositionalName(),temp);
		
	}


	
	public void addPositionalArgument(String x,Argument.DATATYPE t){
		Argument temp = new Argument();
		temp.setDataType(t);
		temp.setPositionalName(x);
		positionalArguments.put(temp.getPositionalName(),temp);
		
	}
	
	
	public void addNamedArgument(String x){
		Argument temp = new Argument();
		temp.setnamedArgumentName(x);
		namedArguments.put(x,temp);
	
	}
	// This is to make the unit test compile
	public void addFlag(String flag){
		addNamedArgument(flag);
	}
	
	
	
	
	public void checkUserInputSize(){
		
		if(userPositionalArguments.size()==1 && (!userPositionalArguments.contains("--h") && !userPositionalArguments.contains("-h"))){
				throw new MissingArgumentException("usage: java VolumeCalculator length width heigh. VolumeCalculator.java: error: the following arguments are required: width height");
			
			
			
		}
		
		
			
		else if(userPositionalArguments.size()==2){
			throw new MissingArgumentException("usage: java VolumeCalculator length width heigh. VolumeCalculator.java: error: the following arguments are required: width height");
		}
		else if(userPositionalArguments.size()>positionalArguments.size()){
			unrecognizedArgumentsMessage++;
			//unrecognizedArgumentsMessage();
				for(int m=3; m < userPositionalArguments.size(); m++){
					Argument temp= new Argument();
					temp.setUnrecognizedArgument(userPositionalArguments.get(m));	
					
					throw new UnknownArgumentException("usage: java VolumeCalculator length width height" + "\n" + "VolumeCalcultor.java: error: unrecognized arguments: " + temp.getUnrecognizedArgument());
				   
				}
				
		 }
		
	}
	
	
	public void matchNamedArguments(){
	
		for(String s: namedArguments.keySet()){
			Argument temp=namedArguments.get(s);
			
				if(temp.getnamedArgumentName()=="Type"){
					String box="Box";
					temp.setNamedArgumentValue(box);
					namedArguments.put(s,temp);
			
				}
			
				else if(temp.getnamedArgumentName()=="Digits"){
					String digit="4";
					temp.setNamedArgumentValue(digit);
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
							temp2.setNamedArgumentValue(userNamedArguments.get(j));
							namedArguments.put(userNamedArguments.get(i),temp2);
							j+=2;
						}
					}
					
					
				
					
					
					
				
			
			
			
			
			
		
	
	
	
	
	}

	
	
	
	
	
	public void matchPositionalArguments (){
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
								//incorrectDataTypeMessage();
								incorrectDataTypeMessage++;
								temp.setIncorrectDataType(temp.getPositionalValue());
								setIncorrectDataTypeMessage(temp.getIncorrectDataType());
								throw new IncorrectDataTypeException("usage: java VolumeCalculator length width height\nVolumeCalcultor.java: error: argument width: invalid Integer value: " + temp.getIncorrectDataType());
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
								//incorrectDataTypeMessage();
								incorrectDataTypeMessage++;
								temp.setIncorrectDataType(temp.getPositionalValue());
								setIncorrectDataTypeMessage(temp.getIncorrectDataType());
								throw new IncorrectDataTypeException("usage: java VolumeCalculator length width height\nVolumeCalcultor.java: error: argument width: invalid float value: " + temp.getIncorrectDataType());
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
		if(userPositionalArguments.contains("--h") ||userPositionalArguments.contains("-h") || userPositionalArguments.contains("--help") ){
				System.out.println(getHelpMessage());
				//System.out.println(userPositionalArguments);
				
				userPositionalArguments.remove("--h");
				userPositionalArguments.remove("--help");
				userPositionalArguments.remove("-h");
				//System.out.println(userPositionalArguments);
				
		}
		
		
			for(int j=0; j < userPositionalArguments.size(); j++){
		
				int k=0;
	
		
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
   			return (T) temp.getNamedArgumentValue();
   		
   		
   		}
   		
   
   			

   	
   	
   	public int getSizeOfHashMap(){
   		return positionalArguments.size();
   	}
   	
   
   	
   	
   	
   	public String getHelpMessage(){
   		helpMessage++;
   		return "usage: java VolumeCalculator length width height" + "\n" + "Calcuate the volume of a box." + "\n" + "positional arguments:" + "\n" +   "length the length of the box (float)"  + "\n" +   "width the width of the box(float)" + "\n" + "height the height of the box(float)";	
   	}
   	
   	
   
   	
   	public String unrecognizedArgumentsMessage(){
   		return "usage: java VolumeCalculator length width height" + "\n" + "VolumeCalcultor.java: error: unrecognized arguments: " + userPositionalArguments.get(3);
   	
   	}
   	
   	public void setIncorrectDataTypeMessage(String s){
   		name=s;
   	}
   	
   	public String getIncorrectDataTypeMessage(){
   		return name;
   	}
   	
   	public String incorrectDataTypeMessage(){
   		
   		return "usage: java VolumeCalculator length width height\nVolumeCalcultor.java: error: argument width: invalid float value: " + getIncorrectDataTypeMessage();
   	}
   	
   
  
   	public boolean isHelpMessageCalled(){
   		if(helpMessage>0){
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
