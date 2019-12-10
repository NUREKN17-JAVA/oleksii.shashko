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
}
