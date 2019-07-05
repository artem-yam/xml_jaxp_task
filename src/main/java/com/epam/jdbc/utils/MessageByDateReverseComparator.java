package com.epam.jdbc.utils;

import com.epam.jdbc.datalayer.dto.Message;

import java.util.Comparator;

public class MessageByDateReverseComparator<T extends Message>
    implements Comparator<T> {
    @Override
    public int compare(T o1, T o2) {
        int result = 0;
        
        if (o1 != o2) {
            if (o1 == null) {
                result = -1;
            } else if (o2 == null) {
                result = 1;
            } else {
                result = o2.getTimeStamp().compareTo(o1.getTimeStamp());
            }
            
        }
        
        return result;
    }
}
