package com.springproj.journalApp.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class weatherResponse {


    private Current current;


    public Current getCurrent() { return current; }
    public void setCurrent(Current current) { this.current = current; }


    @Getter
    @Setter
    public static class Current {
        public int temperature;
        public List<String> weather_descriptions;


        public int feelslike;

    }



}
