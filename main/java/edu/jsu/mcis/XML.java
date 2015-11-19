package edu.jsu.mcis;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.*;
import java.lang.*;

/**
* Class has two functions: Load and save xml.
*
*/

public class XML {

  /**
  * @param filename: Takes in the name of a file
  * Opens an xml file, loads it into ArgumentParser and returns ArgumentParser
  * so values can be passed in.
  */
  public static ArgumentParser loadXML(String fileName) {
    ArgumentParser ap = new ArgumentParser();
    try {
      File xmlFile = new File(fileName);
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(xmlFile);
      doc.getDocumentElement().normalize();
      NodeList nList = doc.getElementsByTagName("positional");
      for (int temp = 0; temp < nList.getLength(); temp++) {
        Node nNode = nList.item(temp);
        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
          Element eElement = (Element) nNode;
          String elementName = eElement.getElementsByTagName("name").item(0).getTextContent();
          String elementType = eElement.getElementsByTagName("type").item(0).getTextContent();
          String elementDesc = eElement.getElementsByTagName("description").item(0).getTextContent();
          Argument.Type dataType;
          if (elementType.equals("float")){
            dataType = Argument.Type.FLOAT;
          }
          else if (elementType.equals("integer")){
            dataType = Argument.Type.INT;
          }
          else if (elementType.equals("boolean")){
            dataType = Argument.Type.BOOLEAN;
          }
          else {
            dataType = Argument.Type.STRING;
          }
          ap.addPositionalArgument(elementName, dataType, elementDesc);
        }
      }
//================================Add named Arguments===============================
      NodeList list = doc.getElementsByTagName("named");
      for (int temp = 0; temp < list.getLength(); temp++){
        Node nNode = list.item(temp);
        if(nNode.getNodeType() == Node.ELEMENT_NODE) {
          Element eElement = (Element) nNode;
          String elementName = eElement.getElementsByTagName("name").item(0).getTextContent();
          String elementShortName = eElement.getElementsByTagName("shortname").item(0).getTextContent();
          String elementType = eElement.getElementsByTagName("type").item(0).getTextContent();
          String elementDefault = eElement.getElementsByTagName("default").item(0).getTextContent();
          Argument.Type dataType;
          if (elementType.equals("float")){
            dataType = Argument.Type.FLOAT;
          }
          else if (elementType.equals("integer")){
            dataType = Argument.Type.INT;
          }
          else if (elementType.equals("boolean")){
            dataType = Argument.Type.BOOLEAN;
          }
          else {
            dataType = Argument.Type.STRING;
          }
          ap.addNamedArgument(elementName, elementShortName, dataType, elementDefault);
        }
      }
    }
    catch (Exception e) {
      throw new FileErrorException("Not Found: " + fileName);
    }
    return ap;
  }

   public void saveXML(String filepath, ArgumentParser a){
   	 	ArgumentParser ap = a;
   	 	String XMLData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+
					"<program>\n";
  		StringBuilder positionalMessage = new StringBuilder();
  		StringBuilder nameMessage = new StringBuilder();
  		String end = "</program>";
		File outfile = new File(filepath);
		for(int i=0; i < ap.getPositionalList().size(); i++){
				Argument argument = ap.getArgument().get(ap.getPositionalList().get(i));
                positionalMessage.append("\t<argument type=\"positional\">" + "\n" +
                "\t\t<name>" + argument.getName() + "</name>" + "\n" +
               "\t\t<datatype>" + argument.getType() + "</datatype>" + "\n" +
               "\t\t<description>" + argument.getDescription() + "</description>" + "\n" +
                "\t</argument>" + "\n");
			}
		for(String name : ap.getNamedArgument().keySet()) {
      			NamedArgument argument = ap.getNamedArgument().get(name);
      		    nameMessage.append("\t<argument type=\"named\">" + "\n" +
                "\t\t<name>" + argument.getName() + "</name>" + "\n" +
                "\t\t<shortName>" + argument.getShortName() + "</shortName>" + "\n" +
               "\t\t<datatype>" + argument.getType() + "</datatype>" + "\n" +
               "\t\t<defaultValue>" + argument.getValue() + "</defaultValue>" + "\n" +
                "\t</argument>" + "\n");
			}


			try{
				Writer writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(outfile), "utf-8"));
				writer.append(XMLData + positionalMessage + nameMessage + end);
				writer.close();
			}
			catch(IOException e){
				throw new HelpMessageException("");
			}




	}
}
