package com.example.notesapplication.service;

import com.example.notesapplication.model.UserDTO;
import com.example.notesapplication.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@Log4j2
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger LOGGER = LogManager.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {
        LOGGER.debug("The email address provided here : {}",emailAddress );
        Optional<UserDTO> user = userRepository.findById(emailAddress);

        LOGGER.debug("User Repository returns : {}", user);
        user.orElseThrow(() -> new UsernameNotFoundException("No user with the userName :" + emailAddress));

        return new MyUserDetails(user.get());
    }
}
