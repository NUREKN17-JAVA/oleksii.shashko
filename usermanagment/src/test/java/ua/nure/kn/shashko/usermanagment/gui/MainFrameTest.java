package ua.nure.kn.shashko.usermanagment.gui;

import com.mockobjects.dynamic.Mock;
import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.TestHelper;
import junit.extensions.jfcunit.eventdata.JTableMouseEventData;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.eventdata.StringEventData;
import junit.extensions.jfcunit.finder.DialogFinder;
import junit.extensions.jfcunit.finder.NamedComponentFinder;
import ua.nure.kn.shashko.usermanagment.db.DaoFactory;
import ua.nure.kn.shashko.usermanagment.db.MockDaoFactory;
import ua.nure.kn.shashko.usermanagment.domain.User;
import ua.nure.kn.shashko.usermanagment.util.Message;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

public class MainFrameTest extends JFCTestCase {
    private static final String BROWSE_PANEL_COMPONENT_NAME = "browsePanel";
    private static final String ADD_BUTTON_COMPONENT_NAME = "addButton";
    private static final String EDIT_BUTTON_COMPONENT_NAME = "editButton";
    private static final String DELETE_BUTTON_COMPONENT_NAME = "deleteButton";
    private static final String DETAIL_BUTTON_COMPONENT_NAME = "detailButton";

    private static final String OK_BUTTON_COMPONENT_NAME = "okButton";

    private static final String USER_TABLE_COMPONENT_NAME = "userTable";

    private static final String ADD_PANEL_COMPONENT_NAME = "addPanel";
    private static final String EDIT_PANEL_COMPONENT_NAME = "editPanel";

    private static final String FIRST_NAME = "John";
    private static final String SECOND_NAME = "Doe";
    private static final Date DATE_OF_BIRTH = new Date();
    private static final String FIRST_NAME_FIELD_COMPONENT_NAME = "firstNameField";
    private static final String LAST_NAME_FIELD_COMPONENT_NAME = "lastNameField";
    private static final String DATE_OF_BIRTH_FIELD_COMPONENT_NAME = "dateOfBirthField";
    public static final String EDITED_LASTNAME = "Doew";
    public static final String EDITED_FIRSTNAME = "John";
    public static final String DIALOG_NAME_USER_INFORMATION = "User information";

    private MainFrame mainFrame;
    private Mock mockUserDao;

    public void setUp() throws Exception {
        super.setUp();

        try {
            Properties properties = new Properties();
            properties.setProperty("dao.Factory", MockDaoFactory.class.getName());

            DaoFactory.getInstance().init(properties);

            mockUserDao = ((MockDaoFactory) DaoFactory.getInstance()).getMockUserDao();
            mockUserDao.expectAndReturn("findAll", new ArrayList<>());

            setHelper(new JFCTestHelper());
            mainFrame = new MainFrame();
        } catch (Exception e) {
            e.printStackTrace();
        }

        mainFrame.setVisible(true);
    }

    public void tearDown() throws Exception {
        try {
            mockUserDao.verify();
            mainFrame.setVisible(false);
            getHelper();
            TestHelper.cleanUp(this);
            super.tearDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testBrowseControl() {
        find(JPanel.class, BROWSE_PANEL_COMPONENT_NAME);
        find(JTable.class, USER_TABLE_COMPONENT_NAME);
        find(JButton.class, ADD_BUTTON_COMPONENT_NAME);
        find(JButton.class, EDIT_BUTTON_COMPONENT_NAME);
        find(JButton.class, DELETE_BUTTON_COMPONENT_NAME);
        find(JButton.class, DETAIL_BUTTON_COMPONENT_NAME);

        JTable table = (JTable) find(JTable.class, USER_TABLE_COMPONENT_NAME);

        int expected_quantity = 3;
        assertEquals(expected_quantity, table.getColumnCount());
        assertEquals(Message.getString("id"), table.getColumnName(0));
        assertEquals(Message.getString("name"), table.getColumnName(1));
        assertEquals(Message.getString("surname"), table.getColumnName(2));
    }

    public void testAddPanelOk() {
        User user = new User(FIRST_NAME, SECOND_NAME, DATE_OF_BIRTH);
        User expectedUser = new User(1L, FIRST_NAME, SECOND_NAME, DATE_OF_BIRTH);

        mockUserDao.expectAndReturn("create", user, expectedUser);

        ArrayList users = new ArrayList();
        users.add(expectedUser);
        mockUserDao.expectAndReturn("findAll", users);

        JTable userTable = (JTable) find(JTable.class, USER_TABLE_COMPONENT_NAME);
        int expectedRows = 0;
        assertEquals(expectedRows, userTable.getRowCount());

        JButton addButton = (JButton) find(JButton.class, ADD_BUTTON_COMPONENT_NAME);
        getHelper().enterClickAndLeave(new MouseEventData(this, addButton));

        find(JPanel.class, ADD_PANEL_COMPONENT_NAME);
        fillFields(FIRST_NAME, SECOND_NAME, DATE_OF_BIRTH);

        JButton okButton = (JButton) find(JButton.class, OK_BUTTON_COMPONENT_NAME);
        getHelper().enterClickAndLeave(new MouseEventData(this, okButton));

        find(JPanel.class, BROWSE_PANEL_COMPONENT_NAME);

        userTable = (JTable) find(JTable.class, USER_TABLE_COMPONENT_NAME);
        expectedRows = 1;
        assertEquals(expectedRows, userTable.getRowCount());
    }

    public void testEditPanelOk() {
        User user = new User(FIRST_NAME, SECOND_NAME, DATE_OF_BIRTH);
        User expectedUser = new User(1L, FIRST_NAME, SECOND_NAME, DATE_OF_BIRTH);

        mockUserDao.expectAndReturn("create", user, expectedUser);

        ArrayList users = new ArrayList();
        users.add(expectedUser);

        mockUserDao.expectAndReturn("findAll", users);
        //add user to table
        JButton addButton = (JButton) find(JButton.class, ADD_BUTTON_COMPONENT_NAME);
        getHelper().enterClickAndLeave(new MouseEventData(this, addButton));

        find(JPanel.class, ADD_PANEL_COMPONENT_NAME);
        fillFields(FIRST_NAME, SECOND_NAME, DATE_OF_BIRTH);

        JButton okCreateButton = (JButton) find(JButton.class, OK_BUTTON_COMPONENT_NAME);
        getHelper().enterClickAndLeave(new MouseEventData(this, okCreateButton));

        find(JPanel.class, BROWSE_PANEL_COMPONENT_NAME);
        //edit this user
        JTable table = (JTable) find(JTable.class, USER_TABLE_COMPONENT_NAME);
        int expectedRows = 1;
        assertEquals(expectedRows, table.getRowCount());

        JButton editButton = (JButton) find(JButton.class, EDIT_BUTTON_COMPONENT_NAME);
        getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0,1,1));
        getHelper().enterClickAndLeave(new MouseEventData(this, editButton));

        find(JPanel.class, EDIT_PANEL_COMPONENT_NAME);
        fillFields(EDITED_FIRSTNAME, EDITED_LASTNAME, DATE_OF_BIRTH);

        JButton okButton = (JButton) find(JButton.class, OK_BUTTON_COMPONENT_NAME);
        getHelper().enterClickAndLeave(new MouseEventData(this, okButton));

        find(JPanel.class, BROWSE_PANEL_COMPONENT_NAME);
        table = (JTable) find(JTable.class, USER_TABLE_COMPONENT_NAME);
        assertEquals(expectedRows, table.getRowCount());
    }

    public void testDetailPanel() {
        User user = new User(FIRST_NAME, SECOND_NAME, DATE_OF_BIRTH);
        User expectedUser = new User(1L, FIRST_NAME, SECOND_NAME, DATE_OF_BIRTH);

        mockUserDao.expectAndReturn("create", user, expectedUser);

        ArrayList users = new ArrayList();
        users.add(expectedUser);

        mockUserDao.expectAndReturn("findAll", users);
        //add user to table
        JButton addButton = (JButton) find(JButton.class, ADD_BUTTON_COMPONENT_NAME);
        getHelper().enterClickAndLeave(new MouseEventData(this, addButton));

        find(JPanel.class, ADD_PANEL_COMPONENT_NAME);
        fillFields(FIRST_NAME, SECOND_NAME, DATE_OF_BIRTH);

        JButton okCreateButton = (JButton) find(JButton.class, OK_BUTTON_COMPONENT_NAME);
        getHelper().enterClickAndLeave(new MouseEventData(this, okCreateButton));

        find(JPanel.class, BROWSE_PANEL_COMPONENT_NAME);

        JTable table = (JTable) find(JTable.class, USER_TABLE_COMPONENT_NAME);
        int expectedRows = 1;
        assertEquals(expectedRows, table.getRowCount());

        JButton detailButton = (JButton) find(JButton.class, DETAIL_BUTTON_COMPONENT_NAME);
        getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0,1,1));
        getHelper().enterClickAndLeave(new MouseEventData(this, detailButton));

        DialogFinder dialogFinder = new DialogFinder(DIALOG_NAME_USER_INFORMATION);
        JDialog dialog = (JDialog) dialogFinder.find();
        assertNotNull(dialog);
    }

    private void fillFields(String firstName, String secondName, Date dataOfBirth) {
        JTextField firstNameField = (JTextField) find(JTextField.class, FIRST_NAME_FIELD_COMPONENT_NAME);
        JTextField lastNameField = (JTextField) find(JTextField.class, LAST_NAME_FIELD_COMPONENT_NAME);
        JTextField dataOfBirthField = (JTextField) find(JTextField.class, DATE_OF_BIRTH_FIELD_COMPONENT_NAME);

        getHelper().sendString(new StringEventData(this, firstNameField, firstName));
        getHelper().sendString(new StringEventData(this, lastNameField, secondName));
        String date = DateFormat.getDateInstance().format(dataOfBirth);
        getHelper().sendString(new StringEventData(this, dataOfBirthField, date));
    }

    private Component find(Class<?> componentClass, String name) {
        NamedComponentFinder finder;
        finder = new NamedComponentFinder(componentClass, name);
        finder.setWait(0);

        Component component = finder.find(mainFrame, 0);
        assertNotNull("Could not find component '" + name + "'", component);

        return component;
    }
}
