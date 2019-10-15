package ua.nure.kn.shahsko.usermanagment.db;

import java.sql.SQLException;

public class DatabaseException extends Exception {
    public DatabaseException(SQLException e) {
        super(e);
    }
}
