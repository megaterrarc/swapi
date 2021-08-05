package com.sw.api.domain.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@RedisHash("planet")
public class PlanetEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    String id;
    String name;
    String climate;
    String ground;
    Integer qtdFilms;

}
