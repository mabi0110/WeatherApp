package model;

import java.util.List;

public class DailyWeather {
    private List<Daily> daily;

    public List<Daily> getDaily() {
        return daily;
    }

    public DailyWeather(List<Daily> daily) {
        this.daily = daily;
    }

    public static class Daily {
        private long dt;
        private Temp temp;
        private double pop;

        public long getDt() {
            return dt;
        }

        public Temp getTemp() {
            return temp;
        }

        public double getPop() {
            return pop;
        }

        public Daily(long dt, Temp temp, double pop) {
            this.dt = dt;
            this.temp = temp;
            this.pop = pop;
        }

        public static class Temp {
            private double min;
            private double max;
            private double eve;
            private double night;
            private double day;
            private double morn;

            public double getMin() {
                return min;
            }

            public double getMax() {
                return max;
            }

            public double getEve() {
                return eve;
            }

            public double getNight() {
                return night;
            }

            public double getDay() {
                return day;
            }

            public double getMorn() {
                return morn;
            }

            public Temp(double min, double max, double eve, double night, double day, double morn) {
                this.min = min;
                this.max = max;
                this.eve = eve;
                this.night = night;
                this.day = day;
                this.morn = morn;
            }
        }
    }
}

