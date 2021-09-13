package com.example.notesapplication.model;

import com.example.notesapplication.exception.ServerException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "notes")
@NoArgsConstructor
@AllArgsConstructor
public class NotesDTO {

    public NotesDTO(UserDTO user, String notesText) {
        this.user = user;
        if (notesText.length() < 50) {
            this.notesText = notesText;
        } else
            throw new ServerException();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notesId", updatable = false, nullable = false)
    @Getter
    private Long id;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "email")
    @JsonIgnore
    @Getter
    private UserDTO user;

    @Column(name = "text", nullable = false, length = 50)
    @Getter
    @Setter
    private String notesText;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP")
    @CreationTimestamp
    @Getter
    private LocalDate createdAt;

    @Column(name = "updated_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP")
    @UpdateTimestamp
    @Getter
    private LocalDate updatedAt;

    @Override
    public String toString() {
        return "NotesDTO{" +
                "id=" + id +
                ", user=" + user.getEmailAddress() +
                ", notesText='" + notesText + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}

