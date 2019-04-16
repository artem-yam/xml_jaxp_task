package com.epam.chat.datalayer.xml;

import com.epam.chat.datalayer.UserDAO;
import com.epam.chat.datalayer.dto.*;
import com.epam.chat.utils.DOMHelper;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.List;

public class XMLUserDAO implements UserDAO {

    //Parsers

    @Override
    public void logIn(User user) {

    }

    @Override
    public boolean isLogged(User user) {
        boolean isLogged;

        List<Message> messages = new XMLMessageDAO().getLast(0);

        Message lastUserMessage = null;

        for (Message message : messages) {
            if (message.getFromUser().equals(user)) {
                lastUserMessage = message;

                break;
            }
        }

        if (lastUserMessage == null ||
                lastUserMessage.getStatus().getTitle().equals(
                        StatusTitle.LOGOUT)) {
            isLogged = false;
        } else {
            isLogged = true;
        }

        return isLogged;
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
        return null;
    }

    @Override
    public Role getRole(String nick) {
        Role role = null;
        try {
            Element root = DOMHelper
                    .getRootElementWithDOM("src/main/resources/chat.xml");
            //Поиск соответствующего юзера в xml
            Element userElement =
                    DOMHelper.findElement(root, "User", "nick", nick);
            String userRole = DOMHelper.getBabyValue(userElement,
                    "role");

            role = new Role(RoleTitle.valueOf(userRole),
                    DOMHelper.getBabyValue(
                            DOMHelper.findElement(root, "Role", "title",
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

