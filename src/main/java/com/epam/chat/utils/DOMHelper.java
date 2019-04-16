package com.epam.chat.utils;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.io.IOException;

public class DOMHelper {

    private static final DOMParser domParser = new DOMParser();
    private static final String sourceXMLFilePath =
            "src/main/resources/chat.xml";
    public static final String outputXMLFilePath =
            "src/main/resources/newChat.xml";

    public static String getSourceXMLFilePath() {
        return sourceXMLFilePath;
    }

    public static Document getDocumentParsedWithDOM(String url)
            throws IOException, SAXException {
        domParser.parse(url);
        return domParser.getDocument();
    }

    // возвращает дочерний элемент по его имени и родительскому элементу
    public static Element getChild(Element parent, String childName) {
        NodeList nlist = parent.getElementsByTagName(childName);
        Element child = (Element) nlist.item(0);
        return child;
    }

    // возвращает текст, содержащийся в элементе
    public static String getChildValue(Element parent, String childName) {
        Element child = getChild(parent, childName);
        Node node = child.getFirstChild();

        String value = node.getNodeValue();
        return value;
    }

    //поиск элемента, внутри которого содержится тег с определенным значением
    public static Element findElement(Element root, String requiredElementTag,
            String elementWithComparingValue, String requiredValue) {
        Element detectedElement = null;
        NodeList childNodes = root.getElementsByTagName(requiredElementTag);
        for (int i = 0; i < childNodes.getLength(); i++) {
            Element childElement = (Element) childNodes.item(i);

            String comparingValue = DOMHelper.getChildValue(childElement,
                    elementWithComparingValue);
            if (comparingValue.equals(requiredValue)) {
                detectedElement = childElement;

                break;
            }
        }

        return detectedElement;
    }

    // Функция для сохранения DOM в файл
    public static void writeDocument(Document document, String filePath) {
        try {
            Transformer tr = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(document);
            FileOutputStream fos = new FileOutputStream(filePath);
            StreamResult result = new StreamResult(fos);
            tr.transform(source, result);
        } catch (TransformerException | IOException e) {
            e.printStackTrace(System.out);
        }
    }
}



