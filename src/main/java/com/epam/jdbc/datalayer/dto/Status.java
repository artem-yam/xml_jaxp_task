package com.epam.jdbc.datalayer.dto;

public class Status {
    
    private StatusTitle title;
    private String description;
    
    public Status(StatusTitle title, String description) {
        this.title = title;
        this.description = description;
    }
    
    public Status() {
    }
    
    public StatusTitle getTitle() {
        return title;
    }
    
    public void setTitle(StatusTitle title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
}
