package com.example.notesapplication.service;

import com.example.notesapplication.controller.NotesController;
import com.example.notesapplication.model.NotesDTO;
import com.example.notesapplication.model.UserDTO;
import com.example.notesapplication.repository.NotesRepository;
import com.example.notesapplication.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class NotesService {
    private static final Logger LOGGER = LogManager.getLogger(NotesController.class);

    @Autowired
    private UserRepository repository;

    @Autowired
    private NotesRepository notesRepository;

    public ResponseEntity<List<NotesDTO>> getNotes(String userEmailId) {
        LOGGER.debug("Logged in person's email Id {}", userEmailId);
        Optional<UserDTO> user = repository.findById(userEmailId);
        if (user.isPresent()) {
            LOGGER.info("Successfully retrieved notes {} for {}", user.get().getNotes(), userEmailId);
            return new ResponseEntity<>(user.get().getNotes(), HttpStatus.OK);
        } else {
            LOGGER.error("Could not find notes for : {}", userEmailId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<UserDTO> getUserDetails(String userEmailId) {
        LOGGER.debug("Logged in person's email Id {}", userEmailId);
        Optional<UserDTO> user = repository.findById(userEmailId);
        if (user.isPresent()) {
            LOGGER.info("Successfully retrieved user details for {}", userEmailId);
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            LOGGER.error("Could not find notes for : {}", userEmailId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<UserDTO> addNotes(String userEmailId, String notesText) {
        LOGGER.debug("Logged in person's email Id {}", userEmailId);
        Optional<UserDTO> user = repository.findById(userEmailId);
        if (user.isPresent()) {
            LOGGER.info("Successfully retrieved user details for {}", userEmailId);
            new NotesDTO(user.get(),notesText);
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            LOGGER.error("Could not find notes for : {}", userEmailId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<NotesDTO> updateNotes(String userEmailId, NotesDTO notes) {
        if(notesRepository.findById(notes.getId()).isPresent()){
            LOGGER.info("Notes already exists updating it");
            notesRepository.save(notes);
            return new ResponseEntity<>(notes, HttpStatus.OK);
        } else {
            LOGGER.info("Notes doesn't exists creating it");
            UserDTO user = repository.getById(userEmailId);
            NotesDTO newNotes = new NotesDTO(user, notes.getNotesText());
            notesRepository.save(newNotes);
            return new ResponseEntity<>(newNotes, HttpStatus.OK);
        }
    }

    public HttpStatus deleteNotes(NotesDTO notes) {
        if(notesRepository.findById(notes.getId()).isPresent()){
            LOGGER.info("Notes already deleting it");
            notesRepository.delete(notes);
        } else {
            LOGGER.info("Notes doesn't exists");
        }
        return HttpStatus.OK;
    }
}
