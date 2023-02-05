package org.example;

import org.example.utils.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.core5.net.URIBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;

public class App {
    private final static String API_URL = "https://api.openweathermap.org/data/2.5/weather";

    public static void main(String[] args) {
        URIBuilder uriBuilder = null;
        try {
            uriBuilder = new URIBuilder(API_URL);
            uriBuilder.addParameter("lat", "21.543333");
            uriBuilder.addParameter("lon", "39.172778");
            uriBuilder.addParameter("appid", System.getenv("appid"));
            uriBuilder.addParameter("units", "metric");
            URI uri = uriBuilder.build();
            HttpResponse<String> response = HTTPHelper.sendGet(uri);
            if (response != null) {
                System.out.println(response.body());

                try (FileWriter fileWriter = new FileWriter("src/main/java/org/example/result.json")) {
                    fileWriter.write(response.body());
                } catch(IOException e) {
                    System.out.println(e.getMessage());
                }

                WeatherInfo wInfo = parseWeatherResponse(response.body(), WeatherInfo.class);
                System.out.println(wInfo);
            }

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static WeatherInfo parseWeatherResponse(String responseString, Class<?> elementClass){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode weatherInfoNode = objectMapper.readTree(responseString);
            WeatherInfo wInfo = new WeatherInfo();
            String country =  weatherInfoNode.get("sys").get("country").textValue();
            String name = weatherInfoNode.get("name").textValue();
            double tempMin = weatherInfoNode.get("main").get("temp_min").doubleValue();
            double tempMax = weatherInfoNode.get("main").get("temp_max").doubleValue();

            wInfo.setCountry(country);
            wInfo.setName(name);
            wInfo.setTempMin(tempMin);
            wInfo.setTempMax(tempMax);

            return wInfo;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

}