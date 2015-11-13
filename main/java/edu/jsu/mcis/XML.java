package edu.jsu.mcis;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class XML {

    private String fileName;
    public String output = "";
    public ArgumentParser ap = new ArgumentParser();

    public void setFileName(String fileName){
        this.fileName = fileName;
        try{
        File file = new File(fileName);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);
        }
        catch (Exception e){
            throw new FileErrorException("Not Found: " + fileName);
        }
    }

    protected String getFileName(){
        return fileName;
    }

    public String loadXML() {
        try {
            File xmlFile = new File(getFileName());
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("positional");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                  Element eElement = (Element) nNode;
                  if (eElement.getElementsByTagName("type").item(0).getTextContent() == "float"){
                    ap.addPositionalArgument(eElement.getElementsByTagName("name").item(0).getTextContent(), Argument.Type.FLOAT, eElement.getElementsByTagName("description").item(0).getTextContent());
                  }
                  else if (eElement.getElementsByTagName("type").item(0).getTextContent() == "integer"){
                    ap.addPositionalArgument(eElement.getElementsByTagName("name").item(0).getTextContent(), Argument.Type.INT, eElement.getElementsByTagName("description").item(0).getTextContent());
                  }
                  else if (eElement.getElementsByTagName("type").item(0).getTextContent() == "string"){
                    ap.addPositionalArgument(eElement.getElementsByTagName("name").item(0).getTextContent(), Argument.Type.STRING, eElement.getElementsByTagName("description").item(0).getTextContent());
                  }
                  else if (eElement.getElementsByTagName("type").item(0).getTextContent() == "boolean"){
                    ap.addPositionalArgument(eElement.getElementsByTagName("name").item(0).getTextContent(), Argument.Type.BOOLEAN, eElement.getElementsByTagName("description").item(0).getTextContent());
                  }
                }
            }
            NodeList list = doc.getElementsByTagName("named");
            for (int temp = 0; temp < list.getLength(); temp++){
                Node nNode = list.item(temp);
                if(nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    output = output + ("\n||" + "Type : " + eElement.getElementsByTagName("name").item(0).getTextContent() +
                    "\nshortname : " + eElement.getElementsByTagName("shortname").item(0).getTextContent() +
                    "\ndata type : " + eElement.getElementsByTagName("type").item(0).getTextContent() +
                    "\ndefault : " + eElement.getElementsByTagName("default").item(0).getTextContent() + "||\n");
                }
            }
        }
        catch (Exception e) {
            throw new FileErrorException("Not Found: " + getFileName());
        }
        return output;
    }

    public String returnLoad(){
        return loadXML();
    }

    public void saveXML() {
        try{

        }
        catch (Exception e) {

        }
    }
}
