package com.sw.api.data.repository.api.sw.impl;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Jacksonized
@Builder
public class PlanetApiFilms {

    private List<PlanetApiFilmsPropertiesResult> result;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Accessors(chain = true)
    @Jacksonized
    @Builder
    static class PlanetApiFilmsPropertiesResult {
        private String uid;
        private PlanetApiFilmsPropertiesProperties properties;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Accessors(chain = true)
    @Jacksonized
    @Builder
    static class PlanetApiFilmsPropertiesProperties {
        private List<String> planets;
    }

}
