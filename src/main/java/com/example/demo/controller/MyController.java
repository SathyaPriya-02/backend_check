package com.example.demo.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.SimCard;
import com.example.demo.entity.User;
import com.example.demo.repository.RechargePaymentRepo;
import com.example.demo.repository.SimCardRepo;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.SimCardService;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/api/users")
public class MyController {

    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private SimCardService simCardService;

    @Autowired
    private SimCardRepo simRepo;
    
    @Autowired
    private RechargePaymentRepo rechargeRepo;
    
    @Autowired
    private UserService uService ;
    
    
    @PostMapping("/newSimCard")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<String> createUser(@RequestBody Map<String, Object> requestData) {
        Map<String, Object> userMap = (Map<String, Object>) requestData.get("user");
        List<String> locations = (List<String>) requestData.get("location");
        System.out.println(userMap);
        System.out.println(locations);
        // Extract user data
        String emailid = (String) userMap.get("emailid");
        String firstName = (String) userMap.get("firstName");
        String lastName = (String) userMap.get("lastName");
        String dob = (String) userMap.get("dob");
        String gender = (String) userMap.get("gender");
        int simCount = (int) userMap.get("simCount");

        
        
        User user = new User();
        user.setEmailid(emailid);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setDob(dob);
        user.setGender(gender);
        user.setSimCount(simCount);
        userRepo.save(user);
        
        
        for (String location : locations) {
            SimCard simCard = new SimCard();
            String phonenumber = uService.generateRandomPhoneNumber();
            String simnumber = uService.generateSimCardNumber();
            simCard.setPhonenumber(phonenumber);
            simCard.setSimcardnumber(simnumber);
            simCard.setUser(user);
            simCard.setLocation(location);
            simCard.setStatus("inactive");
            simRepo.save(simCard);
            uService.sendMail(emailid, phonenumber, simnumber, location);
        }
        
        
        return ResponseEntity.ok("{\"message\": \"added\"}");
    }

    @PostMapping("/activate/{phonenumber}")
	 @CrossOrigin(origins="http://localhost:4200")
	 public ResponseEntity<String> activateUser(@PathVariable String phonenumber,@RequestBody Map<String, String> requestBody) {
	        System.out.println("Im there "+phonenumber);
		 	String planname = requestBody.get("planname");
	        String validity = requestBody.get("validity");
	        String price = requestBody.get("price");
	        String name = requestBody.get("userName");
	        String emailid = requestBody.get("emailid");
	        String simnumber = requestBody.get("simnumber");
	        int validityAsInt = Integer.parseInt(validity);
	        int priceAsInt = Integer.parseInt(price);
	        System.out.println("hi"+name);
	        
	        LocalDate currentDate = LocalDate.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Change the pattern as needed
	        // Format the LocalDate as a string
	        String formattedDate = currentDate.format(formatter);
	        String transactionId = uService.generateTransactionId(phonenumber);
	        System.out.println("active "+planname);
	        System.out.println(validityAsInt);
	        System.out.println(priceAsInt);
	        System.out.println(formattedDate);
	       
	        
	        boolean activationSuccessful = uService.activateUser(simnumber,phonenumber,name,emailid,planname,validityAsInt,priceAsInt,formattedDate,transactionId );

	        if (activationSuccessful) {
	            return ResponseEntity.ok(transactionId);
	        } else {
	            return ResponseEntity.badRequest().body("Invalid activation token");
	        }
	    }
    
    @GetMapping("/detail/{phoneNumber}")
	@CrossOrigin(origins="http://localhost:4200")
	public ResponseEntity<SimCard> getUserDetails(@PathVariable String phoneNumber)
	 {
		 System.out.println(phoneNumber);
	        // Assuming you have a UserRepository to fetch user details
	        SimCard user = simRepo.findByPhonenumber(phoneNumber);
	        
	        System.out.println("details "+user);
	        CacheControl cacheControl = CacheControl.noStore().mustRevalidate();
	        if (user != null) {
	        	System.out.println(user);
	        	return ResponseEntity.ok()
	                    .cacheControl(cacheControl)
	                    .body(user);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
    
    
    
 }
