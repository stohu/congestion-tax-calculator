package congestion.calculator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class CongestionTaxCalculatorTest {

    final List<LocalDateTime> test1 = Arrays.asList("2013-01-14T21:00:00").stream().map(LocalDateTime::parse).collect(Collectors.toList());
    final List<LocalDateTime> test2 = Arrays.asList("2013-01-01T14:00:00").stream().map(LocalDateTime::parse).collect(Collectors.toList());
    final List<LocalDateTime> test3 = Arrays.asList("2013-02-09T06:23:27", "2013-02-09T15:27:00").stream().map(LocalDateTime::parse).collect(Collectors.toList());
    final List<LocalDateTime> test4 = Arrays.asList(
            "2013-02-08T06:20:27",
            "2013-02-08T06:27:00",
            "2013-02-08T14:35:00",
            "2013-02-08T15:29:00",
            "2013-02-08T15:47:00",
            "2013-02-08T16:01:00",
            "2013-02-08T16:48:00",
            "2013-02-08T17:49:00",
            "2013-02-08T18:29:00",
            "2013-02-08T18:35:00"
    ).stream().map(LocalDateTime::parse).collect(Collectors.toList());
    final List<LocalDateTime> test5 = Arrays.asList("2013-03-26T14:25:00").stream().map(LocalDateTime::parse).collect(Collectors.toList());
    final List<LocalDateTime> test6 = Arrays.asList("2013-05-18T14:07:27").stream().map(LocalDateTime::parse).collect(Collectors.toList());

    static CongestionTaxCalculator calc = new CongestionTaxCalculator();

    @BeforeAll
    static void setup() {
        ReflectionTestUtils.setField(calc, "rules", new GothenburgRules());
    }

    @Test
    void getTaxOnEvening() {
        assertEquals(0, calc.getTax("Car", test1));
    }

    @Test
    void getTaxOnHoliday() {
        assertEquals(0, calc.getTax("Bus", test2));
    }

    @Test
    void getTaxOnWeekend() {
        assertEquals(0, calc.getTax("Bus", test3));
    }

    @Test
    void getTaxOnMultiplePasses() {
        assertEquals(57, calc.getTax("Car", test4));
    }

    @Test
    void getTaxOnMultiplePassesForTollfreeVehicle() {
        assertEquals(0, calc.getTax("Bus", test4));
    }

    @Test
    void getTollFee() {
        assertEquals(8, calc.getTollFee(test5.get(0), "SUV"));
    }

    @Test
    void getTollFeeForTollFreeVehicle() {
        assertEquals(0, calc.getTollFee(test5.get(0), "Emergency"));
    }

    @Test
    void getTollFeeForWeekend() {
        assertEquals(0, calc.getTollFee(test6.get(0), "Car"));
    }

    @Test
    void getTollFeeForHoliday() {
        assertEquals(0, calc.getTollFee(test2.get(0), "Car"));
    }

    @Test
    void getTollFee2() {
        assertEquals(13, calc.getTollFee(LocalDateTime.parse("2013-02-07T15:27:00"), "Car"));
    }
}