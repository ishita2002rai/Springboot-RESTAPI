package com.example.green;
//AuthController.java

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

@Autowired
 private final AuthenticationManager authenticationManager;
 private final JwtTokenUtil jwtTokenUtil;
 private final UserDetailsService userDetailsService;
 private final UserRepository userRepository;
 private final BCryptPasswordEncoder passwordEncoder;

 @Autowired
 public AuthController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil,
                       UserDetailsService userDetailsService, UserRepository userRepository,
                       BCryptPasswordEncoder passwordEncoder) {
     this.authenticationManager = authenticationManager;
     this.jwtTokenUtil = jwtTokenUtil;
     this.userDetailsService = userDetailsService;
     this.userRepository = userRepository;
     this.passwordEncoder = passwordEncoder;
 }

 @PostMapping("/login")
 public ResponseEntity<?> login(@RequestBody User user) {
     try {
         authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                 user.getUsername(), user.getPassword()));
     } catch (AuthenticationException e) {
         return ResponseEntity.status(401).body("Invalid username or password");
     }

     UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
     String token = jwtTokenUtil.generateToken(userDetails);

     return ResponseEntity.ok(token);
 }

 @PostMapping("/signup")
 public ResponseEntity<String> signup(@RequestBody User user) {
     User existingUser = userRepository.findByUsername(user.getUsername());
     if (existingUser != null) {
         return ResponseEntity.badRequest().body("Username already exists");
     }

     user.setPassword(passwordEncoder.encode(user.getPassword()));
     userRepository.save(user);

     return ResponseEntity.ok("User registered successfully");
 }
}
