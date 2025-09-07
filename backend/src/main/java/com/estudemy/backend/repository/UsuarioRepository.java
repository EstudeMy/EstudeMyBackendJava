package com.estudemy.backend.repository;

import com.estudemy.backend.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    // Consulta personalizada para buscar por e-mail
    Optional<Usuario> findByUsuarioEmail(String usuarioEmail);

    // Consulta personalizada para buscar por username
    Optional<Usuario> findByUsuarioUserName(String usuarioUserName);
}
