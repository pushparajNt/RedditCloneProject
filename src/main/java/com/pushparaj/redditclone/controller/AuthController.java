package com.pushparaj.redditclone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pushparaj.redditclone.dto.RegisterRequest;
import com.pushparaj.redditclone.model.LoginRequest;
import com.pushparaj.redditclone.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private AuthService authService;

	@PostMapping("/signup")
	public ResponseEntity<String> signup( @RequestBody RegisterRequest registerRequest)
	{
		authService.signup(registerRequest);
		return new ResponseEntity<>("User Registration successfull", HttpStatus.OK);
	}
	
	@GetMapping("/accountverification/{token}")
	public ResponseEntity<String> verifyUser(@PathVariable String token)
	{
		authService.verifyUser(token);
		return new ResponseEntity<>("User Activated Successfully",HttpStatus.OK);
		
	}
	@PostMapping("/login")
	public void login(@RequestBody LoginRequest loginRequest)
	{
		authService.login(loginRequest);
	}

}
