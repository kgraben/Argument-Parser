import java.util.*;
import edu.jsu.mcis.*;



public class BankAccount{
    
    public static void main(String[] args){
        XML xml = new XML();
        ArgumentParser ap = xml.loadXML("testXml.xml");
        ap.parse(args);
        System.out.println("Welcome to ScrumBags Bank");
        System.out.println("Enter your name and password");
        
        System.out.println("Your accont Information");
        System.out.println();
        System.out.println("Age:   "    + ap.getValue("myAge"));
        
        
        
    }






}