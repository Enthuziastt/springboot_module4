package com.example.springboot_module4.demo.services;

import com.example.springboot_module4.demo.entities.Session;
import com.example.springboot_module4.demo.entities.User;
import com.example.springboot_module4.demo.repositories.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service @RequiredArgsConstructor public class SessionService {

    private final SessionRepository sessionRepository;
    private final int SESSION_LIMIT = 2;

    public void generateNewSession(User user, String refreshToken) {
        List<Session> userSessions = sessionRepository.findByUser(user);
        if (userSessions.size() >= SESSION_LIMIT) {
            userSessions.sort(Comparator.comparing(Session::getLastUsedAt));

            Session sessionToDiscard = userSessions.get(0);
            sessionRepository.delete(sessionToDiscard);
            Session newSession = Session.builder().user(user).refreshToken(refreshToken).build();
            sessionRepository.save(newSession);
        } else {
            Session newSsession = Session.builder().user(user).refreshToken(refreshToken).build();
            sessionRepository.save(newSsession);
        }

    }

    public void validateSession(String refreshToken) {
        Session storedSession = sessionRepository
                .findByRefreshToken(refreshToken)
                .orElseThrow(() -> new SessionAuthenticationException(
                        "Session not found for refreshToken: " + refreshToken));
        storedSession.setLastUsedAt(LocalDate.now());
        sessionRepository.save(storedSession);
    }
}
