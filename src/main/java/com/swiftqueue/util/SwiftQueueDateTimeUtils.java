package com.swiftqueue.util;

import com.github.eloyzone.jalalicalendar.DateConverter;

import java.time.LocalDate;

public class SwiftQueueDateTimeUtils {

    private static final DateConverter DATE_CONVERTER = new DateConverter();

    private SwiftQueueDateTimeUtils() {}

    public static LocalDate fromPersianDateAsString(String persianDate) {
        String[] splitDate = persianDate.split("-");
        return new DateConverter().jalaliToGregorian(Integer.parseInt(splitDate[0]), Integer.parseInt(splitDate[1]),
                Integer.parseInt(splitDate[2]));
    }

    public static String fromGregorianDateAsLocalDateTime(LocalDate date) {
        int year = date.getYear();
        int month = date.getMonth().getValue();
        int day = date.getDayOfMonth();
        return DATE_CONVERTER.gregorianToJalali(year, month, day).toString();
    }
}
