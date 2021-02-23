package com.example.springboot;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@RestController
public class BusController {

	@Autowired
  	private BusRepository repository;

    @Autowired
    MongoOperations mongoOperations;

	@Value("${admin.token}")
    private String adminToken;

	@GetMapping("/bus/add-new")
	public JSONObject addNewBus(@RequestParam String busNo, @RequestParam String description, @RequestParam String token) {
		JSONObject res=new JSONObject();
		try	{
			if(adminToken.equals(token)){
				Bus bus = repository.save(new Bus(busNo, description, new ArrayList<>(), new ArrayList<>() ));
                JSONObject data = new JSONObject();
                data.put("id", bus.id);
				res.put("message","Bus added");
                res.put("data", data);
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

    @GetMapping("/bus/delete")
	public JSONObject deleteBus(@RequestParam String id, @RequestParam String token) {
		JSONObject res=new JSONObject();
		try	{
			if(adminToken.equals(token)){
                Bus bus = new Bus();
                bus.setId(id);
				repository.delete(bus);
				res.put("message","Bus deleted");
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

    @GetMapping("/bus/update")
	public JSONObject updateBus(
        @RequestParam String id, 
        @RequestParam(required = false) String busNumber,
        @RequestParam(required = false) String description, 
        @RequestParam String token
    ) {
		JSONObject res=new JSONObject();
		try	{
			if(adminToken.equals(token)){
                Bus bus = mongoOperations.findOne(query(where("_id").is(id)), Bus.class,"Bus");
                if(bus != null){
                    if(busNumber != null) bus.setBusNumber(busNumber);
                    if(description != null) bus.setDescription(description);
				    repository.save(bus);
				    res.put("message","Bus updated");
				    res.put("status",true);
                }
                else{
                    res.put("message","Bus not found");
				    res.put("status",false);
                }
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

    @PostMapping("/bus/update/days")
	public JSONObject updateBusDays(
        @RequestParam String id, 
        @RequestBody List<String> days,
        @RequestParam String token
    ) {
		JSONObject res=new JSONObject();
		try	{
			if(adminToken.equals(token)){
                Bus bus = mongoOperations.findOne(query(where("_id").is(id)), Bus.class,"Bus");
                if(bus != null){
                    bus.setDays(days);
				    repository.save(bus);
				    res.put("message","Days updated");
				    res.put("status",true);
                }
                else{
                    res.put("message","Bus not found");
				    res.put("status",false);
                }
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

    @PostMapping("/bus/update/stops")
	public JSONObject updateBusStops(
        @RequestParam String id, 
        @RequestBody List<Map<String,String>> stops,
        @RequestParam String token
    ) {
		JSONObject res=new JSONObject();
		try	{
			if(adminToken.equals(token)){
                Bus bus = mongoOperations.findOne(query(where("_id").is(id)), Bus.class,"Bus");
                if(bus != null){
                    bus.setStops(stops);
				    repository.save(bus);
				    res.put("message","Stops updated");
				    res.put("status",true);
                }
                else{
                    res.put("message","Bus not found");
				    res.put("status",false);
                }
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

    @GetMapping("/bus")
	public JSONObject getAllBus() {
		JSONObject res=new JSONObject();
        List<JSONObject> buses = new ArrayList<>();

		try	{
            for (Bus bus : repository.findAll()) {
                JSONObject jsonBus=new JSONObject();
                jsonBus.put("id", bus.id);
                jsonBus.put("busNumber", bus.busNumber);
                jsonBus.put("description", bus.description);
                jsonBus.put("days", bus.days);
                jsonBus.put("stops", bus.stops);
				buses.add(jsonBus);
			}
            JSONObject data=new JSONObject();
            data.put("buses", buses);
		    res.put("message","Buses fetched");
            res.put("data", data);
		    res.put("status",true);
        }  
        catch(ArithmeticException e)	{  
            System.out.println(e);
			res.put("message","Server error");
			res.put("status",false); 
        }   
 
		return res;
	}

    public boolean containDay(String day, List<String> days) {
        for(String item : days){
            if(item.equals(day)) return true;
        }
        return false;
    }

    public boolean containStops(String from, String to, List<Map<String, String>> stops) {
        boolean isFrom = false;
        for(Map<String, String> item : stops){
            if(item.get("location").equals(from)) isFrom = true;
            else if(item.get("location").equals(to) && isFrom) return true;
        }
        return false;
    }

    @GetMapping("/bus/filter")
	public JSONObject getBusFilter(@RequestParam String from, @RequestParam String to, @RequestParam String day) {
		JSONObject res=new JSONObject();
        List<JSONObject> buses = new ArrayList<>();

		try	{
            for (Bus bus : repository.findAll()) {
                boolean isDay = containDay(day, bus.days);
                boolean isStop = containStops(from, to, bus.stops);
                if(isDay && isStop) {
                    JSONObject jsonBus=new JSONObject();
                    jsonBus.put("id", bus.id);
                    jsonBus.put("busNumber", bus.busNumber);
                    jsonBus.put("description", bus.description);
                    jsonBus.put("days", bus.days);
                    jsonBus.put("stops", bus.stops);
				    buses.add(jsonBus);
                }
			}
            JSONObject data=new JSONObject();
            data.put("buses", buses);
		    res.put("message","Buses fetched");
            res.put("data", data);
		    res.put("status",true);
        }  
        catch(ArithmeticException e)	{  
            System.out.println(e);
			res.put("message","Server error");
			res.put("status",false); 
        }   
 
		return res;
	}

}