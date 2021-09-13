package com.example.notesapplication.controller;

import com.example.notesapplication.model.UserDTO;
import com.example.notesapplication.service.NotesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

import java.security.Principal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NotesControllerTest {

    @Mock
    private NotesService notesService;

    @Mock
    private HttpServletRequest mockRequest;

    @InjectMocks
    private NotesController notesController;

    @Mock
    private Principal principal;

    @Test
    public void getUser_HappyPath() {
        UserDTO expectedUser = new UserDTO("john.doe@gmail.com", "YourPassword");
        ResponseEntity<UserDTO> expected = new ResponseEntity<>(expectedUser,
                HttpStatus.OK);

        when(principal.getName()).thenReturn("john.doe@gmail.com");
        when(mockRequest.getUserPrincipal()).thenReturn(principal);
        when(this.notesService.getUserDetails("john.doe@gmail.com")).thenReturn(expected);

        ResponseEntity<UserDTO> response = notesController.getUser(mockRequest);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        UserDTO actual = response.getBody();
        assertThat(actual).isEqualTo(expectedUser);
    }

    @Test
    public void getUser_UserDoesNotExist() {
        UserDTO expectedUser = new UserDTO("john.doe@gmail.com", "YourPassword");
        ResponseEntity<UserDTO> expected = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        when(principal.getName()).thenReturn("john.doe@gmail.com");
        when(mockRequest.getUserPrincipal()).thenReturn(principal);
        when(this.notesService.getUserDetails("john.doe@gmail.com")).thenReturn(expected);

        ResponseEntity<UserDTO> response = notesController.getUser(mockRequest);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        assertThat(response.getBody()).isNull();
    }


}
