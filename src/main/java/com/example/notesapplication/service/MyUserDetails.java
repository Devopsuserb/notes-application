package com.example.notesapplication.service;

import com.example.notesapplication.model.UserDTO;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;
import java.util.Collections;

@Log4j2
public class MyUserDetails implements UserDetails {
    private static final Logger LOGGER = LogManager.getLogger(UserDetailsServiceImpl.class);

    private final UserDTO user;

    public MyUserDetails(UserDTO user) {
        LOGGER.debug("Successfully retrieved User details from DAO : {}", user);
        this.user = user;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("user");
        return Collections.singletonList(authority);

    }

    @Override
    public String getPassword() {
        String encode = new BCryptPasswordEncoder().encode(this.user.getPassword());
        LOGGER.debug("encoded password : {} for the password {}", encode, this.user.getPassword());
        return encode;
    }

    @Override
    public String getUsername() {
        return this.user.getEmailAddress();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
