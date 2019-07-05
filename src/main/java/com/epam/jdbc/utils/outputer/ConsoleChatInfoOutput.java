package com.epam.jdbc.utils.outputer;

import com.epam.jdbc.datalayer.dto.Message;
import com.epam.jdbc.datalayer.dto.Role;
import com.epam.jdbc.datalayer.dto.User;

import java.util.List;

public class ConsoleChatInfoOutput implements ChatInfoOutput {
    
    private static final String MESSAGE_OUTPUT = "%tF %tR %s: %s%n";
    private static final String IS_LOGGED_OUTPUT =
        "Is user \'%s\' logged? %s%n";
    private static final String IS_KICKED_OUTPUT =
        "Is user \'%s\' kicked? %s%n";
    private static final String ROLE_OUTPUT = "User \'%s\' has role: %s (%s)%n";
    private static final String USER_OUTPUT = "User: \'%s\'; Role: \'%s\'%n";
    
    @Override
    public void showUsers(List<User> users) {
        for (User user : users) {
            System.out.printf(USER_OUTPUT, user.getNick(),
                user.getRole().toString());
        }
    }
    
    @Override
    public void showUserRole(String userNick, Role role) {
        System.out.printf(ROLE_OUTPUT, userNick, role.getTitle().toString(),
            role.getDescription());
    }
    
    @Override
    public void showIsLogged(String userNick, boolean isLogged) {
        System.out.printf(IS_LOGGED_OUTPUT, userNick, isLogged);
    }
    
    @Override
    public void showIsKicked(String userNick, boolean isKicked) {
        System.out.printf(IS_KICKED_OUTPUT, userNick, isKicked);
    }
    
    @Override
    public void showMessages(List<Message> messages) {
        for (Message message : messages) {
            System.out.printf(MESSAGE_OUTPUT, message.getTimeStamp(),
                message.getTimeStamp(), message.getSenderNick(),
                message.getMessage());
        }
    }
}
