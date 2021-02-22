package com.example.springboot;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;


import org.springframework.beans.factory.annotation.Autowired;


@RestController
public class LocationController {

	@Autowired
  	private LocationRepository repository;

	@GetMapping("/location/add-new")
	public String addNewLocation(@RequestParam String location) {
		repository.save(new Location(location));

		return "added";
	}

}