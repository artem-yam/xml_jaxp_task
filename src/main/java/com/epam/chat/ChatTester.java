package com.epam.chat;

import com.epam.chat.datalayer.AbstractDAOFactory;
import com.epam.chat.datalayer.DBType;
import com.epam.chat.datalayer.MessageDAO;
import com.epam.chat.datalayer.UserDAO;
import com.epam.chat.datalayer.dto.Message;
import com.epam.chat.datalayer.dto.Status;
import com.epam.chat.datalayer.dto.StatusTitle;
import com.epam.chat.utils.outputer.ChatInfoOutput;
import com.epam.chat.utils.outputer.ConsoleChatInfoOutput;
import com.epam.chat.validation.Validator;
import com.epam.chat.validation.XMLValidator;
import org.apache.logging.log4j.LogManager;

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
    private MessageDAO messageDAO;
    private UserDAO userDAO;
    private ChatInfoOutput infoOutput = new ConsoleChatInfoOutput();
    
    public ChatTester(String sourceXML, String schemaForXML,
        Validator validator, DBType dbType) {
        this.sourceXML = sourceXML;
        this.schemaForXML = schemaForXML;
        this.validator = validator;
        
        AbstractDAOFactory daoFactory = AbstractDAOFactory.getInstance(dbType);
        
        messageDAO = daoFactory.getMessageDAO();
        userDAO = daoFactory.getUserDAO();
    }
    
    public static void main(String[] args) {
        //XML_PATH = "src/main/resources/chat.xml";
        //XSD_PATH = "src/main/resources/chat.xsd";
        
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
    
    public void testFunctionality() throws Exception {
        
        infoOutput.showIsLogged(TEST_USER_NICK,
            userDAO.isLogged(TEST_USER_NICK));
        userDAO.login(TEST_USER_NICK);
        infoOutput.showIsLogged(TEST_USER_NICK,
            userDAO.isLogged(TEST_USER_NICK));
        infoOutput.showUserRole(TEST_USER_NICK,
            userDAO.getRole(TEST_USER_NICK));
        messageDAO.sendMessage(
            new Message(TEST_USER_NICK, new Date(), TEST_MESSAGE_TEXT,
                new Status(StatusTitle.MESSAGE)));
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
    
    //todo почитать теорию; +- sax и dom. и т.д.
    //todo + ValidationSample переименовать; передавать ардес через аргументы
    //todo + в AbstractDAO или DBType кидать Exception, если передается не Xml.
    //todo +- runner переименовать; тестировать все методы.
    //todo + в методы передавать не юзера, а его ник.
    //todo + добавить sax-персер(мб где только получение инфы, без изменения
    // xml)
    //todo + брать объект DOMHelper, не использовать там static.
    //todo + не вызывать message- и user- dao внутри друг друга.
    
}