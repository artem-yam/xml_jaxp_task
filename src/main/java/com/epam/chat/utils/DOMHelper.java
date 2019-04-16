package com.epam.chat.utils;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;

public class DOMHelper {

    private static final DOMParser domParser = new DOMParser();

    public static Element getRootElementWithDOM(String url)
            throws IOException, SAXException {
        domParser.parse(url);
        return domParser.getDocument().getDocumentElement();
    }

    // возвращает дочерний элемент по его имени и родительскому элементу
    public static Element getBaby(Element parent, String childName) {
        NodeList nlist = parent.getElementsByTagName(childName);
        Element child = (Element) nlist.item(0);
        return child;
    }

    // возвращает текст, содержащийся в элементе
    public static String getBabyValue(Element parent, String childName) {
        Element child = getBaby(parent, childName);
        Node node = child.getFirstChild();

        String value = node.getNodeValue();
        return value;
    }

    //поиск элемента, внутри которого содержится тег с определенным значением
    public static Element findElement(Element root, String requiredElementTag,
                                      String elementWithComparingValue,
                                      String requiredValue) {
        Element detectedElement = null;
        NodeList childNodes =
                root.getElementsByTagName(requiredElementTag);
        for (int i = 0; i < childNodes.getLength(); i++) {
            Element childElement = (Element) childNodes.item(i);

            String comparingValue =
                    DOMHelper.getBabyValue(childElement,
                            elementWithComparingValue);
            if (comparingValue.equals(requiredValue)) {
                detectedElement = childElement;

                break;
            }
        }

        return detectedElement;
    }
}



