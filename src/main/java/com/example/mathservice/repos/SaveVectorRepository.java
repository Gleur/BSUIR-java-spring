package com.example.mathservice.repos;

import com.example.mathservice.models.SaveVector;
import org.springframework.data.repository.CrudRepository;

public interface SaveVectorRepository extends CrudRepository<SaveVector, Long> {

//List<SaveVector> find


    //@Override
    //Optional<SaveVector> findById(Long aLong);
}
