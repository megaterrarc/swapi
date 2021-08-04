package com.sw.api.domain.entity;

import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@RedisHash("planet")
public class PlanetEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    String name;
    String climate;
    String ground;
    Integer qtdFilms;

}
