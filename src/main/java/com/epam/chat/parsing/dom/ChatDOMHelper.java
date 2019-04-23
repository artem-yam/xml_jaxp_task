package com.epam.chat.parsing.dom;

import com.epam.chat.datalayer.dto.Message;
import com.epam.chat.datalayer.dto.User;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.text.SimpleDateFormat;

public class ChatDOMHelper extends DOMHelper {
    
    private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd'T'kk:mm:ss";
    private static final String MESSAGE_MAIN_ELEMENT = "Message";
    private static final String MESSAGE_USER_ELEMENT = "user_from";
    private static final String MESSAGE_DATE_ELEMENT = "time_stamp";
    private static final String MESSAGE_TEXT_ELEMENT = "message";
    private static final String MESSAGE_STATUS_ELEMENT = "status";
    private static final String[] MESSAGE_CHILD_ELEMENTS =
        new String[]{MESSAGE_USER_ELEMENT, MESSAGE_DATE_ELEMENT,
            MESSAGE_TEXT_ELEMENT, MESSAGE_STATUS_ELEMENT};
    
    private static final String USER_MAIN_ELEMENT = "User";
    private static final String USER_NICK_ELEMENT = "nick";
    private static final String USER_ROLE_ELEMENT = "role";
    private static final String[] USER_CHILD_ELEMENTS =
        new String[]{USER_NICK_ELEMENT, USER_ROLE_ELEMENT};
    
    public Node formMessageNode(Document document, Message message) {
        return createElementWithSimpleChildren(document, MESSAGE_MAIN_ELEMENT,
            MESSAGE_CHILD_ELEMENTS,
            new String[]{message.getSenderNick(), new SimpleDateFormat(
                DATE_FORMAT_PATTERN).format(message.getTimeStamp()),
                message.getMessage(),
                message.getStatus().getTitle().toString()});
    }
    
    public Node formUserNode(Document document, User user) {
        return createElementWithSimpleChildren(document, USER_MAIN_ELEMENT,
            USER_CHILD_ELEMENTS,
            new String[]{user.getNick(), user.getRole().getTitle().toString()});
    }
    
}
