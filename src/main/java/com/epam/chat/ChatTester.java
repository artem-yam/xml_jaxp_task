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
    
    private static final String XML_PATH = "src/main/resources/chat.xml";
    private static final String XSD_PATH = "src/main/resources/chat.xsd";
    
    private String sourceXML;
    private String schemaForXML;
    private Validator validator;
    private MessageDAO messageDAO;
    private UserDAO userDAO;
    private ChatInfoOutput infoOutput = new ConsoleChatInfoOutput();
    
    public ChatTester(String sourceXML, String schemaForXML,
        Validator validator, AbstractDAOFactory daoFactory) {
        this.sourceXML = sourceXML;
        this.schemaForXML = schemaForXML;
        this.validator = validator;
        
        messageDAO = daoFactory.getMessageDAO();
        userDAO = daoFactory.getUserDAO();
    }
    
    public static void main(String[] args) {
        
        ChatTester chatTester = new ChatTester(XML_PATH, XSD_PATH,
            new XMLValidator(), AbstractDAOFactory.getInstance(DBType.XML));
        
        try {
            chatTester.validateSourceFile();
            
            chatTester.testFunctionality();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        //messageDAO.getLast(10);
    }
    
    public void validateSourceFile() throws Exception {
        validator.validate(sourceXML, schemaForXML);
    }
    
    public void testFunctionality() {
        try {
            String userNick = "testUser";
            String adminNick = "Admin1";
            
            Message msgToSend = new Message(userNick, new Date(),
                "test message", new Status(StatusTitle.MESSAGE));
            
            infoOutput.showIsLogged(userNick, userDAO.isLogged(userNick));
            userDAO.logIn(userNick);
            infoOutput.showIsLogged(userNick, userDAO.isLogged(userNick));
            infoOutput.showUserRole(userNick, userDAO.getRole(userNick));
            messageDAO.sendMessage(msgToSend);
            userDAO.logIn(adminNick);
            infoOutput.showUsers(userDAO.getAllLogged());
            userDAO.kick(adminNick, userNick);
            infoOutput.showMessages(messageDAO.getLast(1));
            infoOutput.showIsKicked(userNick, userDAO.isKicked(userNick));
            userDAO.unkick(userNick);
            infoOutput.showIsKicked(userNick, userDAO.isKicked(userNick));
            infoOutput.showMessages(messageDAO.getLast(0));
            userDAO.logout(userNick);
            infoOutput.showIsLogged(userNick, userDAO.isLogged(userNick));
            infoOutput.showUsers(userDAO.getAllLogged());
            userDAO.logout(adminNick);
            
        } catch (Exception exception) {
            infoOutput.showException(exception);
        }
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