package ua.nure.kn.shahsko.usermanagment.db;

import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import ua.nure.kn.shahsko.usermanagment.domain.User;

import java.util.Collection;
import java.util.Date;

public class HsqldbUserDaoTest extends DatabaseTestCase {

    public static final String FIRST_NAME = "John";
    public static final String LAST_NAME = "Doe";
    public static final int NUMBER_OF_ROWS = 2;
    private HsqldbUserDao dao;
    private ConnectionFactory connectionFactory;

    public void testCase() throws DatabaseException {
        try {
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
        } catch (DatabaseException e) {
            fail(e.toString());
        }
    }

    public void testFindAll() throws DatabaseException {
        Collection<User> items = dao.findAll();
        assertNotNull(items);
        assertEquals("Collection size doesn't match ethalon.", NUMBER_OF_ROWS, items.size());
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        dao = new HsqldbUserDao(connectionFactory);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @Override
    protected IDatabaseConnection getConnection() throws Exception {
        connectionFactory = new ConnectionFactoryImpl();
        return new DatabaseConnection(connectionFactory.createConnection());
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        IDataSet dataSet = new XmlDataSet(
                getClass()
                        .getClassLoader()
                        .getResourceAsStream("userDataSet.xml"));
        return dataSet;
    }
}
