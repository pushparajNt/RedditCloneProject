package com.pushparaj.redditclone.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pushparaj.redditclone.dto.RegisterRequest;
import com.pushparaj.redditclone.exception.SpringRedditException;
import com.pushparaj.redditclone.model.LoginRequest;
import com.pushparaj.redditclone.model.NotificationEmail;
import com.pushparaj.redditclone.model.User;
import com.pushparaj.redditclone.model.VerificationToken;
import com.pushparaj.redditclone.repositories.UserRepository;
import com.pushparaj.redditclone.repositories.VerificationTokenRepository;

@Service
public class AuthService {
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private VerificationTokenRepository verificationTokenRepository;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Transactional
	public void signup(RegisterRequest registerRequest)
	{
		User user=new User();
		user.setEmail(registerRequest.getEmail());
		user.setUserName(registerRequest.getUsername());
		user.setPassword(encoder.encode(registerRequest.getPassword()));
		user.setCreated(Instant.now());
		user.setEnabled(false);
		userRepository.save(user);
		String token=generateVerificationToken(user);
		
		mailService.sendMail(new NotificationEmail("Please activate your account", 
				user.getEmail(), "Thank you for signing up to spring reddit,"
						+ "Please click on the below link to activate your account"
						+ "http://localhost:8080/api/auth/accountverification/"+token));
	}
	@Transactional
	public String generateVerificationToken(User user)
	{
		String token=UUID.randomUUID().toString();
		VerificationToken verificationToken=new VerificationToken();
		verificationToken.setToken(token);
		verificationToken.setUser(user);
		verificationTokenRepository.save(verificationToken);
		return token;
	}

	public void verifyUser(String token) {
		
	 Optional<VerificationToken> verificationToken=	
			 verificationTokenRepository.findByToken(token);
	verificationToken.orElseThrow(()->new SpringRedditException("Invalid token"));
	fetchUserAndEnable(verificationToken.get());
	}

	@Transactional
	private void fetchUserAndEnable(VerificationToken verificationToken) {
		String username=verificationToken.getUser().getUserName();
		User user=userRepository.findByUserName(username)
		.orElseThrow(()->new SpringRedditException("User not found-"+username));
		user.setEnabled(true);
		userRepository.save(user);
	}
	public void login(LoginRequest loginRequest) {
		
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));
		
	}
}
