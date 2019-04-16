package com.epam.chat.datalayer;

import com.epam.chat.datalayer.xml.XMLDAOFactory;

public enum DBType {
    XML {
        @Override
        public DAOFactory getDAOFactory() {
            DAOFactory xmlDAOFactory = new XMLDAOFactory();
            return xmlDAOFactory;
        }
    };

    public static DBType getTypeByName(String dbType) {
        try {
            return DBType.valueOf(dbType.toUpperCase());
        } catch (Exception e) {
            throw new DBTypeException();
        }
    }

    public abstract DAOFactory getDAOFactory();

}
