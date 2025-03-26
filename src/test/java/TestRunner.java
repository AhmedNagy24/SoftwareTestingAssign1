import UnitTests.DiscountCalculatorTest;
import UnitTests.DiscountManagerTest;
import UnitTests.WeekTest;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(WeekTest.class, DiscountCalculatorTest.class, DiscountManagerTest.class);

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        if (result.wasSuccessful()) {
            System.out.println(STR."\{result.getRunCount()} tests ran successfully...");
        } else {
            System.out.println(STR."\{result.getFailureCount()} tests failed...");
        }
    }
}
