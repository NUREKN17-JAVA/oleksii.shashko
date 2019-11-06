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

    private static final String CONNECTIO_USER = "sa";
    private static final String CONNECTION_PASSWORD = "";
    private static final String CONNECTION_URL = "jdbc:hsqldb:file:db/usermanagment";
    private static final String CONNECTION_DRIVER = "org.hsqldb.jdbcDriver";

    private User user;
    private static final String FIRST_NAME = "John";
    private static final String UPDATED_FIRST_NAME = "Johny";

    private static final String LAST_NAME = "Doe";
    private static final String UPDATED_LAST_NAME = "Maroon";
    private static final Long TEST_ID = 4L;

    private static final int NUMBER_OF_ROWS = 2;

    private HsqldbUserDao dao;
    private ConnectionFactory connectionFactory;

    public void testCreate() throws DatabaseException {
        try {
            assertNotNull(user.getId());

            User userToCheck = dao.create(user);
            assertNotNull(userToCheck);

            assertEquals(user.getFirstName(), userToCheck.getFirstName());
            assertEquals(user.getLastName(), userToCheck.getLastName());
            assertEquals(user.getDateOfBirth(), userToCheck.getDateOfBirth());
        } catch (DatabaseException e) {
            fail(e.toString());
        }
    }

    public void testFind() throws DatabaseException {
        assertNotNull(user.getId());

        User ethalonUser = dao.create(user);
        User findedUser = dao.find(ethalonUser.getId());

        assertNotNull(findedUser);
        assertEquals(ethalonUser.getId(), findedUser.getId());
        assertEquals(ethalonUser.getFirstName(), findedUser.getFirstName());
        assertEquals(ethalonUser.getLastName(), findedUser.getLastName());

    }

    public void testFindAll() throws DatabaseException {
        Collection<User> items = dao.findAll();
        assertNotNull(items);
        assertEquals("Collection size doesn't match ethalon.", NUMBER_OF_ROWS, items.size());
    }

    public void testUpdate1() throws DatabaseException {
        User testUser = user;
        dao.create(user);

        testUser.setFirstName(UPDATED_FIRST_NAME);

        dao.update(testUser);
        User updatedUser = dao.find(testUser.getId());

        assertNotNull(updatedUser);
        assertEquals(testUser.getFirstName(), updatedUser.getFirstName());
    }

    public void testUpdate2() throws DatabaseException {
        User testUser = user;
        dao.create(user);

        testUser.setLastName(UPDATED_LAST_NAME);

        dao.update(testUser);
        User updatedUser = dao.find(testUser.getId());

        assertNotNull(updatedUser);
        assertEquals(testUser.getLastName(), updatedUser.getLastName());
    }

    public void testDelete() throws DatabaseException {
        User deletedUser = dao.create(user);
        dao.delete(deletedUser);
        assertNull(dao.find(deletedUser.getId()));
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        user = new User();
        user.setId(TEST_ID);
        user.setFirstName(FIRST_NAME);
        user.setLastName(LAST_NAME);
        user.setDateOfBirth(new Date());

        dao = new HsqldbUserDao(connectionFactory);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @Override
    protected IDatabaseConnection getConnection() throws Exception {
        connectionFactory = new ConnectionFactoryImpl(CONNECTIO_USER, CONNECTION_PASSWORD, CONNECTION_URL, CONNECTION_DRIVER);
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
