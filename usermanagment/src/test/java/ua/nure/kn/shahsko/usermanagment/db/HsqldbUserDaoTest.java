package ua.nure.kn.shahsko.usermanagment.db;

import org.dbunit.DatabaseTestCase;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import ua.nure.kn.shahsko.usermanagment.domain.User;

import java.util.Date;

public class HsqldbUserDaoTest extends DatabaseTestCase {

    public static final String FIRST_NAME = "John";
    public static final String LAST_NAME = "Doe";
    private HsqldbUserDao dao;

    public void testCase() throws DatabaseException {
        User user = new User();
        user.setFirstName(FIRST_NAME);
        user.setLastName(LAST_NAME);
        user.setDateOfBirth(new Date());
        assertNull(user.getId());

        User userToCheck = dao.create(user);
        assertNotNull(userToCheck);
        assertNotNull(userToCheck.getId());

        assertEquals(user.getFirstName(), userToCheck.getFirstName());
        assertEquals(user.getLastName(), userToCheck.getLastName());
        assertEquals(user.getDateOfBirth(), userToCheck.getDateOfBirth());
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        dao = new HsqldbUserDao();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @Override
    protected IDatabaseConnection getConnection() throws Exception {
        return null;
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return null;
    }
}
