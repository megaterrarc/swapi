package com.sw.api.controller.rest;

import com.sw.api.data.service.PlanetService;
import com.sw.api.domain.entity.PlanetEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/planet")
public class PlanetRest {

    final PlanetService planetService;

    public PlanetRest(PlanetService planetService) {
        this.planetService = planetService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    PlanetEntity save(@RequestBody PlanetEntity planetEntity ) throws InterruptedException {
        return planetService.save( planetEntity );
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    Page<PlanetEntity> find(PlanetEntity name, Pageable pageable ) {
        return planetService.find(name, pageable );
    }

    @GetMapping("/fomapi")
    @ResponseStatus(HttpStatus.OK)
    Page<PlanetEntity> find( Pageable pageable ) throws InterruptedException {
        return planetService.findApiAll( pageable );
    }

    @GetMapping("/{id}")
    ResponseEntity<PlanetEntity> findById(@PathVariable(name = "id") String id) {
        return planetService.findById(id).isPresent() ?
                ResponseEntity.ok( planetService.findById(id).get() )
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    void deleteById(@PathVariable("id") String id) {
        planetService.deleteById(id);
    }

}
