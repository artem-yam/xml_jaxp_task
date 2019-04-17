package com.epam.chat.datalayer.dto;

public class Status {

    private static final String LOGIN_DESCRIPTION = "User logged in";
    private static final String MESSAGE_DESCRIPTION = "User sent message";
    private static final String KICK_DESCRIPTION =
        "User kicked another user, shown in \'message\' field";
    private static final String LOGOUT_DESCRIPTION = "User left chat";

    private StatusTitle title;
    private String description;

    public Status(StatusTitle title, String description) {
        this.title = title;
        this.description = description;
    }

    public Status(StatusTitle title) {
        this.title = title;

        setDescription(title);
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

    private void setDescription(StatusTitle title) {
        switch (title) {
            case LOGIN:
                description = LOGIN_DESCRIPTION;
                break;
            case MESSAGE:
                description = MESSAGE_DESCRIPTION;
                break;
            case KICK:
                description =
                    KICK_DESCRIPTION;
                break;
            case LOGOUT:
                description = LOGOUT_DESCRIPTION;
                break;
            default:
                description = "";
        }
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
