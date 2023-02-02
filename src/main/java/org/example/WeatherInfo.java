package org.example;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public class WeatherInfo {
    private String country;
    private String name;
    private double tempMin;
    private double tempMax;
    public double getTempMin() {
        return tempMin;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTempMin(double tempMin) {
        this.tempMin = tempMin;
    }

    public double getTempMax() {
        return tempMax;
    }

    public void setTempMax(double tempMax) {
        this.tempMax = tempMax;
    }

    @SuppressWarnings("unchecked")
    @JsonProperty("main")
    private void unpackNested(Map<String,Object>main) {
        this.tempMin = (double)main.get("temp_min");
        this.tempMax = (double)main.get("temp_max");
    }

    @SuppressWarnings("unchecked")
    @JsonProperty("sys")
    private void unpackNested2(Map<String,Object> sys) {
        this.country = (String)sys.get("country");
    }


    @Override
    public String toString() {
        return "WeatherInfo{" + "country='" + country + '\'' + ", name='" + name + '\''
                + ", tempMin=" + tempMin + ", tempMax=" + tempMax + '}';
    }
}