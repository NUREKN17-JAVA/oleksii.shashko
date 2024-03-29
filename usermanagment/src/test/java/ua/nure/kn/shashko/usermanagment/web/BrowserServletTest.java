package ua.nure.kn.shashko.usermanagment.web;

import ua.nure.kn.shashko.usermanagment.domain.User;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertSame;

public class BrowserServletTest extends MockServletTestCase {

    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";

    @Override
    public void setUp() throws Exception {
        super.setUp();
        createServlet(BrowseServlet.class);
    }

    public void testBrowse() {
        User user = new User();
        user.setFirstName(FIRST_NAME);
        user.setLastName(LAST_NAME);
        user.setDateOfBirth(new Date());
        user.setId(1000L);

        List<User> users = Collections.singletonList(user);
        doGet();
        Collection<User> sessionAttr = (Collection<User>) getWebMockObjectFactory().getMockSession().getAttribute("users");

        assertNotNull("Could not find list of users in session", sessionAttr);
        assertSame(sessionAttr, users);
    }

    public void testEdit() {
        User user = new User(1000L, FIRST_NAME, LAST_NAME, new Date());

        getMockUserDao().expectAndReturn("find", 1000L, user);
        addRequestParameter("editButton", "edit");
        addRequestParameter("id", "1000");
        doPost();
        User userInSession = (User) getWebMockObjectFactory().getMockSession().getAttribute("user");
        assertNotNull("Could not find user", user);
        assertSame(user, userInSession);
    }
}
