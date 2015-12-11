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
* XML class adds XML functionality to ArgumentParser
*/
public class XML {
  /**
  * @param fileName name of XML file to be read
  * @return An ArgumentParser object with data from the XML file
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
          if (elementType.equals("float")) {
            dataType = Argument.Type.FLOAT;
          }
          else if (elementType.equals("integer")) {
            dataType = Argument.Type.INT;
          }
          else {
            dataType = Argument.Type.STRING;
          }
          ap.addPositionalArgument(elementName, dataType, elementDesc);
        }
      }
//================================Add named Arguments===============================
      NodeList list = doc.getElementsByTagName("named");
      for (int temp = 0; temp < list.getLength(); temp++) {
        Node nNode = list.item(temp);
        if(nNode.getNodeType() == Node.ELEMENT_NODE) {
          Element eElement = (Element) nNode;
          String elementName = eElement.getElementsByTagName("name").item(0).getTextContent();
          String elementShortName = eElement.getElementsByTagName("shortname").item(0).getTextContent();
          String elementType = eElement.getElementsByTagName("type").item(0).getTextContent();
          String elementDefault = eElement.getElementsByTagName("default").item(0).getTextContent();
          Argument.Type dataType;
          if (elementType.equals("float")) {
            dataType = Argument.Type.FLOAT;
          }
          else if (elementType.equals("integer")) {
            dataType = Argument.Type.INT;
          }
          else if (elementType.equals("boolean")) {
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
      throw new FileErrorException("Error with file: " + fileName);
    }
    return ap;
  }

  /**
  * @param filepath name of XML file to be saved to
  * @param a ArgumentParser object to be written to an XML file
  */
  public void saveXML(String filepath, ArgumentParser a) {
    ArgumentParser ap = a;
    String XMLData = "<?xml version=\"1.0\" encoding=\"us-ascii\"?>\n" + "<program>\n";
    StringBuilder positionalMessage = new StringBuilder();
    StringBuilder nameMessage = new StringBuilder();
    String end = "</program>";
    File outfile = new File(filepath);
    for(int i=0; i < ap.getPositionalList().size(); i++) {
      Argument argument = ap.getArgument().get(ap.getPositionalList().get(i));
      positionalMessage.append("\t<positional>" + "\n" +
      "\t\t<name>" + argument.getName() + "</name>" + "\n" +"\t\t<type>" +
      argument.getType() + "</type>" + "\n" + "\t\t<description>" +
      argument.getDescription() + "</description>" + "\n" + "\t</positional>" + "\n");
    }
    for(String name : ap.getNamedArgument().keySet()) {
      NamedArgument argument = ap.getNamedArgument().get(name);
      nameMessage.append("\t<named>" + "\n" +"\t\t<name>" +
      argument.getName() + "</name>" + "\n" + "\t\t<shortname>" +
      argument.getShortName() + "</shortname>" + "\n" + "\t\t<type>" +
      argument.getType() + "</type>" + "\n" + "\t\t<default>" +
      argument.getValue() + "</default>" + "\n" + "\t</named>" + "\n");
    }

    try {
      Writer writer = new BufferedWriter(new OutputStreamWriter(
      new FileOutputStream(outfile), "us-ascii"));
      writer.append(XMLData + positionalMessage + nameMessage + end);
      writer.close();
    }
    catch(IOException e) {
      throw new FileErrorException("Error with file: " + filepath);
    }
  }
}
