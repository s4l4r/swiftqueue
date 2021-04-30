package com.swiftqueue.util;

import com.github.eloyzone.jalalicalendar.DateConverter;

import java.time.LocalDate;

public class SwiftQueueDateTimeUtils {

    private static final DateConverter DATE_CONVERTER = new DateConverter();

    private SwiftQueueDateTimeUtils() {}

    /**
     * Converts a persian date to LocalDate object according to current locale
     * @param persianDate The date which needs to follow pattern yyyy-mm-dd
     * @return A {@link LocalDate} of the passed date configured with current locale
     */
    public static LocalDate persianToGregorian(String persianDate) {
        String[] splitDate = persianDate.split("-");
        return new DateConverter().jalaliToGregorian(Integer.parseInt(splitDate[0]), Integer.parseInt(splitDate[1]),
                Integer.parseInt(splitDate[2]));
    }

    /**
     * Converts a gregorian date to persian date
     * @param date The date to be converted
     * @return A {@link String} containing persian date
     */
    public static String gregorianToPersian(LocalDate date) {
        int year = date.getYear();
        int month = date.getMonth().getValue();
        int day = date.getDayOfMonth();
        return DATE_CONVERTER.gregorianToJalali(year, month, day).toString();
    }
}
