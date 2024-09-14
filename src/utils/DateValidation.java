package utils;

import java.time.LocalDate;

public class DateValidation {
    public static boolean isDateValid(LocalDate startDate , LocalDate endDate){
        return startDate.isAfter(endDate);
    }
}
