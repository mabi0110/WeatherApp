package model;

import com.google.gson.annotations.SerializedName;

import java.util.List;
public class HourlyWeather {

    private List<Hourly> hourly;

    public List<Hourly> getHourly() {
        return hourly;
    }

    public HourlyWeather(List<Hourly> hourly) {
        this.hourly = hourly;
    }

    public static class Hourly {
        private double temp;
        private long dt;
        private Rain rain;

        public double getTemp() {
            return temp;
        }

        public long getDt() {
            return dt;
        }

        public Rain getRain() {
            return rain;
        }

        public Hourly(double temp, long dt, Rain rain) {
            this.temp = temp;
            this.dt = dt;
            this.rain = rain;
        }

        public static class Rain {

            @SerializedName(value = "1h")
            private double h;

            public double getH() {
                return h;
            }
            public Rain(double h) {
                this.h = h;
            }
        }
    }
}









