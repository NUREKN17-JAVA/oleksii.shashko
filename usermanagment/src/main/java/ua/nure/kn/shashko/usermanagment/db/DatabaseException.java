package ua.nure.kn.shashko.usermanagment.db;

import java.sql.SQLException;

public class DatabaseException extends Exception {
    public DatabaseException(SQLException e) {
        super(e);
    }

    public DatabaseException(String s) {
        super(s);
    }
}
