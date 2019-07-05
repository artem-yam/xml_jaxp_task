package com.epam.jdbc.parser.dom;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class DOMHelper {
    
    private static final String ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE =
        "Incompatible count of child elements and values";
    private DOMParser domParser;
    
    public DOMHelper() {
        domParser = new DOMParser();
    }
    
    public DOMHelper(DOMParser domParser) {
        this.domParser = domParser;
    }
    
    public Document getParsedDocument(String url)
        throws IOException, SAXException {
        domParser.parse(url);
        
        return domParser.getDocument();
    }
    
    public Element getChild(Element parent, String childName) {
        NodeList nodeList = parent.getElementsByTagName(childName);
        return (Element) nodeList.item(0);
    }
    
    public String getChildValue(Element parent, String childName) {
        Element child = getChild(parent, childName);
        Node node = child.getFirstChild();
        
        return node.getNodeValue();
    }
    
    public Element findElement(Element root, String requiredElementTag,
                               String[] elementsWithComparingValue,
                               String[] requiredValue) {
        Element detectedElement = null;
        if (elementsWithComparingValue.length == requiredValue.length) {
            
            NodeList childNodes = root.getElementsByTagName(requiredElementTag);
            boolean isSuitable = false;
            for (int i = 0; i < childNodes.getLength() && !isSuitable; i++) {
                Element childElement = (Element) childNodes.item(i);
                isSuitable = true;
                
                for (int j = 0;
                     j < elementsWithComparingValue.length && isSuitable; j++) {
                    String comparingValue = getChildValue(childElement,
                        elementsWithComparingValue[j]);
                    isSuitable = comparingValue.equals(requiredValue[j]);
                }
                if (isSuitable) {
                    detectedElement = childElement;
                }
            }
        } else {
            throw new IllegalArgumentException(
                ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE);
        }
        
        return detectedElement;
    }
    
    public void writeDocument(Document document, String filePath)
        throws TransformerException, FileNotFoundException {
        TransformerFactory factory = TransformerFactory.newInstance();
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        Transformer transformer = factory.newTransformer();
        DOMSource source = new DOMSource(document);
        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        StreamResult result = new StreamResult(fileOutputStream);
        transformer.transform(source, result);
    }
    
    public Node createElementWithSimpleChildren(Document document,
                                                String elemTag,
                                                String[] childTags,
                                                String[] childTextValues) {
        Node newMessageNode = document.createElement(elemTag);
        if (childTags.length == childTextValues.length) {
            for (int i = 0; i < childTags.length; i++) {
                Node childNode = document.createElement(childTags[i]);
                childNode.appendChild(
                    document.createTextNode(childTextValues[i]));
                
                newMessageNode.appendChild(childNode);
            }
        } else {
            throw new IllegalArgumentException(
                ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE);
        }
        
        return newMessageNode;
    }
}



