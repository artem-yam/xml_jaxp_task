package com.epam.jdbc.datalayer;

import com.epam.jdbc.datalayer.dto.Role;
import com.epam.jdbc.datalayer.dto.User;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface UserDAO {

    void login(String userNick)
            throws IllegalAccessException, IOException, SAXException,
            TransformerException, ParserConfigurationException;

    boolean isLogged(String userNick)
            throws ParseException, SAXException, IOException,
            ParserConfigurationException;

    void logout(String logoutingUserNick)
        throws IOException, SAXException, TransformerException,
                   ParserConfigurationException, IllegalAccessException;

    void kick(String adminNick, String kickableUserNick)
        throws IOException, SAXException, TransformerException,
                   ParserConfigurationException, IllegalAccessException;

    void unkick(String userNick)
            throws SAXException, IOException, TransformerException,
            ParserConfigurationException;

    boolean isKicked(String userNick)
            throws SAXException, IOException, ParserConfigurationException;

    List<User> getAllLogged()
            throws IOException, SAXException, ParserConfigurationException;

    Role getRole(String nick)
        throws IOException, SAXException, ParserConfigurationException;

}
