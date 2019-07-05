package ru.rsreu.Yamschikov.datalayer.oracledb;

import ru.rsreu.Yamschikov.datalayer.DAOFactory;
import ru.rsreu.Yamschikov.datalayer.ScientificActivityDAO;
import ru.rsreu.Yamschikov.datalayer.UserDAO;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;

public class OracleDBDAOFactory extends DAOFactory {
    private static volatile OracleDBDAOFactory instance;
    private Connection connection;
    
    private OracleDBDAOFactory() {
    }
    
    public static OracleDBDAOFactory getInstance()
        throws SQLException, NamingException {
        OracleDBDAOFactory factory = instance;
        if (instance == null) {
            synchronized (OracleDBDAOFactory.class) {
                instance = factory = new OracleDBDAOFactory();
                factory.connected();
            }
        }
        return factory;
    }
    
    private void connected()
        throws SQLException, NamingException {
        Locale.setDefault(Locale.ENGLISH);

		/*Class.forName(("oracledb.class"));
		connection = DriverManager.getConnection(("oracledb.url"),
				("oracledb.user"),
				("oracledb.password"));*/
        
        Context ctx = new InitialContext();
        DataSource ds = (DataSource)
                            ctx.lookup("jdbc/example");
        connection = ds.getConnection();
        
        System.out.println("Connected to oracle DB!");
    }
    
    @Override
    public UserDAO getUserDAO() {
        return new OracleUserDAO(connection);
    }
    
    @Override
    public ScientificActivityDAO getScientificActivityDAO() {
        return new OracleScientificActivityDAO(connection);
    }
}
