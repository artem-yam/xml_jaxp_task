package com.epam.chat;

import com.epam.chat.datalayer.AbstractDAOFactory;
import com.epam.chat.datalayer.DBType;
import com.epam.chat.datalayer.MessageDAO;
import com.epam.chat.datalayer.UserDAO;
import com.epam.chat.datalayer.dto.User;
import com.epam.chat.validation.ValidatorSample;
import org.apache.logging.log4j.LogManager;
import org.xml.sax.SAXException;

import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public final class Runner {
    private static final org.apache.logging.log4j.Logger logger =
        LogManager.getLogger(
            Runner.class);

    private Runner() {
    }

    public static void main(String[] args) {
        try {
            new ValidatorSample().validate();
            MessageDAO messageDAO = AbstractDAOFactory.getInstance(DBType.XML)
                .getMessageDAO();
            UserDAO userDAO =
                AbstractDAOFactory.getInstance(DBType.XML).getUserDAO();

            //messageDAO.getLast(10);

            List<User> users = userDAO.getAllLogged();
            userDAO.kick(users.get(1), users.get(0));
            System.out.println(userDAO.isKicked(users.get(0)));
            userDAO.unkick(users.get(0));
        } catch (IOException | SAXException exception) {
            logger.error("Source document parsing exception ", exception);
        } catch (ParseException exception) {
            logger.error("Date parsing exception ", exception);
        } catch (TransformerException exception) {
            logger.error("Saving document exception ", exception);
        }
        /* catch (IllegalAccessException exception) {
            logger.error(exception);
        }*/

    }

    //TODO + void sendMessage(Message message) – отправка сообщения (запись
    // сообщения в хранилище)
    //TODO + List<Message> getLast(int count) – получения списка последних
    // count сообщений
    //TODO + void login(User loginingUser) – вход пользователя в чат
    //TODO +? boolean isLogged(User user)) – проверка залогинен ли пользователь
    //TODO + void logout(User logoutingUser) – выход пользователя из чата
    //TODO + void kick(User admin, User kickableUser) – блокирование
    // пользователя администратором
    //TODO + void unkick(User user) – разблокирование пользователя администратором
    //TODO + boolean isKicked(User user) – проверка пользователя на блокировку
    //TODO + List<User> getAllLogged() – получение списка всех залогиненных
    // пользователей
    //TODO + Role getRole(String nick) – получение роли пользователя

}