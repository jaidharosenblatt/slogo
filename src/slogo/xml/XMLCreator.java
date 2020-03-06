package slogo.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XMLCreator {
    private DocumentBuilder documentBuilder;

    public XMLCreator(){
        documentBuilder = getDocumentBuilder();
    }

    public void createFile(){
        Document doc = documentBuilder.newDocument();
        Element rootElement = doc.createElement("preferences");
        doc.appendChild(rootElement);

        Element config = doc.createElement("config");
        doc.appendChild(config);

        Element turtles = doc.createElement("turtles");
        doc.appendChild(turtles);
    }

    private DocumentBuilder getDocumentBuilder () {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder();
        }
        catch (ParserConfigurationException e) {
            throw new XMLException(e);
        }
    }
}
