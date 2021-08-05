package com.sw.api.data.repository;

import com.sw.api.domain.entity.PlanetEntity;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanetRepository extends PagingAndSortingRepository<PlanetEntity, String> , QueryByExampleExecutor<PlanetEntity> {

}
