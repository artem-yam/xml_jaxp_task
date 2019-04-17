package com.epam.chat.datalayer;

import com.epam.chat.datalayer.dto.Role;
import com.epam.chat.datalayer.dto.User;
import org.xml.sax.SAXException;

import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface UserDAO {

    void logIn(User user)
            throws IllegalAccessException, IOException, SAXException,
            ParseException, TransformerException;

    boolean isLogged(User user)
            throws ParseException, SAXException, IOException;

    void logout(User logoutingUser)
            throws IOException, SAXException, TransformerException;

    void kick(User admin, User kickableUser)
            throws IOException, SAXException, TransformerException;

    void unkick(User user) throws ParseException, SAXException, IOException,
            TransformerException;

    boolean isKicked(User user)
            throws ParseException, SAXException, IOException;

    List<User> getAllLogged() throws IOException, SAXException, ParseException;

    Role getRole(String nick) throws IOException, SAXException;

}
