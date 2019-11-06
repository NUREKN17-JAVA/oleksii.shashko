package ua.nure.kn.shahsko.usermanagment.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactoryImpl implements ConnectionFactory {

    private String url;
    private String user;
    private String password;
    private String driver;

    ConnectionFactoryImpl() {}

    ConnectionFactoryImpl(String user, String password, String url, String driver) {
        this.user = user;
        this.url = url;
        this.password = password;
        this.driver = driver;
    }

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
