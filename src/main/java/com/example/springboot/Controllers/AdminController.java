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
public class AdminController {

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.pass}")
    private String adminPass;

    @Value("${admin.token}")
    private String adminToken;

	@GetMapping("/login")
	public JSONObject login(@RequestParam String email, @RequestParam String pass) {
		JSONObject res=new JSONObject();

        if(adminEmail.equals(email) && adminPass.equals(pass)){
            JSONObject data=new JSONObject();
            data.put("token", adminToken);

            res.put("message","Successfully Logged In");
            res.put("data", data);
			res.put("status",true);
        }
		else {
            res.put("message","Invalid credentials");
			res.put("status",false); 
        }
 
		return res;
	}

}