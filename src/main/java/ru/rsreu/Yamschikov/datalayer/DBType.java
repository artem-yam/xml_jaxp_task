package ru.rsreu.Yamschikov.datalayer;

import ru.rsreu.Yamschikov.datalayer.oracledb.OracleDBDAOFactory;

import javax.naming.NamingException;
import java.sql.SQLException;

public enum DBType {
    ORACLE {
        @Override
        public DAOFactory getDAOFactory() {
            DAOFactory oracleDBDAOFactory = null;
            try {
                oracleDBDAOFactory = OracleDBDAOFactory.getInstance();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NamingException e) {
                e.printStackTrace();
            }
            return oracleDBDAOFactory;
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
