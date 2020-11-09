package pl.dernovyi.homework8backend.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import pl.dernovyi.homework8backend.model.Weather;
import pl.dernovyi.homework8backend.model.WeatherDto;
@Component
public class ToWeatherDto {
   public WeatherDto toDto(ResponseEntity<Weather> weather ){
       return new WeatherDto(weather.getBody().getLocation().getName(),
               weather.getBody().getCurrent().getTemperature(),
               weather.getBody().getLocation().getLocaltime());
   }
}
