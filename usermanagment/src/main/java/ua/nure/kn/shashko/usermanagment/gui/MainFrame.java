package ua.nure.kn.shashko.usermanagment.gui;

import ua.nure.kn.shashko.usermanagment.db.Dao;
import ua.nure.kn.shashko.usermanagment.db.DaoFactory;
import ua.nure.kn.shashko.usermanagment.domain.User;
import ua.nure.kn.shashko.usermanagment.util.Message;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private JPanel contentPanel;

    private BrowsePanel browsePanel;
    private AddPanel addPanel;
    private EditPanel editPanel;

    private Dao<User> userDao;

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.setVisible(true);
    }

    MainFrame() {
        super();
        try {
            userDao = DaoFactory.getInstance().getUserDao();
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
        initialize();
    }

    public Dao<User> getUserDao() {
        return userDao;
    }

    private void initialize() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(WIDTH, HEIGHT);
        this.setTitle(Message.getString("user_management")); //("Управление пользователями");
        this.setContentPane(getContentPanel());
    }

    public void showAddPanel() {
        showPanel(getAddPanel());
    }

    private void showPanel(JPanel panel) {
        getContentPane().add(panel, BorderLayout.CENTER);
        panel.setVisible(true);
        panel.repaint();
    }

    private AddPanel getAddPanel() {
        if(addPanel == null) {
            addPanel = new AddPanel(this);
        }
        return addPanel;
    }

    private JPanel getContentPanel() {
        if(contentPanel == null) {
            contentPanel = new JPanel();
            contentPanel.setLayout(new BorderLayout());
            contentPanel.add(getBrowsePanel(), BorderLayout.CENTER);
        }
        return contentPanel;
    }

    private BrowsePanel getBrowsePanel() {
        if(browsePanel == null) {
            browsePanel = new BrowsePanel(this);
        }
        ((BrowsePanel) browsePanel).initTable();

        return browsePanel;
    }

    public void showBrowsePanel() {
        showPanel(getBrowsePanel());
    }

    public void showEditPanel() {
        showPanel(getEditPanel());
    }

    private JPanel getEditPanel() {
        if (editPanel == null) {
            editPanel = new EditPanel(this);
        }
        return editPanel;
    }

    public User getSelectedUser() {
        return ((BrowsePanel) browsePanel).getSelectedUser();
    }
}
