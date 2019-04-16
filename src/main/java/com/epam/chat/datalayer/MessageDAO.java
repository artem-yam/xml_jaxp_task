package com.epam.chat.datalayer;

import com.epam.chat.datalayer.dto.Message;
import com.epam.chat.datalayer.dto.User;

import java.util.List;

public interface MessageDAO {

    List<Message> getLast(int count);

    void sendMessage(User user, Message message);
}
