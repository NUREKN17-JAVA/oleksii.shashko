package ua.nure.kn.shahsko.usermanagment.domain;

import junit.framework.TestCase;

import java.util.Calendar;

public class UserTest extends TestCase {

    public static final int DAY_OF_BIRTH = 13;

    public static final int MONTH_OF_BIRTH = Calendar.MAY;
    public static final int YEAR_OF_BIRTH = 2000;
    public static final int ETHALON_AGE = 19;

    public static final int MONTH_OF_BIRTH_1 = Calendar.SEPTEMBER;
    public static final int YEAR_OF_BIRTH_1 = 1999;
    public static final int ETHALON_AGE_1 = 20;

    private User user;

    // Test to get user full name
    public void testGetFullName() {
        user.setFirstName("John");
        user.setLastName("Doe");
        assertEquals("Doe, John", user.getFullName());
    }

    // Test to get age
    public void testGetAge() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(YEAR_OF_BIRTH, MONTH_OF_BIRTH, DAY_OF_BIRTH);
        user.setDateOfBirth(calendar.getTime());
        assertEquals(ETHALON_AGE, user.getAge());
    }

    // Test to get age, if birthday is later than now
    public void testGetAge1() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(YEAR_OF_BIRTH_1, MONTH_OF_BIRTH_1, DAY_OF_BIRTH);
        user.setDateOfBirth(calendar.getTime());
        assertEquals(ETHALON_AGE_1, user.getAge());
    }

    protected void setUp() throws Exception{
        super.setUp();
        user = new User();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
