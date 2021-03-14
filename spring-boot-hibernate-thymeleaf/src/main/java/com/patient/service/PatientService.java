package com.patient.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.patient.exception.RecordNotFoundException;
import com.patient.model.PatientEntity;
import com.patient.repository.PatientRepository;

@Service
public class PatientService {
	
	@Autowired
	PatientRepository repository;
	
	public List<PatientEntity> getAllPatients()
	{
		List<PatientEntity> result = (List<PatientEntity>) repository.findAll();
		
		if(result.size() > 0) {
			return result;
		} else {
			return new ArrayList<PatientEntity>();
		}
	}
	
	public List<PatientEntity> searchPatients(String fullname)
	{
		List<PatientEntity> result = (List<PatientEntity>) repository.findAll();
		List<PatientEntity> searchresult = new ArrayList<PatientEntity>();
		
		if(result.size() > 0) {
			for(PatientEntity patient:result)
			{
				if(patient.getFullname().contains(fullname))
				{
					searchresult.add(patient);
				}
			}
			return searchresult;
		} else {
			return new ArrayList<PatientEntity>();
		}
	}
	
	public PatientEntity getPaitentById(Long id) throws RecordNotFoundException 
	{
		Optional<PatientEntity> employee = repository.findById(id);
		
		if(employee.isPresent()) {
			return employee.get();
		} else {
			throw new RecordNotFoundException("No employee record exist for given id");
		}
	}
	
	public PatientEntity createOrUpdatePatient(PatientEntity entity) 
	{
		if(entity.getId()  == null) 
		{
			entity = repository.save(entity);
			
			return entity;
		} 
		else 
		{
			Optional<PatientEntity> Patient = repository.findById(entity.getId());
			
			if(Patient.isPresent()) 
			{
				PatientEntity newEntity = Patient.get();
				newEntity.setFullname(entity.getFullname());
				newEntity.setGender(entity.getGender());
				newEntity.setPhone(entity.getPhone());

				newEntity = repository.save(newEntity);
				
				return newEntity;
			} else {
				entity = repository.save(entity);
				
				return entity;
			}
		}
	} 
	
	public void deletePatientById(Long id) throws RecordNotFoundException 
	{
		Optional<PatientEntity> employee = repository.findById(id);
		
		if(employee.isPresent()) 
		{
			repository.deleteById(id);
		} else {
			throw new RecordNotFoundException("No employee record exist for given id");
		}
	} 
}