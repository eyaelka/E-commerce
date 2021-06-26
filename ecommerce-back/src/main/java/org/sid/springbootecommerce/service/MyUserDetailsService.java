package org.sid.springbootecommerce.service;



import org.sid.springbootecommerce.Repository.UserRepository;
import org.sid.springbootecommerce.entities.MyUserDetails;
import org.sid.springbootecommerce.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository ;

    @Override
    public MyUserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(s);
        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + s));
        return user.map(MyUserDetails::new).get();
    }

}
