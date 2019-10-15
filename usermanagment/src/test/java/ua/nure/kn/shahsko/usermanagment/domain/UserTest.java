package ua.nure.kn.shahsko.usermanagment.domain;

import junit.framework.TestCase;

import java.util.Calendar;
import java.util.Date;

public class UserTest extends TestCase {

    private static final int DAY_OF_BIRTH = 13;
    private static final int DAY_OF_BIRTH_1 = 15;
    private static final int DAY_OF_BIRTH_2 = 14;
    private static final int DAY_OF_BIRTH_3 = 16;

    private static final int MONTH_OF_BIRTH = Calendar.MAY;
    private static final int YEAR_OF_BIRTH = 2000;
    private static final int ETHALON_AGE = 19;

    private static final int MONTH_OF_BIRTH_1 = Calendar.SEPTEMBER;
    private static final int YEAR_OF_BIRTH_1 = 1999;
    private static final int ETHALON_AGE_1 = 20;


    private static final int MONTH_OF_BIRTH_2 = Calendar.OCTOBER;
    private static final int ETHALON_AGE_3 = 18;

    // Works on 03 October 2019

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

    //Test to get age, if birthday is today
    public void testGetAgeT() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(YEAR_OF_BIRTH, MONTH_OF_BIRTH_2, DAY_OF_BIRTH_1);
        user.setDateOfBirth(calendar.getTime());
        assertEquals(ETHALON_AGE, user.getAge());
    }

    //Test to get age, if birthday in this month but earlier then now
    public void testGetAgeDayBefore() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(YEAR_OF_BIRTH, MONTH_OF_BIRTH_2, DAY_OF_BIRTH_2);
        user.setDateOfBirth(calendar.getTime());
        assertEquals(ETHALON_AGE_3, user.getAge());
    }

    // Test to get age, if birthday in this month and later
    public void testGetAgeDayAfter() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(YEAR_OF_BIRTH, MONTH_OF_BIRTH_2, DAY_OF_BIRTH_3);
        user.setDateOfBirth(calendar.getTime());
        assertEquals(ETHALON_AGE, user.getAge());
    }

    protected void setUp() throws Exception{
        super.setUp();
        user = new User();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
