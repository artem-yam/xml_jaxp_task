package com.epam.chat.utils.outputer;

import com.epam.chat.datalayer.dto.Message;
import com.epam.chat.datalayer.dto.Role;
import com.epam.chat.datalayer.dto.User;

import java.util.List;

public interface ChatInfoOutput {
    
    void showUsers(List<User> users);
    
    void showUserRole(String userNick, Role role);
    
    void showIsLogged(String userNick, boolean isLogged);
    
    void showIsKicked(String userNick, boolean isKicked);
    
    void showMessages(List<Message> messages);
}
