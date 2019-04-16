package com.epam.chat.datalayer.xml;

import com.epam.chat.datalayer.MessageDAO;
import com.epam.chat.datalayer.dto.*;
import com.epam.chat.utils.DOMHelper;
import com.epam.chat.utils.MessageByDateReverseComparator;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class XMLMessageDAO implements MessageDAO {

    //Parsers

    //При count=0 возвращает все сообщения
    @Override
    public List<Message> getLast(int count) {
        List<Message> messages = new ArrayList<>();
        try {
            Element root = DOMHelper.getDocumentParsedWithDOM(
                    DOMHelper.getSourceXMLFilePath()).getDocumentElement();
            NodeList messageNodes = root.getElementsByTagName("Message");

            for (int i = 0; i < messageNodes.getLength(); i++) {

                Element messageElement = (Element) messageNodes.item(i);

                //получение значений тегов внутри этого сообщения
                String fromUser = DOMHelper.getChildValue(messageElement,
                        "user_from");
                String statusString = DOMHelper.getChildValue(messageElement,
                        "status");
                Date timeStamp = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss")
                        .parse(DOMHelper
                                .getChildValue(messageElement, "time_stamp"));
                String messageText = DOMHelper.getChildValue(messageElement,
                        "message");

                //Поиск соответствующего статуса в xml
                Element statusElement = DOMHelper.findElement(root, "Status",
                        "title", statusString);
                //Формирование объекта статуса
                Status messageStatus = new Status(
                        StatusTitle.valueOf(statusString),
                        DOMHelper.getChildValue(statusElement, "description"));

                //Формирование объекта роли
                Role role = new XMLUserDAO().getRole(fromUser);

                //Формирование объекта юзера
                User user = new User(fromUser, role);

                //добавление сформированного сообщения в список
                messages.add(new Message(user, timeStamp, messageText,
                        messageStatus));

            }
            //сортировка (в начале списка - более поздние сообщения)
            messages.sort(new MessageByDateReverseComparator());

            if (messages.size() > count && count != 0) {
                messages = messages.subList(0, count);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return messages;
    }

    @Override
    public void sendMessage(User user, Message message) {

    }
}
