package com.sw.api.data.service;


import com.sw.api.data.repository.PlanetRepository;
import com.sw.api.domain.entity.PlanetEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Optional;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;

@Service
public class PlanetService {

   final PlanetRepository planetRepository;

   public PlanetService(PlanetRepository planetRepository) {
       this.planetRepository = planetRepository;
   }

   public PlanetEntity save(@Nullable PlanetEntity planetEntity ) {
       return planetRepository.save( planetEntity );
   }

   public Page<PlanetEntity> find(@Nullable PlanetEntity planet, Pageable pageable) {

       var matcher = ExampleMatcher.matching().
            withMatcher("name", contains()).withIgnoreCase();

       Example<PlanetEntity> example = Example.of(planet, matcher);

       return planetRepository.findAll(example, pageable);

   }

   public Optional<PlanetEntity> findById(@Nullable String id) {
       return planetRepository.findById(id);
   }

   public void deleteById(@Nullable String id) {
        planetRepository.deleteById( id );
   }



}
