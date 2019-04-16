package com.epam.chat.datalayer.dto;

public class User {

    private String nick;
    private Role role;

    public User(String nick, Role role) {
        this.nick = nick;
        this.role = role;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
