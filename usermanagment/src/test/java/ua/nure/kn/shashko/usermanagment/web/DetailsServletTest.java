package ua.nure.kn.shashko.usermanagment.web;

import ua.nure.kn.shashko.usermanagment.domain.User;

import static junit.framework.Assert.assertNull;

public class DetailsServletTest extends MockServletTestCase {
    @Override
    public void setUp() throws Exception {
        super.setUp();
        createServlet(DetailsServlet.class);
    }

    public void testDetails() {
        addRequestParameter("cancelButton", "cancel");
        User user = (User) getWebMockObjectFactory().getMockSession().getAttribute("user");
        assertNull("User is in session", user);
    }
}