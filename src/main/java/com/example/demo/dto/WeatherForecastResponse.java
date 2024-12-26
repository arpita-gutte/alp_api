package com.example.demo.dto;
import java.util.ArrayList;
import java.util.List;

//import com.example.demo.dto.WeatherForecastResponse.DailyForecast;

public class WeatherForecastResponse {

    private String cityName;
    private List<Forecast> forecastList;

    // Constructor to initialize forecastList
    public WeatherForecastResponse() {
        this.forecastList = new ArrayList<>();  // Initialize the list to avoid null pointer exception
    }
    // Getters and Setters for cityName and forecastList
    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public List<Forecast> getForecastList() {
        return forecastList;
    }

    public void setForecastList(List<Forecast> forecastList) {
        this.forecastList = forecastList;
    }

    public static class Forecast {
      
        private double tempMax;
        private double tempMin;
        private String weatherDescription;
        private String formattedDate;

        // Getters and Setters for Forecast fields


        public double getTempMax() {
            return tempMax;
        }

        public void setTempMax(double tempMax) {
            this.tempMax = tempMax;
        }
        
        public double getTempMin() {
            return tempMin;
        }

        public void setTempMin(double tempMin) {
            this.tempMin = tempMin;
        }

        public String getWeatherDescription() {
            return weatherDescription;
        }

        public void setWeatherDescription(String weatherDescription) {
            this.weatherDescription = weatherDescription;
        }


        public String getFormattedDate() {
            return formattedDate;
        }

        public void setFormattedDate(String formattedDate) {
            this.formattedDate = formattedDate;
        }
    }
}