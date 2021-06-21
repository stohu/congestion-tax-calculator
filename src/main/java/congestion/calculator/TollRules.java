package congestion.calculator;

import java.util.List;
import java.util.Map;

public interface TollRules {
    List<String> getTollFreeVehicles();
    Map<String, Integer> getTollTable();
    List<Integer> getTollFreeMonths();
    List<String> getTollFreeDays();
    void setTollFreeVehicles(List<String> in);
    void setTollTable(Map<String, Integer> in);
    void setTollFreeMonths(List<Integer> in);
    void setTollFreeDays(List<String> in);
}
