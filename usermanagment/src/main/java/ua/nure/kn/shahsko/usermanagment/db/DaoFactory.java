package ua.nure.kn.shahsko.usermanagment.db;

import ua.nure.kn.shahsko.usermanagment.domain.User;

import java.io.IOException;
import java.util.Properties;

public abstract class DaoFactory {

    private static final String DAO_FACTORY = "dao.factory";

    private static DaoFactory instance;
    private static Properties properties;
    private Dao<User> userDao;

    private static final String SETTINGS_PROPERTIES = "settings.properties";


    protected DaoFactory() {
    }

    static {
        properties = new Properties();
        try {
            properties.load(DaoFactory.class.getClassLoader().getResourceAsStream(SETTINGS_PROPERTIES));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized DaoFactory getInstance() {
        if (instance == null) {
            Class<?> factoryClass;
            try {
                factoryClass = Class.forName(properties.getProperty(DAO_FACTORY));
                instance = (DaoFactory) factoryClass.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }

    private ConnectionFactory createConnection() {
        String user = properties.getProperty("connection.user");
        String password = properties.getProperty("connection.password");
        String url = properties.getProperty("connection.url");
        String driver = properties.getProperty("connection.driver");

        return new ConnectionFactoryImpl(user, password, url, driver);
    }

    public Dao<User> getUserDao() throws ReflectiveOperationException {
        try {
            Class UserDaoClass = Class.forName(properties.getProperty("dao.ua.nure.kn.shahsko.usermanagment.db.Dao"));
            userDao = (Dao<User>) UserDaoClass.newInstance();
            userDao.setConnectionFactory(createConnection());
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            throw new ReflectiveOperationException(e);
        }
        
        return userDao;
    }
}
