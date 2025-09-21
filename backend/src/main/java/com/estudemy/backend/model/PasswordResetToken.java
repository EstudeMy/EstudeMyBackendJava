package com.estudemy.backend.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Objects;

@Setter
@Getter
@Document(collection = "password_reset_tokens")
public class PasswordResetToken {

    // Getters e setters
    @Id
    private String id;

    private String usuarioEmail;   // E-mail do usuário dono do token
    private String tokenHash;      // Token hash (não armazenar o token puro)
    private Instant expiration;    // Expiração do token (UTC)

    public PasswordResetToken() {}

    // Construtor completo
    public PasswordResetToken(String usuarioEmail, String tokenHash, Instant expiration) {
        this.usuarioEmail = usuarioEmail;
        this.tokenHash = tokenHash;
        this.expiration = expiration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PasswordResetToken that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "PasswordResetToken{" +
                "id='" + id + '\'' +
                ", usuarioEmail='" + usuarioEmail + '\'' +
                ", expiration=" + expiration +
                '}';
    }
}


