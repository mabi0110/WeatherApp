package io;

import model.DailyWeather;
import model.HourlyWeather;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConsolePrinter {

    private static final Double KELWIN_TO_CEL = 273.15;

    public void printDailyWeather(DailyWeather dailyWeather) {
        DailyWeather.Daily day;
        String formattedDate;
        double avgTemp;

        for (int i = 0; i < dailyWeather.getDaily().size(); i++) {
            day = dailyWeather.getDaily().get(i);
            formattedDate = getFormattedDate(day);
            avgTemp = getAvgTemp(day);

            System.out.printf("%10s, %6.2f, %6.2f, %6.2f, %6.2f\n",
                    formattedDate, (day.getTemp().getMin() - KELWIN_TO_CEL),
                    (day.getTemp().getMax() - KELWIN_TO_CEL),
                    (avgTemp - KELWIN_TO_CEL),
                    day.getPop() * 100);

        }
    }

    private double getAvgTemp(DailyWeather.Daily day) {
        return (day.getTemp().getMorn() + day.getTemp().getDay()
                + day.getTemp().getEve() + day.getTemp().getNight()) / 4;
    }

    private String getFormattedDate(DailyWeather.Daily day) {
        Timestamp stamp = new Timestamp(day.getDt() * 1000);
        Date date = new Date(stamp.getTime());
        DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        return f.format(date);
    }

    public void printHourlyWeather(HourlyWeather hourlyWeather) {
        HourlyWeather.Hourly hour;
        HourlyWeather.Hourly.Rain rain;
        String formattedDate;
        for (int i = 0; i < hourlyWeather.getHourly().size(); i++) {
            hour = hourlyWeather.getHourly().get(i);
            formattedDate = getFormattedDateAndTime(hour);
            rain = hour.getRain();

            if (rain == null){
                System.out.printf("%19s, %6.2f %9s \n", formattedDate, (hour.getTemp() - KELWIN_TO_CEL), "unknown");
            } else  {
                System.out.printf("%19s, %6.2f %6.2f\n", formattedDate, (hour.getTemp() - KELWIN_TO_CEL), rain.getH());
            }
        }
    }

    private String getFormattedDateAndTime(HourlyWeather.Hourly hour) {
        Timestamp stamp = new Timestamp(hour.getDt() * 1000);
        Date date = new Date(stamp.getTime());
        DateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return f.format(date);
    }
}
