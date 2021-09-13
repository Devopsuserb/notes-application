package com.example.notesapplication.controller;

import com.example.notesapplication.model.NotesDTO;
import com.example.notesapplication.model.UserDTO;
import com.example.notesapplication.repository.UserRepository;
import com.example.notesapplication.service.NotesService;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@Log4j2
public class NotesController {
    private static final Logger LOGGER = LogManager.getLogger(NotesController.class);

    @Autowired
    private NotesService service;

    @PostMapping(path = "/getNotes", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<NotesDTO>> getNotesForUser(HttpServletRequest request) {
        try {
            return service.getNotes(request.getUserPrincipal().getName());
        } catch (Exception ex) {
            LOGGER.error(" Exception occurred with : {}", ex.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/addNotes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserDTO> addNotesForUser(@RequestBody String notesText, HttpServletRequest request) {
        try {
            return notesText.length() < 50 ? service.addNotes(request.getUserPrincipal().getName(), notesText) :
                    new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            LOGGER.error(" Exception occurred with : {}", ex.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/updateNotes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<NotesDTO> updateNotesForUser(@RequestBody NotesDTO notes, HttpServletRequest request) {
        try {
            return notes.getNotesText().length() < 50 ? service.updateNotes(request.getUserPrincipal().getName(), notes) :
                    new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            LOGGER.error(" Exception occurred with : {}", ex.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping(path = "/getUserDetails", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserDTO> getUser(HttpServletRequest request) {
        try {
            return service.getUserDetails(request.getUserPrincipal().getName());
        } catch (Exception ex) {
            LOGGER.error(" Exception occurred with : {}", ex.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
