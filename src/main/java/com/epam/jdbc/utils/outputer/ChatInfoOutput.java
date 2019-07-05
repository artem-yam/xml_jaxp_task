package com.epam.jdbc.utils.outputer;

import com.epam.jdbc.datalayer.dto.Message;
import com.epam.jdbc.datalayer.dto.Role;
import com.epam.jdbc.datalayer.dto.User;

import java.util.List;

public interface ChatInfoOutput {
    
    void showUsers(List<User> users);
    
    void showUserRole(String userNick, Role role);
    
    void showIsLogged(String userNick, boolean isLogged);
    
    void showIsKicked(String userNick, boolean isKicked);
    
    void showMessages(List<Message> messages);
}
