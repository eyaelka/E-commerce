package org.sid.springbootecommerce.service;


import org.sid.springbootecommerce.Repository.UserRepository;
import org.sid.springbootecommerce.config.JwtUtil;
import org.sid.springbootecommerce.dto.AuthenticationRequest;
import org.sid.springbootecommerce.dto.AuthenticationResponse;
import org.sid.springbootecommerce.entities.MyUserDetails;
import org.sid.springbootecommerce.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtUtil jwtTokenUtil;

  @Autowired
  private MyUserDetailsService userDetailsService;

  @Transactional

  public ResponseEntity signup(User user) {
      user.setRoles("client");
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setActive(true);
    userRepository.save(user) ;
    final MyUserDetails userDetails=userDetailsService.loadUserByUsername(user.getEmail());
    final String jwt=jwtTokenUtil.generateToken(userDetails);
    return ResponseEntity.ok(new AuthenticationResponse(jwt, user.getId(), user.getRoles()));
  }

  public ResponseEntity<?> login(AuthenticationRequest authenticationRequest) throws Exception {
    User user=userRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow(IllegalArgumentException::new);
    try {
      Authentication a=authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
    } catch(BadCredentialsException e) {
      throw new Exception("Incorrect username or password", e);
    }
    final MyUserDetails userDetails=userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
    final String jwt=jwtTokenUtil.generateToken(userDetails);
    return ResponseEntity.ok(new AuthenticationResponse(jwt, user.getId(), user.getRoles()));
  }
}
