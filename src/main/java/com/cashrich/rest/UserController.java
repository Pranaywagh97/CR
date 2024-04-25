package com.cashrich.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cashrich.binding.UserLogin;
import com.cashrich.binding.UserSignUp;
import com.cashrich.binding.UserUpdate;
import com.cashrich.serviceImpl.UserServiceImpl;

import lombok.Data;

@RestController
@RequestMapping("/cr")
@Data
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;


	@PostMapping("/signup")
	public ResponseEntity<String> signUpUser(@RequestBody UserSignUp userSignUp) {
		String status = userServiceImpl.registerUser(userSignUp);
		return new ResponseEntity<String>(status, HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<String> loginUser(@RequestBody UserLogin userLogin) {
		String user = userServiceImpl.loginUser(userLogin);
		return new ResponseEntity<String>(user, HttpStatus.OK);
	    
	}

	@PutMapping("/update/{userName}")
	public ResponseEntity<String> updateUser(@PathVariable String userName,@RequestBody UserUpdate userUpdate) {
	 
	    
	    if (userName == null) {
	        return new ResponseEntity<>("Username not provided", HttpStatus.BAD_REQUEST);
	    }
	    String status = userServiceImpl.updateUser(userName, userUpdate);
	    return new ResponseEntity<>(status, HttpStatus.OK);
	}
	
	  @GetMapping("/fetch/{userName}")
	    public ResponseEntity<String> fetchAndStoreCoinData(@PathVariable String userName) {
	        String status = userServiceImpl.fetchAndStoreCoinData(userName);
	        return new ResponseEntity<>(status, HttpStatus.OK);
	    }


}