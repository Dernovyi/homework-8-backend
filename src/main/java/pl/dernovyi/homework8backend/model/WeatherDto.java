package pl.dernovyi.homework8backend.model;

import javax.persistence.*;

@Entity
@Table(name = "weather")
public class WeatherDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;

    private int temperature;

    private String ltime;

    public WeatherDto() {
    }

    public WeatherDto(String city, int temperature, String ltime) {
        this.city = city;
        this.temperature = temperature;
        this.ltime = ltime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public String getLocaltime() {
        return ltime;
    }

    public void setLocaltime(String ltime) {
        this.ltime = ltime;
    }
}
