import java.util.List;
public class HourlyWeather {

    List<Hourly> hourly;

    public HourlyWeather(List<Hourly> hourly) {
        this.hourly = hourly;
    }

    public class Hourly {
        Double temp;
        Long dt;
        Rain rain;

        public Hourly(Double temp, Long dt, Rain rain) {
            this.temp = temp;
            this.dt = dt;
            this.rain = rain;
        }

        public class Rain {
            Double h;

            public Rain(Double h) {
                this.h = h;
            }
        }
    }
}
