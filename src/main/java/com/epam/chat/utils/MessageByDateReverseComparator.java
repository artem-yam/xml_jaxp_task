package com.epam.chat.utils;

import com.epam.chat.datalayer.dto.Message;

import java.util.Comparator;

public class MessageByDateReverseComparator<T extends Message>
        implements Comparator<T> {
    @Override
    public int compare(T o1, T o2) {
        return o2.getTimeStamp()
                .compareTo(o1.getTimeStamp());
    }
}
