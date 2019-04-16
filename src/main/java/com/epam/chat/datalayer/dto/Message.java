package com.epam.chat.datalayer.dto;

import java.util.Date;

public class Message {

    private User fromUser;
    private Date timeStamp;
    private String message;
    private Status status;

    public Message(User fromUser, Date timeStamp, String message,
                   Status status) {
        this.fromUser = fromUser;
        this.timeStamp = timeStamp;
        this.message = message;
        this.status = status;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
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
