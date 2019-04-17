package com.epam.chat.utils;

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

public final class DOMHelper {
    
    public static final String SOURCE_XML_FILE_PATH =
        "src/main/resources/chat.xml";
    private static final DOMParser DOM_PARSER = new DOMParser();
    private static final String ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE =
        "Incompatible count of child elements and values";
    
    private DOMHelper() {
    }
    
    public static String getSourceXmlFilePath() {
        return SOURCE_XML_FILE_PATH;
    }
    
    public static Document getDocumentParsedWithDOM(String url)
        throws IOException, SAXException {
        DOM_PARSER.parse(url);
        return DOM_PARSER.getDocument();
    }
    
    // возвращает дочерний элемент по его имени и родительскому элементу
    public static Element getChild(Element parent, String childName) {
        NodeList nlist = parent.getElementsByTagName(childName);
        return (Element) nlist.item(0);
    }
    
    // возвращает текст, содержащийся в элементе
    public static String getChildValue(Element parent, String childName) {
        Element child = getChild(parent, childName);
        Node node = child.getFirstChild();
        
        return node.getNodeValue();
    }
    
    //поиск элемента, внутри которого содержится тег с определенным значением
    public static Element findElement(Element root, String requiredElementTag,
                                      String[] elementsWithComparingValue,
                                      String[] requiredValue) {
        Element detectedElement = null;
        if (elementsWithComparingValue.length == requiredValue.length) {
            
            NodeList childNodes =
                root.getElementsByTagName(requiredElementTag);
            for (int i = 0; i < childNodes.getLength(); i++) {
                Element childElement = (Element) childNodes.item(i);
                boolean isSuitable = false;
                
                for (int j = 0; j < elementsWithComparingValue.length; j++) {
                    String comparingValue =
                        DOMHelper.getChildValue(childElement,
                            elementsWithComparingValue[j]);
                    if (comparingValue.equals(requiredValue[j])) {
                        isSuitable = true;
                    } else {
                        isSuitable = false;
                        break;
                    }
                }
                if (isSuitable) {
                    detectedElement = childElement;
                    
                    break;
                }
            }
        } else {
            throw new IllegalArgumentException(
                ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE);
        }
        
        return detectedElement;
    }
    
    // Функция для сохранения DOM в файл
    public static void writeDocument(Document document, String filePath)
        throws TransformerException, FileNotFoundException {
        TransformerFactory factory = TransformerFactory.newInstance();
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        Transformer tr =
            factory.newTransformer();
        DOMSource source = new DOMSource(document);
        FileOutputStream fos = new FileOutputStream(filePath);
        StreamResult result = new StreamResult(fos);
        tr.transform(source, result);
    }
    
    public static Node createElementWithSimpleChildren(Document document,
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



