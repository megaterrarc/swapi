package com.sw.api.data.service;


import com.sw.api.data.repository.PlanetRepository;
import com.sw.api.domain.entity.PlanetEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlanetService {

   final PlanetRepository planetRepository;

   public PlanetService(PlanetRepository planetRepository) {
       this.planetRepository = planetRepository;
   }

   public PlanetEntity save( PlanetEntity planetEntity ) {
       return planetRepository.save( planetEntity );
   }

   public Page<PlanetEntity> find(Example<PlanetEntity> example, Pageable pageable) {
      return planetRepository.findAll(example, pageable);
   }

   public Optional<PlanetEntity> findById(String id) {
       return planetRepository.findById(id);
   }

    public void deleteById(String id) {
        planetRepository.deleteById( id );
    }


}
