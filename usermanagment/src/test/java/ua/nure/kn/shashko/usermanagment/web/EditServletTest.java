package ua.nure.kn.shashko.usermanagment.web;

import ua.nure.kn.shashko.usermanagment.domain.User;

import java.util.Date;

import static junit.framework.TestCase.assertNotNull;

public class EditServletTest extends MockServletTestCase {

    @Override
    public void setUp() throws Exception {
        super.setUp();
        createServlet(EditServlet.class);
    }

    public void testEdit() {
        User user = new User(1000L, "John", "Doe", new Date());
        getMockUserDao().expect("update", user);

        addRequestParameter("id", "1000");
        addRequestParameter("firstName", "John");
        addRequestParameter("lastName", "Doe");
        addRequestParameter("date", String.valueOf(new Date()));
        addRequestParameter("okButton", "Ok");
        doPost();
    }
}
