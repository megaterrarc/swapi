package com.sw.api.controller.rest;

import com.sw.api.data.service.PlanetService;
import com.sw.api.domain.entity.PlanetEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/planet")
public class PlanetRest {

    final PlanetService planetService;

    public PlanetRest(PlanetService planetService) {
        this.planetService = planetService;
    }

    @PostMapping
    PlanetEntity add(PlanetEntity planetEntity ) {
        return planetService.save( planetEntity );
    }

    @GetMapping()
    List<PlanetEntity> find() {
        return planetService.find();
    }

    @GetMapping()
    List<PlanetEntity> findByName(String name) {
        return planetService.findByName(name);
    }

    List<PlanetEntity> findById(String id) {
        return planetService.findByName(id);
    }

    void deleteById(String id) {
        planetService.deleteById(id);
    }

}
