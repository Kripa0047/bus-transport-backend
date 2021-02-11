package com.example.springboot;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class LocationController {

	@RequestMapping("/location")
	public String getlocation() {
		return "Kolkata";
	}

    @RequestMapping("/date")
	public String getDate() {
		return "23-03-2021";
	}

}
