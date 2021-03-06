package com.sw.api.domain.entity;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Getter @Setter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Jacksonized
@Builder
@Document
public class PlanetEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    String id;
    String name;
    String climate;
    String ground;
    Integer qtdFilms;

}
