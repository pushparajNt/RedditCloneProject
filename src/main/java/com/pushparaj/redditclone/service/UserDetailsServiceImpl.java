package com.pushparaj.redditclone.service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pushparaj.redditclone.model.User;
import com.pushparaj.redditclone.repositories.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl  implements UserDetailsService{

	private final UserRepository userRepository;
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	Optional<User> userOptional=userRepository.findByUserName(username);
	User user=userOptional.orElseThrow(()->new UsernameNotFoundException("No user found:"+username)); 
		return new org.springframework.security
                .core.userdetails.User(user.getUserName(), user.getPassword(),
                user.isEnabled(), true, true,
                true, getAuthorities("USER"));
	}

	 private Collection<? extends GrantedAuthority> getAuthorities(String role) {
	        return Collections.singletonList(new SimpleGrantedAuthority(role));
	    }
}
