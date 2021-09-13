package com.example.notesapplication.controller;

import com.example.notesapplication.model.UserDTO;
import com.example.notesapplication.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@Log4j2
public class NotesController {
    private static final Logger LOGGER = LogManager.getLogger(NotesController.class);

    @Autowired
    private UserRepository repository;

    @GetMapping(path = "/getUserDetails", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserDTO> getUser(HttpServletRequest request) {
        try {
            String userEmailId = request.getUserPrincipal().getName();
            LOGGER.debug("Logged in person's email Id {}", userEmailId);
            Optional<UserDTO> user = repository.findById(userEmailId);
            if (user.isPresent()) {
                LOGGER.info("Successfully retrieved user details for {}", userEmailId);
                return new ResponseEntity<>(user.get(), HttpStatus.OK);
            } else {
                LOGGER.error("Could not find notes for : {}", userEmailId);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            LOGGER.error(" Exception occurred with : {}", ex.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
