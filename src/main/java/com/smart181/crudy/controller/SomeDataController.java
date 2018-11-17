package com.smart181.crudy.controller;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smart181.crudy.model.CustomEntityRep;
import com.smart181.crudy.model.SomeData;
import com.smart181.crudy.repository.SomeDataRepository;

@RestController
@RequestMapping("/someData")
public class SomeDataController {

	@Autowired
	SomeDataRepository repository;
	
	@CrossOrigin
	@GetMapping()
	List<SomeData> getAllData(){
		return repository.findAll();
	}
	
	@CrossOrigin
	@GetMapping("/{id}")
	ResponseEntity<SomeData> getDataById(@PathVariable(value="id") long id) {
		Optional<SomeData> data = repository.findById(id);
		if(data.isPresent()) {
			return ResponseEntity.ok(data.get());//.build();
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@CrossOrigin
	@PostMapping()
	SomeData addSomeData(@RequestBody SomeData data){
		return repository.save(data);
	}
	
	@CrossOrigin
	@PutMapping("/{id}")
	ResponseEntity<SomeData> updateData(@PathVariable(value="id") long id, @RequestBody SomeData data){

		if(!repository.existsById(id))
			return ResponseEntity.notFound().build();
		
		// Force the id to ensure the right object is updated
		data.setId(id);
		
		SomeData updatedSomeData = repository.save(data);
		return ResponseEntity.ok(updatedSomeData);
	}

	@CrossOrigin
	@PatchMapping("/{id}")
	ResponseEntity<SomeData> patialUpdateData(@PathVariable(value="id") long id, @RequestBody CustomEntityRep data){
		Optional<SomeData> dataToUpdateOpt = repository.findById(id);
		if(!dataToUpdateOpt.isPresent())
			return ResponseEntity.notFound().build();
		
		SomeData dataToUpdate = dataToUpdateOpt.get();
		
		// Update the mandatory attributes
		if(data.hasPropertie("someText")) {
			Object someTextObj = (data.getPropertie("someText"));
			
			if(!(someTextObj instanceof String))
				return ResponseEntity.badRequest().build();
			
			dataToUpdate.setSomeText((String) someTextObj);
		}
		
		if(data.hasPropertie("someInteger")) {
			Object someIntegerObj = (data.getPropertie("someInteger"));
			
			if(!(someIntegerObj instanceof Integer))
				return ResponseEntity.badRequest().build();
			
			dataToUpdate.setSomeInteger((Integer) someIntegerObj);
		}

		// TODO
		
		SomeData updatedSomeData = repository.save(dataToUpdate);
		return ResponseEntity.ok(updatedSomeData);
	}
	
	@CrossOrigin
	@DeleteMapping("/{id}")
	ResponseEntity<?> deleteData(@PathVariable(value="id") long id){
		Optional<SomeData> data = repository.findById(id);
		if(data.isPresent()) {
			repository.delete(data.get());
			
			CustomEntityRep result = new CustomEntityRep();
			result.addPropertie("id", id);
			return ResponseEntity.ok(result.getEntity());
		}
		
		return ResponseEntity.notFound().build();
	}
}
