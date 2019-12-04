package ua.nure.kn.shahsko.usermanagment.gui;

import ua.nure.kn.shahsko.usermanagment.db.DatabaseException;
import ua.nure.kn.shahsko.usermanagment.domain.User;
import ua.nure.kn.shahsko.usermanagment.util.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BrowsePanel extends JPanel implements ActionListener {
    private static final String BROWSE_PANEL_COMPONENT_NAME = "browsePanel";
    private static final String ADD_BUTTON_COMPONENT_NAME = "addButton";
    private static final String EDIT_BUTTON_COMPONENT_NAME = "editButton";
    private static final String DELETE_BUTTON_COMPONENT_NAME = "deleteButton";
    private static final String DETAIL_BUTTON_COMPONENT_NAME = "detailButton";

    private static final String USER_TABLE_COMPONENT_NAME = "userTable";

    private static final String ADD_COMMAND = "add";
    private static final String EDIT_COMMAND = "edit";
    private static final String DELETE_COMMAND = "delete";
    private static final String DETAIL_COMMAND = "detail";

    private final MainFrame parent;

    private JScrollPane tablePanel;
    private JPanel buttonPanel;

    private JTable userTable;

    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton detailsButton;

    BrowsePanel(MainFrame mainFrame) {
        parent = mainFrame;
        initialize();
    }

    private void initialize() {
        this.setName(BROWSE_PANEL_COMPONENT_NAME);
        this.setLayout(new BorderLayout());
        this.add(getTablePanel(), BorderLayout.CENTER);
        this.add(getButtonPanel(), BorderLayout.SOUTH);
    }

    private JPanel getButtonPanel() {
        if(buttonPanel == null) {
            buttonPanel = new JPanel();
            buttonPanel.add(getAddButton(), null);
            buttonPanel.add(getEditButton(), null);
            buttonPanel.add(getDeleteButton(), null);
            buttonPanel.add(getDetailsButton(), null);
        }
        return buttonPanel;
    }

    private JButton getDetailsButton() {
        if(detailsButton == null) {
            detailsButton = new JButton();
            detailsButton.setText(Message.getString("user.details_button")); //("Подробнее");
            detailsButton.setName(DETAIL_BUTTON_COMPONENT_NAME);
            detailsButton.setActionCommand(DETAIL_COMMAND);
            detailsButton.addActionListener(this);
        }
        return detailsButton;
    }

    private JButton getDeleteButton() {
        if(deleteButton == null) {
            deleteButton = new JButton();
            deleteButton.setText(Message.getString("delete.user_button")); //("Удалить");
            deleteButton.setName(DELETE_BUTTON_COMPONENT_NAME);
            deleteButton.setActionCommand(DELETE_COMMAND);
            deleteButton.addActionListener(this);
        }
        return deleteButton;
    }

    private JButton getEditButton() {
        if(editButton == null) {
            editButton = new JButton();
            editButton.setText(Message.getString("edit.user_button")); //("Изменить");
            editButton.setName(EDIT_BUTTON_COMPONENT_NAME);
            editButton.setActionCommand(EDIT_COMMAND);
            editButton.addActionListener(this);
        }
        return editButton;
    }

    private JButton getAddButton() {
        if(addButton == null) {
            addButton = new JButton();
            addButton.setText(Message.getString("add.user_button")); //("Добавить");
            addButton.setName(ADD_BUTTON_COMPONENT_NAME);
            addButton.setActionCommand(ADD_COMMAND);
            addButton.addActionListener(this);
        }
        return addButton;
    }

    private JScrollPane getTablePanel() {
        if(tablePanel == null) {
            tablePanel = new JScrollPane(getUserTable());
        }
        return tablePanel;
    }

    private JTable getUserTable() {
        if(userTable == null) {
            userTable = new JTable();
            userTable.setName(USER_TABLE_COMPONENT_NAME);
        }
        return userTable;
    }

    public void initTable() {
        UserTableModel model = null;

        try {
            model = new UserTableModel(parent.getUserDao().findAll());
        } catch (DatabaseException e) {
            model = new UserTableModel(new ArrayList());
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        getUserTable().setModel(model);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        if (ADD_COMMAND.equalsIgnoreCase(actionCommand)) {
            this.setVisible(false);
            parent.showAddPanel();
        }
        if (EDIT_COMMAND.equalsIgnoreCase(actionCommand)) {
            int selectedRow = userTable.getSelectedRow();
            int selectedColumn = userTable.getSelectedColumn();

            if (selectedColumn != -1 || selectedRow != -1) {
                this.setVisible(false);
                parent.showEditPanel();
            } else {
                JOptionPane.showMessageDialog(this, "Select user in the table, please!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (DELETE_COMMAND.equalsIgnoreCase(actionCommand)) {
            User selectedUser = getSelectedUser();
            if (selectedUser != null) {
                int result = JOptionPane.showConfirmDialog(this, "Are you sure to delete this user?",
                        "Confirm deleting", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (result == JOptionPane.YES_OPTION) {
                    try {
                        parent.getUserDao().delete(selectedUser);
                        getUserTable().setModel(new UserTableModel(parent.getUserDao().findAll()));
                    } catch (DatabaseException ex) {
                        JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
        if (DETAIL_COMMAND.equalsIgnoreCase(actionCommand)) {
            User selectedUser = getSelectedUser();
            JOptionPane.showMessageDialog(this, selectedUser.toString(), "User information", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public User getSelectedUser() {
        int selectedRow = getUserTable().getSelectedRow();
        int IdColumn = 0;

        User user = null;

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please, select user in the table.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            Long userId = (Long) userTable.getValueAt(userTable.getSelectedRow(), IdColumn);
            try {
                user = parent.getUserDao().find(userId);
            } catch (DatabaseException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        return user;
    }
}
