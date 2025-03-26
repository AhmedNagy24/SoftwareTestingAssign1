package UnitTests;

import org.jfree.data.time.Week;
import org.jfree.data.time.Year;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static org.junit.Assert.*;

public class WeekTest {
    @Test
    public void testWeekDefaultCtor() {
        // Arrange
        Calendar calendar = Calendar.getInstance();
        int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        int currentYear = calendar.get(Calendar.YEAR);

        // Act
        Week week = new Week();

        // Assert
        assertEquals(currentYear, week.getYear().getYear());
        assertEquals(currentWeek, week.getWeek());
    }
    @Test
    public void testCtorWithWeekAndYearInt() {
        // Arrange
        int inputWeek = 10;
        int inputYear = 2025;

        // Act
        Week week = new Week(inputWeek, inputYear);

        // Assert
        assertEquals("Week number should match", inputWeek, week.getWeek());
        assertEquals("Year should match", inputYear, week.getYear().getYear());
    }
    @Test
    public void testCtorWithWeekAndYearObject() {
        // Arrange
        int inputWeek = 15;
        Year year2020 = new Year(2025);

        // Act
        Week week = new Week(inputWeek, year2020);

        // Assert
        assertEquals("Week number should match", inputWeek, week.getWeek());
        assertEquals("Year should match", 2025, week.getYear().getYear());
    }
    @Test
    public void testCtorWithDate() {
        // Arrange
        Calendar cal = Calendar.getInstance();
        cal.set(2025, Calendar.MARCH, 18, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date date = cal.getTime();
        int expectedWeek = cal.get(Calendar.WEEK_OF_YEAR);
        int expectedYear = cal.get(Calendar.YEAR);

        // Act
        Week week = new Week(date, TimeZone.getDefault(), Locale.getDefault());

        // Assert
        assertEquals("Week from Date: week number mismatch", expectedWeek, week.getWeek());
        assertEquals("Week from Date: year mismatch", expectedYear, week.getYear().getYear());
    }
    @Test
    public void testCtorWithDateAndCalendar() {
        // Arrange
        Calendar cal = Calendar.getInstance();
        cal.set(2025, Calendar.MARCH, 18, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date date = cal.getTime();
        int expectedWeek = cal.get(Calendar.WEEK_OF_YEAR);
        int expectedYear = cal.get(Calendar.YEAR);

        // Act
        Week week = new Week(date, cal);

        // Assert
        assertEquals("Week from Date and Calendar: week number mismatch", expectedWeek, week.getWeek());
        assertEquals("Week from Date and Calendar: year mismatch", expectedYear, week.getYear().getYear());
    }
    @Test
    public void testFirstMillisecondOfWeek() {
        // Arrange
        Calendar cal = Calendar.getInstance();
        cal.set(2025, Calendar.MARCH, 16, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date date = cal.getTime();
        long expectedMillis = cal.getTimeInMillis();

        // Act
        Week week = new Week(date, TimeZone.getDefault(), Locale.getDefault());
        long actualMillis = week.getFirstMillisecond();

        // Assert
        assertEquals("First millisecond of week mismatch", expectedMillis, actualMillis);
    }
    @Test
    public void testLastMillisecondOfWeek() {
        // Arrange
        Calendar cal = Calendar.getInstance();
        cal.set(2025, Calendar.MARCH, 22, 23, 59, 59);
        cal.set(Calendar.MILLISECOND, 999);
        Date date = cal.getTime();
        long expectedMillis = cal.getTimeInMillis();

        // Act
        Week week = new Week(date, TimeZone.getDefault(), Locale.getDefault());
        long actualMillis = week.getLastMillisecond();

        // Assert
        assertEquals("Last millisecond of week mismatch", expectedMillis, actualMillis);
    }
    @Test
    public void testPeg() {
        // Arrange
        Week week = new Week(12, 2025);
        long initialFirstMillis = week.getFirstMillisecond();
        long initialLastMillis = week.getLastMillisecond();
        Calendar cal = Calendar.getInstance();
        cal.set(2025, Calendar.MARCH, 17, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.setTimeZone(TimeZone.getTimeZone("GMT"));
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        long expectedFirstMillis = cal.getTimeInMillis();
        cal.set(2025, Calendar.MARCH, 23, 23, 59, 59);
        cal.set(Calendar.MILLISECOND, 999);
        long expectedLastMillis = cal.getTimeInMillis();

        // Act
        week.peg(cal);

        // Assert
        assertNotEquals("After peg, first millisecond should change", initialFirstMillis, week.getFirstMillisecond());
        assertNotEquals("After peg, last millisecond should change", initialLastMillis, week.getLastMillisecond());
        assertEquals("After peg, first millisecond should match", expectedFirstMillis, week.getFirstMillisecond());
        assertEquals("After peg, last millisecond should match", expectedLastMillis, week.getLastMillisecond());
    }
    @Test
    public void testNextAndPrevious() {
        // Arrange
        Week week = new Week(10, 2020);

        // Act
        Week nextWeek = (Week) week.next();
        Week prevWeek = (Week) week.previous();

        // Assert
        assertNotNull("Next week is null", nextWeek);
        assertNotNull("Previous week is null", prevWeek);
        assertEquals("Next week should be week 11", 11, nextWeek.getWeek());
        assertEquals("Previous week should be week 9", 9, prevWeek.getWeek());

        // Arrange (boundary case)
        Week lastWeek = new Week(53, 2020);

        // Act
        Week afterLast = (Week) lastWeek.next();

        // Assert
        assertNotNull("Week after last is null", afterLast);
        assertEquals("After last week should be week 1", 1, afterLast.getWeek());
        assertEquals("After last week should be in next year", 2021, afterLast.getYear().getYear());
    }
    @Test
    public void testSerialIndex() {
        // Arrange
        Week week = new Week(12, 2025);

        // Act
        long serialIndex = week.getSerialIndex();

        // Assert
        assertEquals("Serial index should be 2020*53 + 10", 2025 * 53 + 12, serialIndex);
    }
    @Test
    public void testFirstMillisecondWithCal(){
        // Arrange
        Calendar cal = Calendar.getInstance();
        cal.set(2025, Calendar.MARCH, 16, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        long expectedMillis = cal.getTimeInMillis();

        // Act
        Week week = new Week(12, 2025);
        long actualMillis = week.getFirstMillisecond(cal);

        // Assert
        assertEquals("First millisecond of week mismatch", expectedMillis, actualMillis);
    }
    @Test
    public void testLastMillisecondWithCal(){
        // Arrange
        Calendar cal = Calendar.getInstance();
        cal.set(2025, Calendar.MARCH, 22, 23, 59, 59);
        cal.set(Calendar.MILLISECOND, 999);
        long expectedMillis = cal.getTimeInMillis();

        // Act
        Week week = new Week(12, 2025);
        long actualMillis = week.getLastMillisecond(cal);

        // Assert
        assertEquals("Last millisecond of week mismatch", expectedMillis, actualMillis);
    }
    @Test
    public void testToString() {
        // Arrange
        int weekNumber = 12;
        int year = 2025;
        Week week = new Week(weekNumber, year);

        // Act
        String str = week.toString();

        // Assert
        assertEquals("Week string mismatch", STR."Week \{weekNumber}, \{year}", str);
    }
    @Test
    public void testEquals() {
        // Arrange
        Week week1 = new Week(12, 2025);
        Week week2 = new Week(12, 2025);
        Week week3 = new Week(13, 2025);
        Week week4 = new Week(12, 2026);

        // Assert
        assertEquals("Week should be equal to itself", week1, week1);
        assertEquals("Weeks with same week and year should be equal", week1, week2);
        assertNotEquals("Weeks with different week should not be equal", week1, week3);
        assertNotEquals("Weeks with different year should not be equal", week1, week4);
    }
    @Test
    public void testHashCode() {
        // Arrange
        Week week1 = new Week(12, 2025);
        Week week2 = new Week(12, 2025);
        Week week3 = new Week(13, 2025);
        Week week4 = new Week(12, 2026);

        // Assert
        assertEquals("Week should have same hash code as itself", week1.hashCode(), week1.hashCode());
        assertEquals("Weeks with same week and year should have same hash code", week1.hashCode(), week2.hashCode());
        assertNotEquals("Weeks with different week should not have same hash code", week1.hashCode(), week3.hashCode());
        assertNotEquals("Weeks with different year should not have same hash code", week1.hashCode(), week4.hashCode());
    }
    @Test
    public void testCompareTo() {
        // Arrange
        Week week1 = new Week(12, 2025);
        Week week2 = new Week(12, 2025);
        Week week3 = new Week(13, 2025);
        Week week4 = new Week(12, 2026);

        // Assert
        assertEquals("Week should be equal to itself", 0, week1.compareTo(week1));
        assertEquals("Weeks with same week and year should be equal", 0, week1.compareTo(week2));
        assertTrue("Weeks with different week should not be equal", week1.compareTo(week3) < 0);
        assertTrue("Weeks with different year should not be equal", week1.compareTo(week4) < 0);
    }
    @Test
    public void testParseWeek(){
        // Arrange
        int weekNumber = 12;
        int year = 2025;
        String yearWeek = STR."\{year}-W\{weekNumber}";
        String weekYear = STR."W\{weekNumber},\{year}";


        // Act
        Week week1 = Week.parseWeek(yearWeek);
        Week week2 = Week.parseWeek(weekYear);

        // Assert
        assertEquals("Week number should match", weekNumber, week1.getWeek());
        assertEquals("Year should match", year, week1.getYear().getYear());
        assertEquals("Week number should match", weekNumber, week2.getWeek());
        assertEquals("Year should match", year, week2.getYear().getYear());
    }
}
