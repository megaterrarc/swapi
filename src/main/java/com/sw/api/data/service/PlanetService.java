package com.sw.api.data.service;


import com.sw.api.data.repository.PlanetRepository;
import com.sw.api.domain.entity.PlanetEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlanetService {

   final PlanetRepository planetRepository;

   public PlanetService(PlanetRepository planetRepository) {
       this.planetRepository = planetRepository;
   }

   public PlanetEntity save( PlanetEntity planetEntity ) {
       return planetRepository.save( planetEntity );
   }

   public List<PlanetEntity> find() {
      var  planetEntities = new ArrayList<PlanetEntity>();
      planetRepository.findAll().forEach(planetEntities::add);
      return planetEntities;
   }

   public List<PlanetEntity> findByName(String name) {
       var  planetEntities = new ArrayList<PlanetEntity>();
       planetRepository.findAll().forEach(planetEntities::add);
       return planetEntities;
   }

   public List<PlanetEntity> findById(String id) {
       var  planetEntities = new ArrayList<PlanetEntity>();
       planetRepository.findById(id).ifPresent( planetEntities::add );
       return planetEntities;
   }

    public void deleteById(String id) {
        planetRepository.deleteById( id );
    }


}
