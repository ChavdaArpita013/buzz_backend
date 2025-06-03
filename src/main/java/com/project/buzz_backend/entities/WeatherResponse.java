package com.example.jounal.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherResponse {
    private Current current;
    @Getter
    @Setter
    public class Current {
        private String observationTime;
        private long temperature;
        private String[] weatherDescriptions;
        private long humidity;
        private long feelslike;



    }
}






