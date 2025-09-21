package com.estudemy.backend.repository;

import com.estudemy.backend.model.PasswordResetToken;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends MongoRepository<PasswordResetToken, String> {
    Optional<PasswordResetToken> findByTokenHash(String tokenHash);
    void deleteByUsuarioEmail(String usuarioEmail);
}




