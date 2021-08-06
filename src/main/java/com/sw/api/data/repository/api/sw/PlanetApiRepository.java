package com.sw.api.data.repository.api.sw;

import com.sw.api.domain.entity.PlanetEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlanetApiRepository {

      Page<PlanetEntity> findAllApi(Pageable pageable) throws InterruptedException;
      Integer findApiQtdFilmsByPlanet(String planetName) throws InterruptedException;
      Page<PlanetEntity> findAllApi(String name, Pageable pageable) throws InterruptedException;

}
