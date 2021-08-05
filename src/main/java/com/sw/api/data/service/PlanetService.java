package com.sw.api.data.service;


import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.sw.api.data.repository.PlanetRepository;
import com.sw.api.domain.entity.PlanetEntity;
import lombok.NonNull;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.sw.api.domain.entity.*;

import java.util.Optional;
import java.util.function.Function;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.*;

@Service
public class PlanetService {

   final PlanetRepository planetRepository;

   public PlanetService(PlanetRepository planetRepository) {
       this.planetRepository = planetRepository;
   }

   public PlanetEntity save(@NonNull PlanetEntity planetEntity ) {
       return planetRepository.save( planetEntity );
   }

   public Page<PlanetEntity> find(PlanetEntity planet, Pageable pageable) {

       var matcher = ExampleMatcher.matching().
            withMatcher("name", contains()).withIgnoreCase();

       Example<PlanetEntity> example = Example.of(planet, matcher);

       return planetRepository.findAll(example, pageable);

   }

   public Optional<PlanetEntity> findById(String id) {
       return planetRepository.findById(id);
   }

   public void deleteById(String id) {
        planetRepository.deleteById( id );
   }



}
