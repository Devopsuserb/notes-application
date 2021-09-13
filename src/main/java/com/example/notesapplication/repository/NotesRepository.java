package com.example.notesapplication.repository;

import com.example.notesapplication.model.NotesDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotesRepository extends JpaRepository<NotesDTO, Long> {
}
