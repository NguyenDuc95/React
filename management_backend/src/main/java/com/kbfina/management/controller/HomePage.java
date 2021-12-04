package com.kbfina.management.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import com.kbfina.management.entities.User;
import com.kbfina.management.requests.AuthenticationRequest;
import com.kbfina.management.responses.LoginResponse;
import com.kbfina.management.config.auth.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class HomePage {

	private final Logger logger = LogManager.getLogger(HomePage.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	JWTTokenHelper jWTTokenHelper;
    
    @GetMapping("/")
    public String homepage(){
		logger.debug("show homepage");
        return "hello world";
    }


	 @CrossOrigin(origins = "http://172.30.222.3:3000")
	@PostMapping("/api/login")
	public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) throws InvalidKeySpecException, NoSuchAlgorithmException, UnsupportedEncodingException {
		logger.debug("REST login username:  " + authenticationRequest.getUserName() + " - Password: " + authenticationRequest.getPassword());;
		final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				authenticationRequest.getUserName(), authenticationRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		if(authentication != null){
			
			logger.error("authentication creditals: " + authentication.toString());
		} else {
			logger.error("authentication is null");
		}
		User user = (User)authentication.getPrincipal();
		String jwtToken = jWTTokenHelper.generateToken(user.getUsername());
		
		LoginResponse response = new LoginResponse();
		response.setToken(jwtToken);

		return ResponseEntity.ok(response);
	}

}
