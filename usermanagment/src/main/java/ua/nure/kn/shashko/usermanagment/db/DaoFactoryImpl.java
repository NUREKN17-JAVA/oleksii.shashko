package ua.nure.kn.shashko.usermanagment.db;

import ua.nure.kn.shashko.usermanagment.domain.User;

public class DaoFactoryImpl extends DaoFactory {

    @Override
    public Dao<User> getUserDao() throws ReflectiveOperationException {
        Dao<User> userDao = null;
        try {
            Class<?> clazz= Class.forName(properties.getProperty(USER_DAO));
            userDao = (Dao<User>) clazz.newInstance();
            userDao.setConnectionFactory(getConnectionFactory());
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            throw new ReflectiveOperationException(e);
        }

        return userDao;
    }
}
