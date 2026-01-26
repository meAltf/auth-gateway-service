package com.alataf.session.Repository;

import com.alataf.session.Entity.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, Long> {

    Optional<UserSession> findByUserIdAndActiveTrue(Long userId);

    Optional<UserSession> findBySessionId(String sessionId);
}

