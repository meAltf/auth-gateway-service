package com.alataf.session.Service.impl;

import com.alataf.session.DTO.LoginRequest;
import com.alataf.session.Entity.User;
import com.alataf.session.Entity.UserSession;
import com.alataf.session.Exception.ConcurrentLoginException;
import com.alataf.session.Repository.UserRepository;
import com.alataf.session.Repository.UserSessionRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl {

    private final UserRepository userRepository;
    private final UserSessionRepository sessionRepository;
    private final PasswordEncoder passwordEncoder;

    public void login(LoginRequest request, HttpSession httpSession) {

        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // Concurrent login check
        isConcurrentLogin(user);

        // Create session
        httpSession.setAttribute("USER_ID", user.getId());

        UserSession userSession = new UserSession();
        userSession.setUserId(user.getId());
        userSession.setSessionId(httpSession.getId());
        userSession.setLoginTime(LocalDateTime.now());
        userSession.setExpiryTime(LocalDateTime.now().plusMinutes(15));
        userSession.setActive(true);

        sessionRepository.save(userSession);
    }

    private void isConcurrentLogin(User user) {
        sessionRepository.findByUserIdAndActiveTrue(user.getId())
                .ifPresent(s -> {
                    throw new ConcurrentLoginException("Your session has expired due to multiple concurrent login");
                });
    }

    public void logout(HttpSession session) {

        sessionRepository.findBySessionId(session.getId())
                .ifPresent(userSession -> {
                    userSession.setActive(false);
                    sessionRepository.save(userSession);
                });

        session.invalidate();
    }
}

