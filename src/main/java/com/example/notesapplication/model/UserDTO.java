package com.example.notesapplication.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
public class UserDTO {

    @Id
    @Column(name = "email", unique = true, updatable = false, nullable = false, length = 50)
    @Getter
    private String emailAddress;

    @Column(name = "password", nullable = false)
    @Getter
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @Getter
    @JsonIgnore
    private List<NotesDTO> notes;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP")
    @CreationTimestamp
    @Getter
    private LocalDate createdAt;

    @Column(name = "updated_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP")
    @UpdateTimestamp
    @Getter
    private LocalDate updatedAt;

    public UserDTO(String emailAddress, String password) {
        if (password.length() < 8) {
            throw new IllegalArgumentException();
        }
        this.emailAddress = emailAddress;
        this.password = password;
        this.notes = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                " emailAddress='" + emailAddress + '\'' +
                ", password='" + password + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
