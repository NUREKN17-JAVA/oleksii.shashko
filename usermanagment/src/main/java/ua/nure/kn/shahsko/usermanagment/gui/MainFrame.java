package ua.nure.kn.shahsko.usermanagment.gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private JPanel contentPanel;
    private BrowsePanel browsePanel;
    private AddPanel addPanel;

    MainFrame() {
        initialize();
    }

    private void initialize() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(WIDTH, HEIGHT);
        this.setTitle("Управление пользователями"); // localize
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

    private Component getBrowsePanel() {
        if(browsePanel == null) {
            browsePanel = new BrowsePanel(this);
        }

        return browsePanel;
    }

}
