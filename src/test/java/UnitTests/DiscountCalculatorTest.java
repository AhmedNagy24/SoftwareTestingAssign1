package UnitTests;

import org.jfree.data.time.Week;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
public class DiscountCalculatorTest {

    @Test
    public void testIsTheSpecialWeekWhenFalse() throws Exception {
        // Arrange
        Calendar calendar = Calendar.getInstance();
        calendar.set(2025, Calendar.MARCH, 22);  // March 22, 2025
        Date date = calendar.getTime();
        Week week = new Week(date);

        // Act


        // Assert

    }

    // Test missing cases ( JUNE, 23 is a date in week 26 )

}
