package com.springproj.journalApp.service;

import com.springproj.journalApp.api.response.weatherResponse;
import com.springproj.journalApp.cache.appcache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service

public class weatherservice {

//    private static final String API_KEY = "***REMOVED***";  sensity data is yaml
@Value("${weather.api.key}")
private  String API_KEY;

@Autowired
private appcache  appcache;



    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private redisService redisService;





    public weatherResponse getWether(String city ){
        String normalizedCity = city.toLowerCase().trim();
        weatherResponse cachedresp = redisService.get("weather_of_" + normalizedCity, weatherResponse.class);

        if(cachedresp!=null){
            return cachedresp;
        }
        else{
        String finalAPI= "http://api.weatherstack.com/current?access_key=" + API_KEY + "&query=" + normalizedCity;
     ResponseEntity <weatherResponse> response= restTemplate.exchange(finalAPI, HttpMethod.GET,null, weatherResponse.class);
   weatherResponse  body=  response.getBody();
   if(body!=null){
       redisService.set("weather_of_"+normalizedCity,body,300l);
   }
   return body;

    }

}}
//like we have basic auth wether site have api access key pass
//processof converting json response into corresponding java object is called deserialization
//pojo to json -serialization