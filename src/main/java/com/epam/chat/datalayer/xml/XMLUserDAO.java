package com.epam.chat.datalayer.xml;

import com.epam.chat.datalayer.UserDAO;
import com.epam.chat.datalayer.dto.*;
import com.epam.chat.utils.DOMHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class XMLUserDAO implements UserDAO {

    //Parsers

    @Override
    public void logIn(User user) {
        try {
            Document document = DOMHelper.getDocumentParsedWithDOM(
                    DOMHelper.getSourceXMLFilePath());
            Element root = document.getDocumentElement();

            Node newMessageNode;
            NodeList messageNodes = root.getElementsByTagName("Message");

            //TODO создавать новую ноду, а не переделывать существующую
            if (messageNodes.getLength() != 0) {
                Node someMessageNode = messageNodes.item(0);
                newMessageNode = someMessageNode.cloneNode(true);

                //Установка значения тега user_from
                Element userElement = DOMHelper.getChild(
                        (Element) newMessageNode, "user_from");
                Node userNode = userElement.getFirstChild();
                userNode.setNodeValue(user.getNick());

                //Установка значения тега time_stamp
                Element timeStampElement = DOMHelper.getChild(
                        (Element) newMessageNode, "time_stamp");
                Node timeStampNode = timeStampElement.getFirstChild();
                timeStampNode.setNodeValue(
                        new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss")
                                .format(new Date()));

                //Установка значения тега message
                Element messageElement = DOMHelper.getChild(
                        (Element) newMessageNode, "message");
                Node messageNode = messageElement.getFirstChild();
                messageNode.setNodeValue(
                        "User " + user.getNick() + " logged in");

                //Установка значения тега status
                Element statusElement = DOMHelper.getChild(
                        (Element) newMessageNode, "status");
                Node statusNode = statusElement.getFirstChild();
                statusNode.setNodeValue(StatusTitle.LOGIN.toString());

                root.insertBefore(newMessageNode, root.getFirstChild());
            } else {
                newMessageNode = document.createElement("User");
            }

            DOMHelper.writeDocument(document, DOMHelper.outputXMLFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    //TODO проверять чтобы был статус LOGIN, а после него не было LOGOUT???
    @Override
    public boolean isLogged(User user) {

        List<Message> messages = new XMLMessageDAO().getLast(0);

        Message lastUserMessage = null;

        for (Message message : messages) {
            if (message.getFromUser().equals(user)) {
                lastUserMessage = message;

                break;
            }
        }

        return !(lastUserMessage == null ||
                lastUserMessage.getStatus().getTitle().equals(
                        StatusTitle.LOGOUT));
    }

    @Override
    public void logout(User userToLogout) {

    }

    @Override
    public void kick(User admin, User userToKick) {

    }

    @Override
    public void unkick(User user) {

    }

    @Override
    public boolean isKicked(User user) {
        return false;
    }

    @Override
    public List<User> getAllLogged() {
        List<User> loggedUsers = new ArrayList<>();
        try {
            Element root = DOMHelper.getDocumentParsedWithDOM(
                    DOMHelper.getSourceXMLFilePath()).getDocumentElement();

            NodeList userNodes = root.getElementsByTagName("User");

            for (int i = 0; i < userNodes.getLength(); i++) {
                Element userElement = (Element) userNodes.item(i);

                String nick = DOMHelper.getChildValue(userElement, "nick");

                User user = new User(nick, getRole(nick));

                if (isLogged(user)) {
                    loggedUsers.add(user);
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        return loggedUsers;
    }

    @Override
    public Role getRole(String nick) {
        Role role = null;
        try {
            Element root = DOMHelper.getDocumentParsedWithDOM(
                    DOMHelper.getSourceXMLFilePath()).getDocumentElement();
            //Поиск соответствующего юзера в xml
            Element userElement = DOMHelper.findElement(root, "User", "nick",
                    nick);
            String userRole = DOMHelper.getChildValue(userElement, "role");

            role = new Role(RoleTitle.valueOf(userRole), DOMHelper
                    .getChildValue(DOMHelper
                                    .findElement(root, "Role", "title",
                                            userRole),
                            "description"));
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (SAXException e1) {
            e1.printStackTrace();
        }

        return role;
    }
}

