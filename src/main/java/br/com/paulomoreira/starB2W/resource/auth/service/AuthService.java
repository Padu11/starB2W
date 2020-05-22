package br.com.paulomoreira.starB2W.resource.auth.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.paulomoreira.starB2W.model.User;
import br.com.paulomoreira.starB2W.resource.auth.UserRepository;
import br.com.paulomoreira.starB2W.util.Constants;


@Service
public class AuthService implements UserDetailsService {
	
	@Autowired
	UserRepository userRepositoy;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		
		Optional<User> user = userRepositoy.findByEmail(username);
		
		if(user.isPresent()) {
			
			return user.get();
			
		}
		
		
		throw new UsernameNotFoundException(Constants.USER_PASSWORD_INVALID);
		
	}

}
