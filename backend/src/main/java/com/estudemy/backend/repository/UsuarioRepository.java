package com.estudemy.backend.repository;

import com.estudemy.backend.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    Optional<Usuario> findByUsuarioEmail(String usuarioEmail);
    Optional<Usuario> findByUsuarioUserName(String usuarioUserName);
}
