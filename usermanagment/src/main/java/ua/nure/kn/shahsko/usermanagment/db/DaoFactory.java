package ua.nure.kn.shahsko.usermanagment.db;

import ua.nure.kn.shahsko.usermanagment.domain.User;

import java.io.IOException;
import java.util.Properties;

public class DaoFactory {

    private static final String USER_DAO = "dao.UserDao";

    private static final DaoFactory INSTANCE = new DaoFactory();
    public static final String USER = "connection.user";
    public static final String PASSWORD = "connection.password";
    public static final String URL = "connection.url";
    public static final String DRIVER = "connection.driver";
    private static Properties properties;

    private static final String SETTINGS_PROPERTIES = "settings.properties";

    DaoFactory() {
        properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream(SETTINGS_PROPERTIES));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static DaoFactory getInstance() {
        return INSTANCE;
    }

    private ConnectionFactory createConnection() {
        String user = properties.getProperty(USER);
        String password = properties.getProperty(PASSWORD);
        String url = properties.getProperty(URL);
        String driver = properties.getProperty(DRIVER);

        return new ConnectionFactoryImpl(user, password, url, driver);
    }

    public Dao<User> getUserDao() throws ReflectiveOperationException {
        Dao<User> userDao = null;
        try {
            Class UserDaoClass = Class.forName(properties.getProperty(USER_DAO));
            userDao = (Dao<User>) UserDaoClass.newInstance();
            userDao.setConnectionFactory(createConnection());
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            throw new ReflectiveOperationException(e);
        }

        return userDao;
    }
}
