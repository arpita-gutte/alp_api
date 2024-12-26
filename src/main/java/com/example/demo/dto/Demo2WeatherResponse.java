package com.example.demo.dto;


public class Demo2WeatherResponse {
	 private String cityName;
	    private double temperature;
	    private int humidity;
	    private int pressure;
	    private String weatherDescription;
	    private double windSpeed;
	 
	    // Getters and Setters
	    public String getCityName() {
	        return cityName;
	    }
	 
	    public void setCityName(String cityName) {
	        this.cityName = cityName;
	    }
	 
	    public double getTemperature() {
	        return temperature;
	    }
	 
	    public void setTemperature(double temperature) {
	        this.temperature = temperature;
	    }
	 
	    public int getHumidity() {
	        return humidity;
	    }
	 
	    public void setHumidity(int humidity) {
	        this.humidity = humidity;
	    }
	 
	    public int getPressure() {
	        return pressure;
	    }
	 
	    public void setPressure(int pressure) {
	        this.pressure = pressure;
	    }
	 
	    public String getWeatherDescription() {
	        return weatherDescription;
	    }
	 
	    public void setWeatherDescription(String weatherDescription) {
	        this.weatherDescription = weatherDescription;
	    }
	 
	    public double getWindSpeed() {
	        return windSpeed;
	    }
	 
	    public void setWindSpeed(double windSpeed) {
	        this.windSpeed = windSpeed;
	    }

}
