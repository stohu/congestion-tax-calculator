package congestion.calculator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

@Service
public class CongestionTaxCalculator {

    @Autowired
    private TollRules rules;

    /**
     * calculate the total tax for a vehicle under a day at toll stations
     * @param vehicleType - type of the vehicle
     * @param passTimes - timestamps of all passes under a day by the vehicle
     *              The timestamps are assumed to be in ascending order.
     *              Passes before and after the series are not considered.
     * @return total toll fee for the passes
     */
    public int getTax(String vehicleType, List<LocalDateTime> passTimes)
    {
        LocalDateTime intervalStart = passTimes.get(0);
        int maxFee = getTollFee(intervalStart, vehicleType);
        int totalFee = 0;

        for (int i = 1; i < passTimes.size() && totalFee < 60 ; i++) {
            LocalDateTime passTime = passTimes.get(i);
            int nextFee = getTollFee(passTime, vehicleType);

            long diffInMillies = passTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() -
                    intervalStart.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            long minutes = diffInMillies/1000/60;

            if (minutes <= 60)
            {
                if (nextFee > maxFee) maxFee = nextFee;
            }
            else
            {
                totalFee += maxFee;
                intervalStart = passTime;
                maxFee = getTollFee(intervalStart, vehicleType);
            }
        }                
      
        if (totalFee > 60) totalFee = 60;
        return totalFee;
    }

    private boolean isTollFreeVehicle(String vehicleType) {
        if (vehicleType == null) return false;
        return rules.getTollFreeVehicles().contains(vehicleType);
    }

    public int getTollFee(LocalDateTime date, String vehicleType)
    {
        if (isTollFreeDate(date) || isTollFreeVehicle(vehicleType)) return 0;

        String hourMinute = String.format("%02d:%02d", date.getHour(), date.getMinute());
        return rules.getTollTable().entrySet().stream()
                .filter(entry -> entry.getKey().compareTo(hourMinute) >= 0)
                .min(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue).orElse(0);
    }

    private boolean isTollFreeDate(LocalDateTime date)
    {
        int year = date.getYear();
        int month = date.getMonthValue();
        int dayOfMonth = date.getDayOfMonth();
        String monthDate = String.format("%02d-%02d", month, dayOfMonth);
        DayOfWeek day = date.getDayOfWeek();

        if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) return true;

        if (year == 2013)
        {
            if (rules.getTollFreeMonths().contains(month)) return true;
            if (rules.getTollFreeDays().contains(monthDate)) return true;
        }
        return false;
    }
}
