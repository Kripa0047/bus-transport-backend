package com.example.springboot;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;

@RestController
public class LocationController {

	@Autowired
  	private LocationRepository repository;

	@Value("${admin.token}")
    private String adminToken;

	@GetMapping("/location/add-new")
	public JSONObject addNewLocation(@RequestParam String location, @RequestParam String token) {
		JSONObject res=new JSONObject();
		try	{
			if(adminToken.equals(token)){
				repository.save(new Location(location));
				res.put("message","Location added");
				res.put("status",true);
			}else {
				res.put("message","Invalid token");
				res.put("status",false);
			}
        }  
        catch(ArithmeticException e)	{  
            System.out.println(e);
			res.put("message","Server error");
			res.put("status",false); 
        }   
 
		return res;
	}

	@GetMapping("/location")
	public JSONObject getAllLocation() {
		List<JSONObject> locations = new ArrayList<>();
		JSONObject res=new JSONObject();

		try	{
			for (Location location : repository.findAll()) {
				JSONObject loc = new JSONObject();
				loc.put("id", location.id);
				loc.put("location", location.name);
				locations.add(loc);
			}
			JSONObject data=new JSONObject();
			data.put("locations",locations);
			res.put("message","Location Fetched");
			res.put("status",true);
			res.put("data",data);
        }  
        catch(ArithmeticException e)	{  
            System.out.println(e);
			res.put("message","Server error");
			res.put("status",false);
        }   

		return res;
	}

	@GetMapping("/location/delete")
	public JSONObject deleteLocation(@RequestParam String id, @RequestParam String token) {
		JSONObject res=new JSONObject();

		try	{
			if(adminToken.equals(token)){
                Location location = new Location();
                location.setId(id);
				repository.delete(location);
				res.put("message","Location deleted");
				res.put("status",true);
			}else {
				res.put("message","Invalid token");
				res.put("status",false);
			}
        }  
        catch(ArithmeticException e)	{  
            System.out.println(e);
			res.put("message","Server error");
			res.put("status",false);
        }   

		return res;
	}

}