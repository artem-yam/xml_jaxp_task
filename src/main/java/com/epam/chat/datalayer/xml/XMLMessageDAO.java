package com.epam.chat.datalayer.xml;

import com.epam.chat.datalayer.MessageDAO;
import com.epam.chat.datalayer.dto.*;
import com.epam.chat.utils.DOMHelper;
import com.epam.chat.utils.MessageByDateReverseComparator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class XMLMessageDAO implements MessageDAO {

    private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd'T'kk:mm:ss";
    private static final String MESSAGE_MAIN_ELEMENT = "Message";
    private static final String MESSAGE_USER_ELEMENT = "user_from";
    private static final String MESSAGE_DATE_ELEMENT = "time_stamp";
    private static final String MESSAGE_TEXT_ELEMENT = "message";
    private static final String MESSAGE_STATUS_ELEMENT = "status";
    private static final String[] MESSAGE_CHILD_ELEMENTS =
        new String[]{MESSAGE_USER_ELEMENT, MESSAGE_DATE_ELEMENT,
            MESSAGE_TEXT_ELEMENT, MESSAGE_STATUS_ELEMENT};
    private static final String STATUS_MAIN_ELEMENT = "Status";
    private static final String STATUS_TITLE_ELEMENT = "title";
    private static final String STATUS_DESCRIPTION_ELEMENT = "description";

    //Parsers

    //При count=0 возвращает все сообщения
    @Override
    public List<Message> getLast(int count)
        throws IOException, SAXException, ParseException {
        List<Message> messages = new ArrayList<>();

        Element root = DOMHelper.getDocumentParsedWithDOM(
            DOMHelper.getSourceXmlFilePath()).getDocumentElement();
        NodeList messageNodes =
            root.getElementsByTagName(MESSAGE_MAIN_ELEMENT);

        for (int i = 0; i < messageNodes.getLength(); i++) {

            Element messageElement = (Element) messageNodes.item(i);

            //получение значений тегов внутри этого сообщения
            String fromUser = DOMHelper.getChildValue(messageElement,
                MESSAGE_USER_ELEMENT);
            String statusString = DOMHelper.getChildValue(messageElement,
                MESSAGE_STATUS_ELEMENT);
            Date timeStamp = new SimpleDateFormat(DATE_FORMAT_PATTERN)
                .parse(DOMHelper
                    .getChildValue(messageElement,
                        MESSAGE_DATE_ELEMENT));
            String messageText = DOMHelper.getChildValue(messageElement,
                MESSAGE_TEXT_ELEMENT);

            //Поиск соответствующего статуса в xml
            Element statusElement =
                DOMHelper.findElement(root, STATUS_MAIN_ELEMENT,
                    new String[]{STATUS_TITLE_ELEMENT},
                    new String[]{statusString});
            //Формирование объекта статуса
            Status messageStatus = new Status(
                StatusTitle.valueOf(statusString),
                DOMHelper.getChildValue(statusElement,
                    STATUS_DESCRIPTION_ELEMENT));

            //Формирование объекта роли
            Role role = new XMLUserDAO().getRole(fromUser);

            //Формирование объекта юзера
            User user = new User(fromUser, role);

            //добавление сформированного сообщения в список
            messages.add(new Message(user, timeStamp, messageText,
                messageStatus));

        }
        //сортировка (в начале списка - более поздние сообщения)
        messages.sort(new MessageByDateReverseComparator<>());

        if (messages.size() > count && count != 0) {
            messages = messages.subList(0, count);
        }

        return messages;
    }

    @Override
    public void sendMessage(Message message)
        throws IOException, SAXException, TransformerException {

        Document document = DOMHelper.getDocumentParsedWithDOM(
            DOMHelper.getSourceXmlFilePath());
        Element root = document.getDocumentElement();

        Node newMessageNode = DOMHelper
            .createElementWithSimpleChildren(document,
                MESSAGE_MAIN_ELEMENT,
                MESSAGE_CHILD_ELEMENTS,
                new String[]{message.getFromUser().getNick(),
                    new SimpleDateFormat(
                        DATE_FORMAT_PATTERN)
                        .format(message.getTimeStamp()),
                    message.getMessage(),
                    message.getStatus().getTitle().toString()
                });

        root.insertBefore(newMessageNode, root.getFirstChild());

        DOMHelper.writeDocument(document, DOMHelper.SOURCE_XML_FILE_PATH);
    }

}
