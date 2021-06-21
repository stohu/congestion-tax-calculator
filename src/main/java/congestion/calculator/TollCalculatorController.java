package congestion.calculator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
public class TollCalculatorController {

    @Autowired
    private CongestionTaxCalculator calculator;

    @GetMapping("/total")
    public Integer calculateTotal(
            @RequestParam("type") String vehicleType,
            @RequestParam("passtimes") String passTimestamps
    ) {
        var passes = Arrays.stream(passTimestamps.split(";"))
                .sorted()
                .map(LocalDateTime::parse).collect(Collectors.toList());
        return calculator.getTax(vehicleType, passes);
    }

    @GetMapping("/toll")
    public Integer getToll(
            @RequestParam("type") String vehicleType,
            @RequestParam("passtime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime passTime
    ) {
        return calculator.getTollFee(passTime, vehicleType);
    }

}
