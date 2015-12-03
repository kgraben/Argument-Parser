import java.util.*;
import edu.jsu.mcis.*;
import java.io.File;
import java.io.*;



public class BankAccount{
    public static void main(String[] args){
    	String username = "";
        System.out.println("Welcome to ScrumBags Bank");
        System.out.println();
	boolean run = true;
	while(run){
		System.out.println("Do you have an account? Y/N");
     		Scanner scanner = new Scanner(System.in);
		String stay = scanner.nextLine();
		System.out.println();
		if(stay.equals("y") || stay.equals("Y")){
			System.out.println("Enter your username:  ");
			String name = scanner.nextLine();
			System.out.println();
			System.out.println("Enter your password:  ");
			String password = scanner.nextLine();
			System.out.println();
			try{
				XML xml = new XML();
        			ArgumentParser ap = xml.loadXML( name + ".xml");
        			String[] data = { stay, name, password };
        			ap.parse(data);
        			if( name.equals(ap.getValue("username")) && 
        				password.equals(ap.getValue("userpassword"))){
        				System.out.println();
        				System.out.println("Welcome " + name + 
        				"," + " what would you like to do? ");
        				System.out.println();
        				boolean exit = false;
        				while(!exit){
        					System.out.println();
        					System.out.println("1. Check balance" + "\n" +
        								   "2. Withdraw" + "\n" +
        								   "3. Deposit"  + "\n" +
        								   "4. Get account info" + "\n" +
        								   "5. edit username/password" + "\n" +
        								   "6. exit");
        					System.out.println();
        					String choice = scanner.nextLine();
        					System.out.println();
        					if(choice.equals("1")){
        				  		System.out.println("Balance: "  + ap.getValue("balance"));
        				    	}
        					else if(choice.equals("2")){
        						System.out.println("How much you want to withdraw?");
        						String withdraw = scanner.nextLine();
        						String bal = ap.getValue("balance") + " ";
        						float total = Float.parseFloat(bal) - Float.parseFloat(withdraw);
        						ap.addNamedArgument("balance", "b", Argument.Type.FLOAT, 
        											total + " ");
								xml.saveXML( ap.getValue("username") + ".xml", ap);	
        					}
        					else if(choice.equals("3")){
        						System.out.println("How much you want to deposit?");
        						String deposit = scanner.nextLine();
        						String bal = ap.getValue("balance") + " ";
        						float total = Float.parseFloat(bal) + Float.parseFloat(deposit);
        						ap.addNamedArgument("balance", "b", Argument.Type.FLOAT, 
        											total + " ");
								xml.saveXML( ap.getValue("username") + ".xml", ap);	
        					}
        					else if(choice.equals("5")){
        						boolean file = new File((ap.getValue("username") + ".xml")).delete();
        						System.out.println("Enter new username : ");
        						username = scanner.nextLine();
        						System.out.println();
        						System.out.println("Enter new password : ");
        						String userpassword = scanner.nextLine();
        						System.out.println();
        						ap.addNamedArgument("username", "un", Argument.Type.STRING, username );
								ap.addNamedArgument("userpassword", "up", Argument.Type.STRING, userpassword);
        						xml.saveXML( username + ".xml", ap);
        					}
        					else if(choice.equals("4")){
        						System.out.println("Name: " + ap.getValue("name"));
        						System.out.println("Email: " + ap.getValue("email"));
        						System.out.println("Age:  " + ap.getValue("Age"));
        						System.out.println("Date of Birth:  " + ap.getValue("DOB"));
        						System.out.println("Username:  " + ap.getValue("username"));
        						System.out.println("Userpassword: " + ap.getValue("userpassword"));
        						System.out.println("Balance:  " + ap.getValue("balance"));
        						System.out.println("Account Number:  " + ap.getValue("accountNumber"));
        				
        					}
        					else if(choice.equals("6")){
    
        						System.out.println( "Thank you " + ap.getValue("username") + " for choosing " +
        											"ScrumBags as your Bank." + 
        											" Hope to see you again.");  
        						exit = true;
        						run = false;
        					}
        	
        			}
        		}
        		else{
        			System.out.println( "invalid password. Try Again");
        			System.out.println();
        		}
					
			}catch(FileErrorException ex){
				System.out.println(name + " does not exist in our database ");
				System.out.println();
						
			}
		}
			
		else if (stay.equals("n") || stay.equals("N")){
			System.out.println("Would you like to create one? Y/N");
		 	stay = scanner.nextLine();
		 	if(stay.equals("N") || stay.equals("n")){
		 		System.out.println("Thank you for considering ScrumBags as your Bank. Hope you come back.");
		 		run = false;
		 	}
		 	else if(stay.equals("y") || stay.equals("Y")){
				System.out.println();
				System.out.println("What is your name?");
				String name = scanner.nextLine();
				System.out.println();
				System.out.println("What is your email?");
				String email = scanner.nextLine();
				System.out.println();
				System.out.println("How old are you? ");
				String age = scanner.nextLine();
				System.out.println();
				System.out.println("What is your date of birth? ");
				String DOB = scanner.nextLine();
				System.out.println();
				System.out.println("Create a username and userpassword :  ");
		    		username = scanner.nextLine();
				String userpassword = scanner.nextLine();
				System.out.println();
				System.out.println("How much money do you want to put in your account? ");
				String balance = scanner.nextLine();
				ArgumentParser ap = new ArgumentParser();
				XML xml = new XML();
				ap.addPositionalArgument(stay, Argument.Type.STRING, "Determine the program");
				ap.addPositionalArgument(name, Argument.Type.STRING, "This is your name");
				ap.addPositionalArgument(email, Argument.Type.STRING, "This is your email");
				ap.addNamedArgument("name", "n", Argument.Type.STRING, name);
				ap.addNamedArgument("email", "e", Argument.Type.STRING, email);
				ap.addNamedArgument("Age", "a", Argument.Type.INT, age);
				ap.addNamedArgument("DOB", "d", Argument.Type.STRING, DOB);
				ap.addNamedArgument("username", "un", Argument.Type.STRING, username);
				ap.addNamedArgument("userpassword", "up", Argument.Type.STRING, userpassword);
				ap.addNamedArgument("balance", "b", Argument.Type.FLOAT, balance);
				double accountNumber = Math.random() * 100000000;
				long num = (long) accountNumber;
				ap.addNamedArgument("accountNumber", "an", Argument.Type.INT, num + "");
				xml.saveXML( username + ".xml", ap);
				run = false;
		
			}
			
        
		
			
	 	}
		
        }
       
    }
}
