package com.example.notesapplication.dao;

import com.example.notesapplication.model.NotesDTO;
import com.example.notesapplication.model.UserDTO;
import com.example.notesapplication.repository.NotesRepository;
import com.example.notesapplication.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class RepositoryTest {

    @Autowired
    NotesRepository notesRepository;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setup() {
        notesRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void insertNotes_HappyPath() {
        NotesDTO note = new NotesDTO(new UserDTO("jane.doe@gmail.com", "password"), "testNotes");
        notesRepository.save(note);
        NotesDTO actual = notesRepository.getById(note.getId());
        assertThat(actual).isEqualTo(note);
        assertThat(userRepository.findById(actual.getUser().getEmailAddress()).get().getEmailAddress()).isEqualTo("jane.doe@gmail.com");
    }

    @Test
    public void selectUserByEmailAddress_HappyPath() {
        userRepository.save(new UserDTO("jane.doe@gmail.com", "password"));
        Optional<UserDTO> user = userRepository.findById("jane.doe@gmail.com");
        assertThat(user.isPresent()).isTrue();
        assertThat(user.get().getEmailAddress()).isEqualTo("jane.doe@gmail.com");
    }

    @Test
    public void deleteNotes_HappyPath() {
        NotesDTO note = new NotesDTO(new UserDTO("jane.doe@gmail.com", "password"), "testNotes");
        notesRepository.save(note);
        assertThat(notesRepository.findAll().size()).isNotZero();
        notesRepository.delete(note);
        assertThat(notesRepository.findAll().size()).isZero();
    }
}
