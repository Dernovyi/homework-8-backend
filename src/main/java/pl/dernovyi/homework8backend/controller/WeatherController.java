package pl.dernovyi.homework8backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.dernovyi.homework8backend.model.Weather;
import pl.dernovyi.homework8backend.repo.WeatherRepo;
import pl.dernovyi.homework8backend.service.ToWeatherDto;


@Component
@EnableScheduling
public class WeatherController {

    ToWeatherDto toDto;
    WeatherRepo weatherRepo;

    @Autowired
    public WeatherController(ToWeatherDto toDto, WeatherRepo weatherRepo) {
        this.toDto = toDto;
        this.weatherRepo = weatherRepo;
    }

    @Value("${city}")
    private String city;
    @Value("${access_key}")
    private String key;

    @Scheduled(cron="0 59 * * * ?")
    public void getWeather() {
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder url = UriComponentsBuilder.fromHttpUrl("http://api.weatherstack.com/current")
                .queryParam("access_key", key)
                .queryParam("query", city);
        ResponseEntity<Weather> forEntity = restTemplate.getForEntity(url.toUriString(), Weather.class);

        weatherRepo.save(toDto.toDto(forEntity));


    }


}
