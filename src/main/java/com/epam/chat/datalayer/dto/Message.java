package com.epam.chat.datalayer.dto;

import java.util.Date;

public class Message {
    
    private String senderNick;
    private Date timeStamp;
    private String message;
    private Status status;
    
    public Message(String senderNick, Date timeStamp, String message,
                   Status status) {
        this.senderNick = senderNick;
        this.timeStamp = timeStamp;
        this.message = message;
        this.status = status;
    }
    
    public Message() {
    }
    
    public String getSenderNick() {
        return senderNick;
    }
    
    public void setSenderNick(String senderNick) {
        this.senderNick = senderNick;
    }
    
    public Date getTimeStamp() {
        return timeStamp;
    }
    
    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public Status getStatus() {
        return status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
    
}
