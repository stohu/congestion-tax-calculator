package congestion.calculator;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class GothenburgRules implements TollRules {
    private List<String> tollFreeVehicles = Arrays.asList(
            "Motorcycle",
            "Bus",
            "Emergency",
            "Diplomat",
            "Foreign",
            "Military");
    private Map<String, Integer> tollTable = new HashMap<>()
    {{
        put("05:59", 0);
        put("06:29", 8);
        put("06:59", 13);
        put("07:59", 18);
        put("08:29", 13);
        put("14:59", 8);
        put("15:29", 13);
        put("16:59", 18);
        put("17:59", 13);
        put("18:29", 8);
    }};
    private List<String> tollFreeDays = Arrays.asList(
            "01-01",
            "03-28",
            "03-29",
            "04-01",
            "04-30",
            "05-01",
            "05-08",
            "05-09",
            "06-05",
            "06-06",
            "06-21",
            "11-01",
            "12-24",
            "12-25",
            "12-26",
            "12-31"
    );
    private List<Integer> tollFreeMonths = Collections.singletonList(7);
}
