package com.alataf.session.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_session")
@Data @RequiredArgsConstructor
public class UserSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String sessionId;

    private LocalDateTime loginTime;

    private LocalDateTime expiryTime;

    private boolean active;
}

