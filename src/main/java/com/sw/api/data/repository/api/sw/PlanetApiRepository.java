package com.sw.api.data.repository.api.sw;

import com.sw.api.domain.entity.PlanetEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlanetApiRepository {

      Page<PlanetEntity> findAllApi(Pageable pageable);
      Integer findApiQtdFilmsByPlanet(String planetName);
      Page<PlanetEntity> findAllApi(String name, Pageable pageable);

}
