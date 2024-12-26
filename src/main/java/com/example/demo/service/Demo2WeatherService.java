//package com.example.demo.service;
//import org.springframework.stereotype.Service;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.util.UriComponentsBuilder;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.client.HttpClientErrorException;
//import org.springframework.web.client.HttpServerErrorException;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//@Service
//public class Demo2WeatherService
//{
//
//    @Value("${weather_city}")
//    private String apiKey;
//
//    // Get current weather for a specific city
//    public String getWeather(String city) {
//        try {
//            String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey + "&units=metric";
//            RestTemplate restTemplate = new RestTemplate();
//            return restTemplate.getForObject(url, String.class);
//        } catch (HttpClientErrorException | HttpServerErrorException e) {
//            return "Error while fetching weather data: " + e.getResponseBodyAsString();
//        } catch (Exception e) {
//            return "Error occurred: " + e.getMessage();
//        }
//    }
//
//	public String getWeatherForecastByCity(String city) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//}
//
//
//    
//
//
//
//   
package com.example.demo.service;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.web.client.RestTemplate;


import com.example.demo.dto.Demo2WeatherResponse;
import com.example.demo.dto.WeatherForecastResponse;
import com.example.demo.dto.WeatherForecastResponse.Forecast;


@Service
public class Demo2WeatherService
{

    @Value("${weather_city}")
    private String apiKey;

    // Get current weather for a specific city
    public Demo2WeatherResponse getWeather(String city) {
       
            String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey + "&units=metric";
            RestTemplate restTemplate = new RestTemplate();
           
            String response = restTemplate.getForObject(url, String.class);
     
            // Parse the response
            JSONObject json = new JSONObject(response);
     
            Demo2WeatherResponse weatherResponse = new Demo2WeatherResponse();
            weatherResponse.setCityName(json.getString("name"));
            weatherResponse.setTemperature(json.getJSONObject("main").getDouble("temp"));
            weatherResponse.setHumidity(json.getJSONObject("main").getInt("humidity"));
            weatherResponse.setPressure(json.getJSONObject("main").getInt("pressure"));
            weatherResponse.setWeatherDescription(json.getJSONArray("weather").getJSONObject(0).getString("description"));
            weatherResponse.setWindSpeed(json.getJSONObject("wind").getDouble("speed"));
     
            return weatherResponse;
    }
    private String formatTimestamp(long timestamp) {
        // Convert the Unix timestamp to a Date object
        Date date = new Date(timestamp * 1000); // Multiply by 1000 to convert to milliseconds
        
        // Define the format for "Mon dd/MM/yyyy"
        SimpleDateFormat sdf = new SimpleDateFormat("EEE dd/MM/yyyy");
        
        // Return the formatted date as a string
        return sdf.format(date);
    }

    public WeatherForecastResponse getWeatherForecast(String city) {
        // Prepare the API URL
        String url = "http://api.openweathermap.org/data/2.5/forecast?q=" + city + "&appid=" + apiKey + "&units=metric";
        RestTemplate restTemplate = new RestTemplate();

        // Call the API and get the response as a string
        String response = restTemplate.getForObject(url, String.class);

        // Parse the JSON response
        JSONObject json = new JSONObject(response);
        JSONArray list = json.getJSONArray("list");

        // Prepare the WeatherForecastResponse object
        WeatherForecastResponse forecastResponse = new WeatherForecastResponse();
        forecastResponse.setCityName(json.getJSONObject("city").getString("name"));
        
        // List to store aggregated day-wise forecast data
        List<WeatherForecastResponse.Forecast> aggregatedForecasts = new ArrayList<>();

        
     // Initialize variables for each day
        double dayMaxTemp = Double.MIN_VALUE;
        double dayMinTemp = Double.MAX_VALUE;
        String dayWeatherDescription = "";
        int count = 0;
        String currentDay = "";

        for (int i = 0; i < list.length(); i++) {
            JSONObject forecastJson = list.getJSONObject(i);
            long timestamp = forecastJson.getLong("dt");
            String formattedDate = formatTimestamp(timestamp);
            double tempMax = forecastJson.getJSONObject("main").getDouble("temp_max");
            double tempMin = forecastJson.getJSONObject("main").getDouble("temp_min");
            String weatherDescription = forecastJson.getJSONArray("weather").getJSONObject(0).getString("description");

            // Check if we have moved to the next day
            if (!formattedDate.equals(currentDay)) {
                if (count > 0) {
                    // Save the aggregated data for the previous day
                    WeatherForecastResponse.Forecast dayForecast = new WeatherForecastResponse.Forecast();
                    dayForecast.setFormattedDate(currentDay);
                    dayForecast.setTempMax(dayMaxTemp);
                    dayForecast.setTempMin(dayMinTemp);
                    dayForecast.setWeatherDescription(dayWeatherDescription);
                    aggregatedForecasts.add(dayForecast);
                }

                // Reset variables for the new day
                currentDay = formattedDate;
                dayMaxTemp = tempMax;
                dayMinTemp = tempMin;
                dayWeatherDescription = weatherDescription;
                count = 1;
            } else {
                // Aggregate the data for the same day
                dayMaxTemp = Math.max(dayMaxTemp, tempMax);
                dayMinTemp = Math.min(dayMinTemp, tempMin);
              count++;
                // You can choose to average weather descriptions, or just keep the first one
            }
        }

        // Add the final day's forecast after the loop
        WeatherForecastResponse.Forecast dayForecast = new WeatherForecastResponse.Forecast();
        dayForecast.setFormattedDate(currentDay);
        dayForecast.setTempMax(dayMaxTemp);
        dayForecast.setTempMin(dayMinTemp);
         dayForecast.setWeatherDescription(dayWeatherDescription);
        aggregatedForecasts.add(dayForecast);

        // Set the aggregated daily forecasts in the response
        forecastResponse.setForecastList(aggregatedForecasts);

        return forecastResponse;
       
    }
}


    



   

