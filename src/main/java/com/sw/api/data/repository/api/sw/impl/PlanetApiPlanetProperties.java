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
public class PlanetApiPlanetProperties {

    private PlanetApiPropertiesResult result;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Accessors(chain = true)
    @Jacksonized
    @Builder
    static class PlanetApiPropertiesResult {
        private String uid;
        private PlanetApiPropertiesProperties properties;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Accessors(chain = true)
    @Jacksonized
    @Builder
    static class PlanetApiPropertiesProperties {

        private String climate;
        private String terrain;
        private List<String> films;
    }

}
