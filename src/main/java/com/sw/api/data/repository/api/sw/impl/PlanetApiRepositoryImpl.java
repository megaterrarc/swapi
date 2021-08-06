package com.sw.api.data.repository.api.sw.impl;

import com.sun.jdi.request.InvalidRequestStateException;
import com.sw.api.data.repository.api.sw.PlanetApiRepository;
import com.sw.api.domain.entity.PlanetEntity;
import net.bytebuddy.asm.Advice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;

public class PlanetApiRepositoryImpl implements PlanetApiRepository {


    public static final String URL_PLANET = "https://www.swapi.tech/api/planets/";
    public static final String URL_FILMS = "https://www.swapi.tech/api/films/";

    final  RestTemplate restTemplate;

    public PlanetApiRepositoryImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Page<PlanetEntity> findAllApi( Pageable pageable) throws InterruptedException {
        return findAllApi(null, pageable);
    }

    @Override
    public Page<PlanetEntity> findAllApi(String name, Pageable pageable) throws InterruptedException {

        UriComponentsBuilder url = UriComponentsBuilder.fromHttpUrl(URL_PLANET)
                .queryParam("limit", String.valueOf(pageable.getPageSize()))
                .queryParam("page", String.valueOf(pageable.getPageNumber() + 1));

        if ( name != null && !name.isEmpty()) {
            url.queryParam("name", name);
        }

        HttpEntity<?> request = new HttpEntity<>(null,null);

        String toUriString = url.toUriString();

        ResponseEntity<PlanetApiPlanetPage> planetApiPlanetPage = restTemplate
                .exchange(toUriString, HttpMethod.GET , request, PlanetApiPlanetPage.class);

        if ( planetApiPlanetPage.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
            sleep( Long.parseLong(Objects.requireNonNull(planetApiPlanetPage.getHeaders().getFirst("Retry-After"))) + 3L  );
            return findAllApi( name, pageable);
        }

        if ( planetApiPlanetPage.getStatusCode() == HttpStatus.OK ) {

            List<PlanetApiPlanetPage.PlanetApi> results = planetApiPlanetPage.getBody().getResults() == null ? Collections.emptyList() : planetApiPlanetPage.getBody().getResults();

            final var filmsQtd = results.size() > 0 ? findApiQtdFilmsPerPlanet() : new HashMap<String, Integer>(20);

            return new PageImpl<>(results.stream().parallel().map(planetApi -> {

                        PlanetApiPlanetProperties planetApiPlanetProperties = restTemplate
                                .getForObject(URL_PLANET + planetApi.getUid(), PlanetApiPlanetProperties.class);


                        return PlanetEntity.builder()
                                .name(planetApi.getName())
                                .id(planetApi.getUid())
                                .climate(planetApiPlanetProperties.getResult() == null ? null : planetApiPlanetProperties.getResult().getProperties().getClimate())
                                .ground(planetApiPlanetProperties.getResult() == null ? null : planetApiPlanetProperties.getResult().getProperties().getTerrain())
                                .qtdFilms(planetApiPlanetProperties.getResult() == null ? null : filmsQtd.getOrDefault(URL_PLANET + planetApi.getUid(), 0))
                                .build();
                    }
            ).collect(Collectors.toList()), pageable, planetApiPlanetPage.getBody().getTotal_records() == null ? 0 : planetApiPlanetPage.getBody().getTotal_records() );
        }

        throw new InvalidRequestStateException("Falha na Comunicação");
    }

    private HashMap<String, Integer> findApiQtdFilmsPerPlanet() throws InterruptedException {
        HttpEntity<?> request = new HttpEntity<>(null,null);

        ResponseEntity<PlanetApiFilms> planetFilms = restTemplate
                .exchange( URL_FILMS, HttpMethod.GET , request, PlanetApiFilms.class);

        if ( planetFilms.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
            sleep( Long.parseLong(Objects.requireNonNull(planetFilms.getHeaders().getFirst("Retry-After"))) + 3L  );
            return findApiQtdFilmsPerPlanet();
        }

        if ( planetFilms.getStatusCode() == HttpStatus.OK) {
            var hashMap = new HashMap<String, Integer>(20);

            planetFilms.getBody().getResult().forEach(planetResult -> {

                planetResult.getProperties().getPlanets().forEach(s -> {
                    hashMap.put(s,  hashMap.getOrDefault( s, Integer.valueOf(0) )  + 1 );
                });

            });

            return  hashMap;
        }

        throw new InvalidRequestStateException("Falha na Comunicação");

    }

    @Override
    public Integer findApiQtdFilmsByPlanet(String planetName) throws InterruptedException {

        Page<PlanetEntity> planetEntities = findAllApi(planetName, PageRequest.ofSize(10));

        return  planetEntities.getTotalElements() == 1 ? planetEntities.getContent().get(0).getQtdFilms() : 0;

    }

}
