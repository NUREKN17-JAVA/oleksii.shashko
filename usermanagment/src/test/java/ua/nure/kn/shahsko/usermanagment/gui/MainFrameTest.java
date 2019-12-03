package ua.nure.kn.shahsko.usermanagment.gui;

import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.TestHelper;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.eventdata.StringEventData;
import junit.extensions.jfcunit.finder.NamedComponentFinder;
import ua.nure.kn.shahsko.usermanagment.util.Message;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.util.Date;

public class MainFrameTest extends JFCTestCase {
    private static final String BROWSE_PANEL_COMPONENT_NAME = "browsePanel";
    private static final String ADD_BUTTON_COMPONENT_NAME = "addButton";
    private static final String EDIT_BUTTON_COMPONENT_NAME = "editButton";
    private static final String DELETE_BUTTON_COMPONENT_NAME = "deleteButton";
    private static final String DETAIL_BUTTON_COMPONENT_NAME = "detailButton";

    private static final String OK_BUTTON_COMPONENT_NAME = "okButton";

    private static final String USER_TABLE_COMPONENT_NAME = "userTable";

    private static final String ADD_PANEL_COMPONENT_NAME = "addPanel";

    private static final String FIRST_NAME = "John";
    private static final String SECOND_NAME = "Doe";
    private static final Date DATA_OF_BIRTH = new Date();
    private static final String FIRST_NAME_FIELD_COMPONENT_NAME = "firstNameField";
    private static final String LAST_NAME_FIELD_COMPONENT_NAME = "lastNameField";
    private static final String DATE_OF_BIRTH_FIELD_COMPONENT_NAME = "dateOfBirthField";

    private MainFrame mainFrame;

    public void setUp() throws Exception {
        super.setUp();
        mainFrame = new MainFrame();
        setHelper(new JFCTestHelper());
        mainFrame.setVisible(true);
    }

    public void tearDown() throws Exception {
        mainFrame.setVisible(false);
        getHelper();
        TestHelper.cleanUp(this);
        super.tearDown();
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
        JTable userTable = (JTable) find(JTable.class, USER_TABLE_COMPONENT_NAME);
        int expectedRows = 0;
        assertEquals(expectedRows, userTable.getRowCount());

        JButton addButton = (JButton) find(JButton.class, ADD_BUTTON_COMPONENT_NAME);
        getHelper().enterClickAndLeave(new MouseEventData(this, addButton));

        find(JPanel.class, ADD_PANEL_COMPONENT_NAME);
        fillFields(FIRST_NAME, SECOND_NAME, DATA_OF_BIRTH);

        JButton okButton = (JButton) find(JButton.class, OK_BUTTON_COMPONENT_NAME);
        getHelper().enterClickAndLeave(new MouseEventData(this, okButton));

        find(JPanel.class, BROWSE_PANEL_COMPONENT_NAME);

        userTable = (JTable) find(JTable.class, USER_TABLE_COMPONENT_NAME);
        expectedRows = 1;
        assertEquals(expectedRows, userTable.getRowCount());
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
