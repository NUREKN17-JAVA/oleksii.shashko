package ua.nure.kn.shahsko.usermanagment.gui;

import ua.nure.kn.shahsko.usermanagment.util.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditPanel extends JPanel implements ActionListener {

    private static final String EDIT_PANEL_COMPONENT_NAME = "editPanel";
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private static final String FIRST_NAME_FIELD_COMPONENT_NAME = "firstNameField";
    private static final String LAST_NAME_FIELD_COMPONENT_NAME = "lastNameField";
    private static final String DATE_OF_BIRTH_FIELD_COMPONENT_NAME = "dateOfBirthField";

    private static final String OK_BUTTON_COMPONENT_NAME = "okButton";
    private static final String CANCEL_BUTTON_COMPONENT_NAME = "cancelButton";

    private static final String OK_COMMAND = "ok";
    private static final String CANCEL_COMMAND = "cancel";

    private final MainFrame parent;

    private JPanel fieldPanel;
    private JPanel buttonPanel;

    private JTextField firstNameField;
    private JTextField dateOfBirthField;
    private JTextField lastNameField;

    private JButton submitButton;
    private JButton cancelButton;

    EditPanel(MainFrame frame) {
        this.parent = frame;
        initialize();
    }

    private void initialize() {
        this.setName(EDIT_PANEL_COMPONENT_NAME);
        this.setSize(WIDTH, HEIGHT);
        this.setLayout(new BorderLayout());
        this.add(getFieldPanel(), BorderLayout.NORTH);
        this.add(getButtonPanel(), BorderLayout.SOUTH);
    }

    private JPanel getButtonPanel() {
        if (buttonPanel == null) {
            buttonPanel = new JPanel();
            buttonPanel.add(getOkButton(), null);
            buttonPanel.add(getCancelButton(), null);
        }
        return buttonPanel;
    }

    private JButton getCancelButton() {
        if (cancelButton == null) {
            cancelButton = new JButton();
            cancelButton.setName(CANCEL_BUTTON_COMPONENT_NAME);
            cancelButton.setText(Message.getString("cancel_button")); //("Отменить");
            cancelButton.setActionCommand(CANCEL_COMMAND);
            cancelButton.addActionListener(this);
        }
        return cancelButton;
    }

    private JButton getOkButton() {
        if (submitButton == null) {
            submitButton = new JButton();
            submitButton.setName(OK_BUTTON_COMPONENT_NAME);
            submitButton.setText(Message.getString("submit_edit_button")); //("Подтвердить");
            submitButton.setActionCommand(OK_COMMAND);
            submitButton.addActionListener(this);
        }
        return submitButton;
    }

    private JPanel getFieldPanel() {
        if (fieldPanel == null) {
            fieldPanel = new JPanel();
            fieldPanel.setLayout(new GridLayout(3, 2));
            addLabelField(fieldPanel, Message.getString("name_label")/*"Имя"*/, getFirstNameField());
            addLabelField(fieldPanel, Message.getString("surname_label")/*"Фамилия"*/, getLastNameField());
            addLabelField(fieldPanel, Message.getString("date.of.birth_label")/*"Дата Рождения"*/, getDateOfBirthField());
        }
        return fieldPanel;
    }

    private JTextField getLastNameField() {
        if (lastNameField == null) {
            lastNameField = new JTextField();
            lastNameField.setName(LAST_NAME_FIELD_COMPONENT_NAME);
        }
        return lastNameField;
    }

    private JTextField getDateOfBirthField() {
        if (dateOfBirthField == null) {
            dateOfBirthField = new JTextField();
            dateOfBirthField.setName(DATE_OF_BIRTH_FIELD_COMPONENT_NAME);
        }
        return dateOfBirthField;
    }

    private JTextField getFirstNameField() {
        if (firstNameField == null) {
            firstNameField = new JTextField();
            firstNameField.setName(FIRST_NAME_FIELD_COMPONENT_NAME);
        }
        return firstNameField;
    }

    private void addLabelField(JPanel panel, String name, JTextField field) {
        JLabel label = new JLabel(name);
        label.setLabelFor(field);
        panel.add(label);
        panel.add(field);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
