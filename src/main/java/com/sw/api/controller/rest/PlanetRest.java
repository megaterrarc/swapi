package com.sw.api.controller.rest;

import com.sw.api.data.service.PlanetService;
import com.sw.api.domain.entity.PlanetEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/planet")
public class PlanetRest {

    final PlanetService planetService;

    public PlanetRest(PlanetService planetService) {
        this.planetService = planetService;
    }

    @PostMapping
    PlanetEntity save(@RequestBody PlanetEntity planetEntity ) {
        return planetService.save( planetEntity );
    }

    @GetMapping()
    Page<PlanetEntity> find(PlanetEntity name, Pageable pageable ) {


        return planetService.find(name, pageable );
    }

    @GetMapping("/{id}")
    PlanetEntity findById( @PathVariable(name = "id") String id) {
         return  planetService.findById(id).get();
    }

    @DeleteMapping("/{id}")
    void deleteById(@PathVariable("id") String id) {
        planetService.deleteById(id);
    }

}
