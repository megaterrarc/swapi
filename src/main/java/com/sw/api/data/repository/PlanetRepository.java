package com.sw.api.data.repository;

import com.sw.api.domain.entity.PlanetEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanetRepository extends CrudRepository<PlanetEntity, String>, QueryByExampleExecutor<PlanetEntity> {


}
