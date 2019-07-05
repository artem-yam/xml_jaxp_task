package com.epam.jdbc.datalayer;

import com.epam.jdbc.datalayer.xml.XMLDAOFactory;

public enum DBType {
    XML {
        @Override
        public AbstractDAOFactory getDAOFactory() {
            return new XMLDAOFactory();
        }
    };

    public static DBType getTypeByName(String dbType) throws RuntimeException {
        return DBType.valueOf(dbType.toUpperCase());
    }

    public abstract AbstractDAOFactory getDAOFactory();

}
