package ua.nure.kn.shashko.usermanagment.db;

import junit.framework.Assert;
import junit.framework.TestCase;
import ua.nure.kn.shashko.usermanagment.domain.User;

public class DaoFactoryTest extends TestCase {

    public void testGetUserDao() {
        DaoFactory daoFactory = DaoFactory.getInstance();
        Assert.assertNotNull(daoFactory);
        Dao<User> userDao;
        try {
            userDao = daoFactory.getUserDao();
            Assert.assertNotNull(userDao);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }
}
