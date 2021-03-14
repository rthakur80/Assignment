package com.patient.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.patient.model.PatientEntity;

@Repository
public interface PatientRepository 
			extends CrudRepository<PatientEntity, Long> {

}
