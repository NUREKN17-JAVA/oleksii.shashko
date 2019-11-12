package ua.nure.kn.shahsko.usermanagment.db;

import ua.nure.kn.shahsko.usermanagment.domain.User;

import java.io.IOException;
import java.util.Properties;

public abstract class DaoFactory {

    private static final String DAO_FACTORY = "dao.Factory";
    protected static final String USER_DAO = "dao.UserDao";

    private static DaoFactory instance;
    public static final String USER = "connection.user";
    public static final String PASSWORD = "connection.password";
    public static final String URL = "connection.url";
    public static final String DRIVER = "connection.driver";
    protected static Properties properties;

    private static final String SETTINGS_PROPERTIES = "settings.properties";

    static  {
        properties = new Properties();
        try {
            properties.load(DaoFactory.class.getClassLoader().getResourceAsStream(SETTINGS_PROPERTIES));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected DaoFactory(){
    }

    public static synchronized DaoFactory getInstance() {
        if (instance == null){
            try {
                Class factoryClass = Class.forName(properties
                        .getProperty(DAO_FACTORY));
                instance = (DaoFactory) factoryClass.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }

    public static void init(Properties prop) {
        properties = prop;
        instance = null;
    }

    private ConnectionFactory createConnection() {
        String user = properties.getProperty(USER);
        String password = properties.getProperty(PASSWORD);
        String url = properties.getProperty(URL);
        String driver = properties.getProperty(DRIVER);

        return new ConnectionFactoryImpl(user, password, url, driver);
    }

    protected ConnectionFactory getConnectionFactory() {
        return new ConnectionFactoryImpl(properties);
    }

    public abstract Dao<User> getUserDao() throws ReflectiveOperationException;
}
