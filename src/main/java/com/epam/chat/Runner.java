package com.epam.chat;

import com.epam.chat.datalayer.DAOFactory;
import com.epam.chat.datalayer.DBType;
import com.epam.chat.datalayer.MessageDAO;
import com.epam.chat.datalayer.UserDAO;

public class Runner {
    public static void main(String[] args) {
        MessageDAO messageDAO =
                DAOFactory.getInstance(DBType.XML).getMessageDAO();
        UserDAO userDAO = DAOFactory.getInstance(DBType.XML).getUserDAO();

        messageDAO.getLast(10);

    }

    //TODO void sendMessage(Message message) – отправка сообщения (запись сообщения в хранилище)
    //TODO + List<Message> getLast(int count) – получения списка последних count сообщений
    //TODO void login(User loginingUser) – вход пользователя в чат
    //TODO + boolean isLogged(User user)) – проверка залогинен ли пользователь
    //TODO void logout(User logoutingUser) – выход пользователя из чата
    //TODO void kick(User admin, User kickableUser) – блокирование пользователя администратором
    //TODO void unkick(User user) – разблокирование пользователя администратором
    //TODO boolean isKicked(User user) – проверка пользователя на блокировку
    //TODO List<User> getAllLogged() – получение списка всех залогиненных пользователей
    //TODO + Role getRole(String nick) – получение роли пользователя

}