package cn.tcc.foundation.core.util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author shawn.xiao
 * @date 2017/10/25
 */
public class DateUtil {


    /**
     * date增加day
     *
     * @param date
     * @param day
     * @return
     */
    public static Date addDay(Date date, int day) {
        long time = date.getTime();
        time += (day * 24 * 60 * 60 * 1000L);
        return new Date(time);
    }

    public static long dayOfDays(Date day1, Date day2) {
        return (day1.getTime() - day2.getTime()) / (24 * 60 * 60 * 1000L);
    }

    public static long seconds(LocalDateTime startTime, LocalDateTime endTime) {
        return Duration.between(startTime, endTime).getSeconds();
    }

    public static List<Date> buildContinuouslyDays(Date checkInDate, Date checkOutDate) {
        long days = dayOfDays(checkOutDate, checkInDate);
        if (days <= 0) {
            return new ArrayList<>(0);
        }
        List<Date> dates = new ArrayList<>((int) days);
        for (int day = 0; day < days; day++) {
            dates.add(DateUtil.addDay(checkInDate, day));
        }
        return dates;
    }
}
