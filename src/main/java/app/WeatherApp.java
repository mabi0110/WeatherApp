package app;

public class WeatherApp {

    public static void main(String[] args) {

        String key = "ac5bf29d05cc68c773cc34b1868465b6";
        String configFileName = "D:\\pulpit\\Java\\WeatherApp\\config.txt";
        String exclude = "hourly";
        String city = "London";
//        String key = args[0];
//        String configFileName = args[1];
//        String exclude = args[2];
//        String city = args[3];

        WeatherControl weatherControl = new WeatherControl();
        weatherControl.control(key, configFileName, exclude, city);
    }
}
