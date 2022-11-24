import java.util.List;

public class Weather {
    List<Daily> daily;

    public Weather(List<Daily> daily) {
        this.daily = daily;
    }

    public class Daily {
        Long dt;
        Temp temp;
        Double pop;

        public Daily(Long dt, Temp temp, Double pop) {
            this.dt = dt;
            this.temp = temp;
            this.pop = pop;
        }

        public class Temp {
            Double min;
            Double max;
            Double eve;
            Double night;
            Double day;
            Double morn;

            public Temp(Double min, Double max, Double eve, Double night, Double day, Double morn) {
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

