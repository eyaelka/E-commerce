package org.sid.springbootecommerce.controller;



import org.sid.springbootecommerce.dto.AuthenticationRequest;
import org.sid.springbootecommerce.entities.User;
import org.sid.springbootecommerce.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService ;

    @PostMapping(value = "/signup")
    public ResponseEntity<?> register(@RequestBody User user){
        return this.authService.signup(user) ;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        return this.authService.login(authenticationRequest) ;
    }

}
