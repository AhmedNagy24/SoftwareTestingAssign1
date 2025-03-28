package UnitTests;

import JFree.DiscountManager;
import JFree.IDiscountCalculator;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

import static org.junit.Assert.*;

public class DiscountManagerTest {

    @Test
    public void testCalculatePriceWhenDiscountsSeasonIsFalse() {
        // Arrange
        boolean isDiscountsSeason = false;
        double originalPrice = 100.0;
        double expectedPrice = 100.0;

        Mockery mockingContext = new Mockery();
        IDiscountCalculator mockedDependency = mockingContext.mock(IDiscountCalculator.class);
        DiscountManager discountManager = new DiscountManager(isDiscountsSeason, mockedDependency);
        // Act
        double actualPrice = discountManager.calculatePriceAfterDiscount(originalPrice);

        // Assert
        mockingContext.assertIsSatisfied();
        assertEquals(expectedPrice, actualPrice, 0.0);
    }

    @Test
    public void testCalculatePriceWhenDiscountsSeasonIsTrueAndSpecialWeekIsTrue() {
        // Arrange
        boolean isDiscountsSeason = true;
        double originalPrice = 100.0;
        double expectedPrice = originalPrice * .8;

        Mockery mockingContext = new Mockery();
        IDiscountCalculator mockedDependency = mockingContext.mock(IDiscountCalculator.class);

        mockingContext.checking(new Expectations() {{
            oneOf(mockedDependency).isTheSpecialWeek();
            will(returnValue(true));
        }});
        DiscountManager discountManager = new DiscountManager(isDiscountsSeason, mockedDependency);
        // Act
        double actualPrice = discountManager.calculatePriceAfterDiscount(originalPrice);

        // Assert
        mockingContext.assertIsSatisfied();
        assertEquals(expectedPrice, actualPrice, 0.0);
    }
    @Test
    public void testCalculatePriceWhenDiscountsSeasonIsTrueAndSpecialWeekIsFalse() {
        // Arrange
        boolean isDiscountsSeason = true;
        double originalPrice = 100.0;
        int discountPercentage = 7;
        double expectedPrice = originalPrice - (originalPrice * discountPercentage / 100.0);

        Mockery mockingContext = new Mockery();
        IDiscountCalculator mockedDependency = mockingContext.mock(IDiscountCalculator.class);

        mockingContext.checking(new Expectations() {{
            oneOf(mockedDependency).isTheSpecialWeek();
            will(returnValue(false));
            oneOf(mockedDependency).getDiscountPercentage();
            will(returnValue(discountPercentage));
        }});
        DiscountManager discountManager = new DiscountManager(isDiscountsSeason, mockedDependency);
        // Act
        double actualPrice = discountManager.calculatePriceAfterDiscount(originalPrice);

        // Assert
        mockingContext.assertIsSatisfied();
        assertEquals(expectedPrice, actualPrice, 0.0);
    }
}
