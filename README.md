# WeatherApp

## Description

A programing task for Tink Winter Internship.  

WeatherApp is a simple weather forecast command line app that pulls weather data from OpenWeather [One Call API](https://openweathermap.org/api/one-call-api) based
on user input and prints it to standard output in a readable form.

Based on the user's input, this application allows to find out one of the following for a specific location:
 
* daily weather forecast for the next 7 days
* hourly weather forecast for the next 48 hours

## Design

The program takes exactly four command line arguments, in order:

* OpenWeather One Call 2.5 API key
* path to a configuration file, which contains comma-separated values: city name, latitude, longitude
* forecast type (one of: `daily`, `hourly`)
* name of the city

The program prints weather forecast data for input city for the next 48 hours (`hourly`) or 7 days (`daily`).

In case of daily forecast the data points are:

* min. temperature
* max. temperature
* average of morning, day, evening and night temperatures
* probability of precipitation (%)

In case of hourly forecast:

* temperature
* precipitation volume

Temperatures are given in degrees Celcius, and where a data point is not available, `unknown` is printed.
