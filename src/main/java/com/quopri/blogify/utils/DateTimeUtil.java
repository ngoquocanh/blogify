package com.quopri.blogify.utils;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    //Mar 25, 1993
    private static final String MONTH_DAY_YEAR      = "MMM dd, yyyy";
    private static final String MONTH_DAY_YEAR_TIME = "MMM dd, yyyy HH:mm";

    /**
     * Use in thymeleaf: th:text="${T(com.quopri.blogify.utils.DateTimeUtil).convertZonedDateTimeToStringDate(article.articleModified)}"
     * Other way is: th:text="${#temporals.format(post.articleDate, 'dd/MMM/yyyy HH:mm')}"
     * @param zonedDateTime
     * @return
     */
    public static String convertZonedDateTimeToStringDate(ZonedDateTime zonedDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(MONTH_DAY_YEAR);
        String zonedDateTimeConverted = zonedDateTime.format(formatter);
        return zonedDateTimeConverted;
    }

    public static String convertZonedDateTimeToStringTime(ZonedDateTime zonedDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(MONTH_DAY_YEAR_TIME);
        String zonedDateTimeConverted = zonedDateTime.format(formatter);
        return zonedDateTimeConverted;
    }
}
