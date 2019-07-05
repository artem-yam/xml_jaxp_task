package com.epam.jdbc.datalayer.dto;

import java.util.Objects;

public class Role {
    
    private RoleTitle title;
    private String description;
    
    public Role(RoleTitle title, String description) {
        this.title = title;
        this.description = description;
    }
    
    public Role() {
    }
    
    public RoleTitle getTitle() {
        return title;
    }
    
    public void setTitle(RoleTitle title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Role role = (Role) o;
        return title == role.title && Objects.equals(description,
            role.description);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(title, description);
    }
}
