package UnitTests;

import JFree.DiscountCalculator;
import JFree.IDiscountCalculator;
import org.jfree.data.time.Week;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class DiscountCalculatorTest {

    @Test
    public void testIsTheSpecialWeekWhenFalse() {
        // Arrange
        Calendar calendar = Calendar.getInstance();
        calendar.set(2025, Calendar.MARCH, 22);  // March 22, 2025
        Date date = calendar.getTime();
        Week week = new Week(date);
        IDiscountCalculator discountCalculator = new DiscountCalculator(week);

        // Act
        boolean result = discountCalculator.isTheSpecialWeek();

        // Assert
        assertFalse(result);
    }

    // Test missing cases ( JUNE, 23 is a date in week 26 )
    @Test
    public void testIsTheSpecialWeekWhenTrue() {
        // Arrange
        Calendar calendar = Calendar.getInstance();
        calendar.set(2025, Calendar.JUNE, 23);  // June 23, 2025
        Date date = calendar.getTime();
        Week week = new Week(date);
        IDiscountCalculator discountCalculator = new DiscountCalculator(week);

        // Act
        boolean result = discountCalculator.isTheSpecialWeek();

        // Assert
        assertTrue(result);
    }
    @Test
    public void testGetDiscountPercentageWhenEven() {
        // Arrange
        Calendar calendar = Calendar.getInstance();
        calendar.set(2025, Calendar.JUNE, 23);  // week 26
        Date date = calendar.getTime();
        Week week = new Week(date);
        IDiscountCalculator discountCalculator = new DiscountCalculator(week);

        // Act
        int result = discountCalculator.getDiscountPercentage();

        // Assert
        assertEquals(7, result);
    }
    @Test
    public void testGetDiscountPercentageWhenOdd() {
        // Arrange
        Calendar calendar = Calendar.getInstance();
        calendar.set(2025, Calendar.JUNE, 18);  // week 25
        Date date = calendar.getTime();
        Week week = new Week(date);
        IDiscountCalculator discountCalculator = new DiscountCalculator(week);

        // Act
        int result = discountCalculator.getDiscountPercentage();

        // Assert
        assertEquals(5, result);
    }
}
