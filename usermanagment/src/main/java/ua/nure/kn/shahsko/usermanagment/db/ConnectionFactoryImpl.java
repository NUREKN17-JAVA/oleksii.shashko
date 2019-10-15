package ua.nure.kn.shahsko.usermanagment.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactoryImpl implements ConnectionFactory {

    private String url = "jdbc:hsqldb:file:db/usermanagement";
    private String user = "sa";
    private String password = "";
    private String driver = "org.hsqldb.jdbcDriver";

    @Override
    public Connection createConnection() throws DatabaseException {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }
}
