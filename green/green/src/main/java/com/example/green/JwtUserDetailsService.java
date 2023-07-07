package com.example.green;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User.UserBuilder;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

 private final UserRepository userRepository;

 @Autowired
 public JwtUserDetailsService(UserRepository userRepository) {
     this.userRepository = userRepository;
 }

 @Override
 public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
     User user = userRepository.findByUsername(username);
     if (user == null) {
         throw new UsernameNotFoundException("User not found with username: " + username);
     }

     UserBuilder builder = org.springframework.security.core.userdetails.User.withUsername(username);
     builder.password(user.getPassword());
     builder.roles("USER");

     return builder.build();
 }
}
