package com.sw.api.data.repository.api.sw.impl;

import com.sw.api.data.repository.api.sw.PlanetApiRepository;
import com.sw.api.domain.entity.PlanetEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class PlanetApiRepositoryImpl implements PlanetApiRepository {


    public static final String URL_PLANET = "https://www.swapi.tech/api/planets/";
    public static final String URL_FILMS = "https://www.swapi.tech/api/films/";

    final  RestTemplate restTemplate;

    public PlanetApiRepositoryImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Page<PlanetEntity> findAllApi( Pageable pageable) {
        return findAllApi(null, pageable);
    }

    @Override
    public Page<PlanetEntity> findAllApi(String name, Pageable pageable) {

        var params = new HashMap<String, String>();

        UriComponentsBuilder url = UriComponentsBuilder.fromHttpUrl(URL_PLANET)
                .queryParam("limit", String.valueOf(pageable.getPageSize()))
                .queryParam("page", String.valueOf(pageable.getPageNumber()));

        if ( name != null && !name.isEmpty()) {
            url.queryParam("name", String.valueOf(pageable.getPageSize()));
        }

        PlanetApiPlanetPage planetApiPlanetPage = restTemplate
                .getForObject( url.toUriString() , PlanetApiPlanetPage.class, params);

        List<PlanetApiPlanetPage.PlanetApi> results = planetApiPlanetPage.getResults() == null ? Collections.emptyList() :  planetApiPlanetPage.getResults();

        final var filmsQtd = results.size() > 0 ? findApiQtdFilmsPerPlanet() : new HashMap<String, Integer>(20);

        return new PageImpl<>(results.stream().parallel().map(planetApi -> {

                    PlanetApiPlanetProperties planetApiPlanetProperties = restTemplate
                            .getForObject( URL_PLANET + planetApi.getUid(), PlanetApiPlanetProperties.class);


                    return  PlanetEntity.builder()
                            .name(planetApi.getName())
                            .id(planetApi.getUid())
                            .climate( planetApiPlanetProperties.getResult() == null ? null : planetApiPlanetProperties.getResult().getProperties().getClimate() )
                            .ground(  planetApiPlanetProperties.getResult() == null ? null : planetApiPlanetProperties.getResult().getProperties().getTerrain() )
                            .qtdFilms(  planetApiPlanetProperties.getResult() == null ? null : filmsQtd.getOrDefault( URL_PLANET + planetApi.getUid(), 0) )
                            .build();
                }
        ).collect(Collectors.toList()), pageable, planetApiPlanetPage.getTotal_records());
    }

    private HashMap<String, Integer> findApiQtdFilmsPerPlanet() {

        PlanetApiFilms planetFilms = restTemplate
                .getForObject( URL_FILMS , PlanetApiFilms.class);

        var hashMap = new HashMap<String, Integer>(20);

        planetFilms.getResult().forEach(planetResult -> {

            planetResult.getProperties().getPlanets().forEach(s -> {
                hashMap.put(s,  hashMap.getOrDefault( s, Integer.valueOf(0) )  + 1 );
            });

        });

        return  hashMap;

    }

    @Override
    public Integer findApiQtdFilmsByPlanet(String planetName) {

        Page<PlanetEntity> planetEntities = findAllApi(planetName, PageRequest.ofSize(20));

        return  planetEntities.getTotalElements() > 1 ? planetEntities.getContent().get(0).getQtdFilms() : 0;

    }

}
