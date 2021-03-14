package com.patient.web;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.patient.exception.RecordNotFoundException;
import com.patient.model.PatientEntity;
import com.patient.service.PatientService;

@Controller
@RequestMapping("/")
public class PatientMvcController 
{
	private static final Logger logger = LogManager.getLogger(PatientMvcController.class);
	
	@Autowired
	PatientService service;

	@RequestMapping
	public String getAllPaitents(Model model) 
	{
		logger.info("getAllPaitents method start");
		List<PatientEntity> list = service.getAllPatients();
		if(list.size()==0)
		{
			model.addAttribute("errorMessage","No Data Found");
		}
		else
		{
		model.addAttribute("patients", list);
		}
		logger.info("getAllPaitents method end");
		return "list-patients";
	}
	
	
	@RequestMapping(path = "/searchPatient")
	public String searchPatientByName(Model model,@RequestParam String search) 
							throws RecordNotFoundException 
	{
		logger.info("search method start"+search.length());
		if(search.length()==0)
		{
			model.addAttribute("errorMessage","Please enter string to search Patient.");
		}
		else
		{
			List<PatientEntity> list = service.searchPatients(search);
			if(list.size()==0)
			{
				model.addAttribute("errorMessage","No Data Found");
			}
			else
			{
				model.addAttribute("patients", list);
			}
		}
		return "list-patients";
	}
	
	

	@RequestMapping(path = {"/editPatient", "/editPatient/{id}"})
	public String editPatientById(Model model, @PathVariable("id") Optional<Long> id) 
							throws RecordNotFoundException 
	{
		logger.info("editPatientById method start");
		if (id.isPresent()) {
			PatientEntity entity = service.getPaitentById(id.get());
			model.addAttribute("patient", entity);
			logger.info("Patient edited   "+entity.getFullname());
		} else {
			model.addAttribute("patient", new PatientEntity());
			logger.info("Patient added   "+new PatientEntity().getFullname());
		}
		logger.info("editPatientById method end");
		return "add-edit-patient";
	}
	
	@RequestMapping(path = "/deletePatient/{id}")
	public String deletePatientById(Model model, @PathVariable("id") Long id) 
							throws RecordNotFoundException 
	{
		service.deletePatientById(id);
		logger.info("Patient deleted   "+id);
		return "redirect:/";
	}

	@RequestMapping(path = "/createPatient", method = RequestMethod.POST)
	public String createOrUpdatePatient(PatientEntity patient) 
	{
		service.createOrUpdatePatient(patient);
		logger.info("Patient created   "+patient.toString());
		return "redirect:/";
	}
}
