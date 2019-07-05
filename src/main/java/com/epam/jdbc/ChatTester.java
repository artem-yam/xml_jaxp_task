package com.epam.jdbc;

import com.epam.jdbc.datalayer.AbstractDAOFactory;
import com.epam.jdbc.datalayer.DBType;
import com.epam.jdbc.datalayer.MessageDAO;
import com.epam.jdbc.datalayer.UserDAO;
import com.epam.jdbc.datalayer.dto.Message;
import com.epam.jdbc.datalayer.dto.StatusTitle;
import com.epam.jdbc.utils.outputer.ChatInfoOutput;
import com.epam.jdbc.utils.outputer.ConsoleChatInfoOutput;
import com.epam.jdbc.validation.Validator;
import com.epam.jdbc.validation.XMLValidator;
import org.apache.logging.log4j.LogManager;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.sql.RowSet;
import java.sql.*;
import java.util.Date;

public final class ChatTester {
    private static final org.apache.logging.log4j.Logger logger =
        LogManager.getLogger(ChatTester.class);
    
    private static final String TEST_USER_NICK = "testUser";
    private static final String TEST_ADMIN_NICK = "Admin1";
    private static final String TEST_MESSAGE_TEXT = "test message";
    
    private String sourceXML;
    private String schemaForXML;
    private Validator validator;
    
    private DBType dbType;
    
    private MessageDAO messageDAO = null;
    private UserDAO userDAO = null;
    private ChatInfoOutput infoOutput = new ConsoleChatInfoOutput();
    
    public ChatTester(String sourceXML, String schemaForXML,
                      Validator validator, DBType dbType) {
        this.sourceXML = sourceXML;
        this.schemaForXML = schemaForXML;
        this.validator = validator;
        this.dbType = dbType;
    }
    
    public static void main(String[] args) {
<<<<<<< HEAD:src/main/java/com/epam/chat/ChatTester.java
        // src/main/resources/chat.xml src/main/resources/chat.xsd
=======
        // src/main/resources/jdbc.xml src/main/resources/jdbc.xsd
>>>>>>> 01ac0c8007b04892f1a9d78283287b2f1a1af8db:src/main/java/com/epam/jdbc/ChatTester.java
        ChatTester chatTester = new ChatTester(args[0], args[1],
            new XMLValidator(), DBType.XML);
        
        chatTester.runTest();
    }
    
    public void runTest() {
        try {
            validateSourceFile();
            testFunctionality();
        } catch (Exception exception) {
            logger.error(exception);
        }
    }
    
    public void validateSourceFile() throws Exception {
        validator.validate(sourceXML, schemaForXML);
    }
    
    private void initDAO() {
        AbstractDAOFactory daoFactory = AbstractDAOFactory.getInstance(dbType);
        
        messageDAO = daoFactory.getMessageDAO(sourceXML);
        userDAO = daoFactory.getUserDAO(sourceXML);
    }
    
    public void testFunctionality() throws Exception {
        
        if (messageDAO == null || userDAO == null) {
            initDAO();
        }
        
        infoOutput.showIsLogged(TEST_USER_NICK,
            userDAO.isLogged(TEST_USER_NICK));
        userDAO.login(TEST_USER_NICK);
        infoOutput.showIsLogged(TEST_USER_NICK,
            userDAO.isLogged(TEST_USER_NICK));
        infoOutput.showUserRole(TEST_USER_NICK,
            userDAO.getRole(TEST_USER_NICK));
        messageDAO.sendMessage(new Message(TEST_USER_NICK, new Date(),
            TEST_MESSAGE_TEXT, StatusTitle.MESSAGE));
        userDAO.login(TEST_ADMIN_NICK);
        infoOutput.showUsers(userDAO.getAllLogged());
        userDAO.kick(TEST_ADMIN_NICK, TEST_USER_NICK);
        infoOutput.showMessages(messageDAO.getLast(1));
        infoOutput.showIsKicked(TEST_USER_NICK,
            userDAO.isKicked(TEST_USER_NICK));
        userDAO.unkick(TEST_USER_NICK);
        infoOutput.showIsKicked(TEST_USER_NICK,
            userDAO.isKicked(TEST_USER_NICK));
        infoOutput.showMessages(messageDAO.getLast(0));
        userDAO.logout(TEST_USER_NICK);
        infoOutput.showIsLogged(TEST_USER_NICK,
            userDAO.isLogged(TEST_USER_NICK));
        infoOutput.showUsers(userDAO.getAllLogged());
        userDAO.logout(TEST_ADMIN_NICK);
    }
}