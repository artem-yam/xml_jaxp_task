package com.epam.chat.datalayer;

import com.epam.chat.datalayer.xml.XMLDAOFactory;

public enum DBType {
    XML {
        @Override
        public AbstractDAOFactory getDAOFactory() {
            AbstractDAOFactory xmlDAOFactory = new XMLDAOFactory();
            return xmlDAOFactory;
        }
    };
    
    public static DBType getTypeByName(String dbType) {
        return DBType.valueOf(dbType.toUpperCase());
    }
    
    public abstract AbstractDAOFactory getDAOFactory();
    
}
