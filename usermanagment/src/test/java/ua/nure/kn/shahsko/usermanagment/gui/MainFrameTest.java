package ua.nure.kn.shahsko.usermanagment.gui;

import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.TestHelper;
import junit.extensions.jfcunit.finder.NamedComponentFinder;

import java.awt.*;

public class MainFrameTest extends JFCTestCase {
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

    private Component find(Class<?> componentClass, String name) {
        NamedComponentFinder finder;
        finder = new NamedComponentFinder(componentClass, name);
        finder.setWait(0);

        Component component = finder.find(mainFrame, 0);
        assertNotNull("Could not find component '" + name + "'", component);

        return component;
    }
}
