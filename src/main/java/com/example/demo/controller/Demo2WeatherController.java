package com.example.demo.controller;


import com.example.demo.dto.Demo2WeatherResponse;
import com.example.demo.dto.WeatherForecastResponse;
import com.example.demo.service.Demo2WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Demo2WeatherController 
{

    @Autowired
   private  Demo2WeatherService weatherService;

    public Demo2WeatherController(Demo2WeatherService weatherService) {
        this.weatherService = weatherService;
    }
    // Get current weather for a specific city
    @GetMapping("/weather")
   public Demo2WeatherResponse getWeather(@RequestParam String city) 
    {
       return weatherService.getWeather(city);
    } 
    @GetMapping("/weather/forecast")
    public WeatherForecastResponse getWeatherForecast(@RequestParam String city) {
        return weatherService.getWeatherForecast(city);
    }
 }
