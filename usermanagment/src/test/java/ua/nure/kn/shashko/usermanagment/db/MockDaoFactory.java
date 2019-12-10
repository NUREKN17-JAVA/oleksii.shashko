package ua.nure.kn.shashko.usermanagment.db;

import com.mockobjects.dynamic.Mock;
import ua.nure.kn.shashko.usermanagment.domain.User;

public class MockDaoFactory extends DaoFactory {
    private Mock mockUserDao;

    MockDaoFactory() {
        mockUserDao = new Mock(Dao.class);
    }

    public Mock getMockUserDao() {
        return mockUserDao;
    }

    @Override
    public Dao<User> getUserDao() throws ReflectiveOperationException {
        return (Dao<User>) mockUserDao.proxy();
    }
}
