package pl.dernovyi.homework8backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import pl.dernovyi.homework8backend.model.WeatherDto;

@Repository
public interface WeatherRepo extends JpaRepository<WeatherDto, Long> {
}
