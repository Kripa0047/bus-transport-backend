package com.example.springboot;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.json.simple.JSONObject;

@RestController
public class LocationController {

	@Autowired
  	private LocationRepository repository;

	@GetMapping("/location/add-new")
	public String addNewLocation(@RequestParam String location) {
		JSONObject res=new JSONObject();
		try	{
			repository.save(new Location(location));
			res.put("message","Location added");
			res.put("status",true);
        }  
        catch(ArithmeticException e)	{  
            System.out.println(e);
			res.put("message","Server error");
			res.put("status",false); 
        }   
 
		return res.toJSONString();
	}

	@GetMapping("/location")
	public String getAllLocation() {
		List<String> data = new ArrayList<>();
		JSONObject res=new JSONObject();

		try	{
			for (Location location : repository.findAll()) {
				data.add(location.name);
			}
			res.put("message","Location Fetched");
			res.put("status",true);
			res.put("data",data);
        }  
        catch(ArithmeticException e)	{  
            System.out.println(e);
			res.put("message","Server error");
			res.put("status",false);
        }   

		return res.toString();
	}

}