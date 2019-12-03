package ua.nure.kn.shahsko.usermanagment.gui;

import ua.nure.kn.shahsko.usermanagment.db.DatabaseException;
import ua.nure.kn.shahsko.usermanagment.domain.User;
import ua.nure.kn.shahsko.usermanagment.util.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;

public class AddPanel extends JPanel implements ActionListener {
    private static final String ADD_PANEL_COMPONENT_NAME = "addPanel";

    private static final String OK_BUTTON_COMPONENT_NAME = "okButton";
    private static final String CANCEL_BUTTON_COMPONENT_NAME = "cancelButton";

    private static final String OK_COMMAND = "ok";
    private static final String CANCEL_COMMAND = "cancel";

    private static final String FIRST_NAME_FIELD_COMPONENT_NAME = "firstNameField";
    private static final String LAST_NAME_FIELD_COMPONENT_NAME = "lastNameField";
    private static final String DATE_OF_BIRTH_FIELD_COMPONENT_NAME = "dateOfBirthField";

    private final MainFrame parent;
    private JPanel buttonPanel;
    private JButton okButton;
    private JButton cancelButton;
    private JPanel fieldPanel;

    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField dateOfBirthField;

    public AddPanel(MainFrame mainFrame) {
        parent = mainFrame;
        initialize();
    }

    private void initialize() {
        this.setName(ADD_PANEL_COMPONENT_NAME);
        this.setLayout(new BorderLayout());
        this.add(getFieldPanel(), BorderLayout.NORTH);
        this.add(getButtonPanel(), BorderLayout.SOUTH);
    }

    private JPanel getFieldPanel() {
        if (fieldPanel == null) {
            fieldPanel = new JPanel();
            fieldPanel.setLayout(new GridLayout(3, 2));
            addLabeledField(fieldPanel, Message.getString("name_label")/*"Имя"*/, getFirstNameField());
            addLabeledField(fieldPanel, Message.getString("surname_label")/*"Фамилия"*/, getLastNameField());
            addLabeledField(fieldPanel, Message.getString("date.of.birth_label")/*"Дата Рождения"*/, getDateOfBirthField());
        }
        return fieldPanel;
    }

    private JTextField getDateOfBirthField() {
        if (dateOfBirthField == null) {
            dateOfBirthField = new JTextField();
            dateOfBirthField.setName(DATE_OF_BIRTH_FIELD_COMPONENT_NAME);
        }
        return dateOfBirthField;
    }

    private void addLabeledField(JPanel panel, String name, JTextField field) {
        JLabel label = new JLabel(name);
        label.setLabelFor(field);
        panel.add(label);
        panel.add(field);
    }

    private JTextField getLastNameField() {
        if (lastNameField == null) {
            lastNameField = new JTextField();
            lastNameField.setName(LAST_NAME_FIELD_COMPONENT_NAME);
        }
        return lastNameField;
    }

    private JTextField getFirstNameField() {
        if (firstNameField == null) {
            firstNameField = new JTextField();
            firstNameField.setName(FIRST_NAME_FIELD_COMPONENT_NAME);
        }
        return firstNameField;
    }

    private JPanel getButtonPanel() {
        if(buttonPanel == null) {
            buttonPanel = new JPanel();
            buttonPanel.add(getOkButton(), null);
            buttonPanel.add(getCancelButton(), null);
        }
        return buttonPanel;
    }

    private JButton getCancelButton() {
        if(cancelButton == null) {
            cancelButton = new JButton();
            cancelButton.setText(Message.getString("cancel_button")); //("Окей");
            cancelButton.setName(CANCEL_BUTTON_COMPONENT_NAME);
            cancelButton.setActionCommand(CANCEL_COMMAND);
            cancelButton.addActionListener(this);
        }
        return cancelButton;
    }

    private JButton getOkButton() {
        if(okButton == null) {
            okButton = new JButton();
            okButton.setText(Message.getString("submit_button")); //("Окей");
            okButton.setName(OK_BUTTON_COMPONENT_NAME);
            okButton.setActionCommand(OK_COMMAND);
            okButton.addActionListener(this);
        }
        return okButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("ok".equalsIgnoreCase(e.getActionCommand())){
            User user = new User();
            user.setFirstName(getFirstNameField().getText());
            user.setLastName(getLastNameField().getText());

            DateFormat format = DateFormat.getDateInstance();
            try {
                user.setDateOfBirth(format.parse(getDateOfBirthField().getText()));
            } catch (ParseException ex) {
                getDateOfBirthField().setBackground(Color.RED);
                return;
            }

            try {
                parent.getUserDao().create(user);
            } catch (DatabaseException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        clearFields();
        this.setVisible(false);
        parent.showBrowsePanel();
    }

    private void clearFields() {
        Color bgColor = Color.WHITE;

        getFirstNameField().setText("");
        getFirstNameField().setBackground(bgColor);

        getLastNameField().setText("");
        getLastNameField().setBackground(bgColor);

        getDateOfBirthField().setText("");
        getDateOfBirthField().setBackground(bgColor);
    }
}
