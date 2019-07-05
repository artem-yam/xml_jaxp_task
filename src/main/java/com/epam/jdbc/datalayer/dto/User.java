package com.epam.jdbc.datalayer.dto;

import java.util.Objects;

public class User {
    
    private String nick;
    private RoleTitle role;
    
    public User(String nick, RoleTitle role) {
        this.nick = nick;
        this.role = role;
    }
    
    public User() {
    }
    
    public String getNick() {
        return nick;
    }
    
    public void setNick(String nick) {
        this.nick = nick;
    }
    
    public RoleTitle getRole() {
        return role;
    }
    
    public void setRole(RoleTitle role) {
        this.role = role;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(nick, user.nick) && Objects.equals(role,
            user.role);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(nick, role);
    }
}
