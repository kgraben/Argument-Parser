package edu.jsu.mcis;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class LoadXML {

    private String fileName;

    public void setFileName(String fileName){
      this.fileName = fileName;
    }

    protected String getFileName(){
      return fileName;
    }

  public void loadXML() {

    try {

    File xmlFile = new File(getFileName());
    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    Document doc = dBuilder.parse(xmlFile);
    doc.getDocumentElement().normalize();
    System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
    NodeList nList = doc.getElementsByTagName("positional");
    System.out.println("----------------------------");
    for (int temp = 0; temp < nList.getLength(); temp++) {
        Node nNode = nList.item(temp);
        System.out.println("\nCurrent Element :" + nNode.getNodeName());
        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
            Element eElement = (Element) nNode;
            System.out.println("positional : " + eElement.getAttribute("id"));
            System.out.println("name : " + eElement.getElementsByTagName("name").item(0).getTextContent());
            System.out.println("type : " + eElement.getElementsByTagName("type").item(0).getTextContent());
            System.out.println("description : " + eElement.getElementsByTagName("description").item(0).getTextContent());
            System.out.println("position : " + eElement.getElementsByTagName("position").item(0).getTextContent());
        }
    }
    } catch (Exception e) {
    e.printStackTrace();
    }
  }

}
