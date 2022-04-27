package com.runetsoft.demo.repository;

import com.runetsoft.demo.model.TireModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TireCrudRepository extends CrudRepository<TireModel, Integer> {
}
