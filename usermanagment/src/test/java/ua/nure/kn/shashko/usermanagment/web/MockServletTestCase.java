package ua.nure.kn.shashko.usermanagment.web;

import com.mockobjects.dynamic.Mock;
import com.mockrunner.servlet.BasicServletTestCaseAdapter;
import ua.nure.kn.shashko.usermanagment.db.DaoFactory;
import ua.nure.kn.shashko.usermanagment.db.MockDaoFactory;

import java.util.Properties;

public abstract class MockServletTestCase extends BasicServletTestCaseAdapter {
    private Mock mockUserDao;

    public Mock getMockUserDao() {
        return mockUserDao;
    }

    public void setMockUserDao(Mock mockUserDao) {
        this.mockUserDao = mockUserDao;
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        Properties properties = new Properties();
        properties.setProperty("dao.Factory", MockDaoFactory.class.getName());
        DaoFactory.init(properties);
        mockUserDao = ((MockDaoFactory) DaoFactory.getInstance()).getMockUserDao();
    }

    @Override
    public void tearDown() throws Exception {
        mockUserDao.verify();
        super.tearDown();
    }
}
