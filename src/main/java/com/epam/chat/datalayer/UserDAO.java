package com.epam.chat.datalayer;

import com.epam.chat.datalayer.dto.Role;
import com.epam.chat.datalayer.dto.User;

import java.util.List;

public interface UserDAO {

    void logIn(User user);

    boolean isLogged(User user);

    void logout(User logoutingUser);

    void kick(User admin, User kickableUser);

    void unkick(User user);

    boolean isKicked(User user);

    List<User> getAllLogged();

    Role getRole(String nick);

}
