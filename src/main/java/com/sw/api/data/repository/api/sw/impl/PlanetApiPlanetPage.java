package com.sw.api.data.repository.api.sw.impl;


import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.jackson.Jacksonized;

import java.util.Collections;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Jacksonized
@Builder
public class PlanetApiPlanetPage {

    private Integer total_records;
    private Integer total_pages;
    private List<PlanetApi> results = Collections.emptyList();

    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Accessors(chain = true)
    @Jacksonized
    @Builder
    static class PlanetApi {
        String uid;
        String name;
        String url;
    }



}
