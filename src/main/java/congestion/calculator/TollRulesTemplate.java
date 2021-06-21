package congestion.calculator;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class TollRulesTemplate implements TollRules {
    private List<String> tollFreeVehicles;
    private Map<String, Integer> tollTable;
    private List<String> tollFreeDays;
    private List<Integer> tollFreeMonths;
}
