package com.epam.chat.datalayer.dto;

public class Role {

    private RoleTitle title;
    private String description;

    public Role(RoleTitle title, String description) {
        this.title = title;
        this.description = description;
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
}
