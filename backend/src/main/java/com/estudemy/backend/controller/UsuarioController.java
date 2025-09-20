package com.estudemy.backend.controller;

import com.estudemy.backend.model.Usuario;
import com.estudemy.backend.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // ✅ Cadastrar usuário com verificação de email e username duplicado
    @PostMapping
    public ResponseEntity<?> cadastrarUsuario(@Valid @RequestBody Usuario usuario) {

        // Verifica email
        Optional<Usuario> emailExistente = usuarioRepository.findByUsuarioEmail(usuario.getUsuarioEmail());
        if (emailExistente.isPresent()) {
            return ResponseEntity.badRequest().body(
                    new MensagemErro("E-mail já cadastrado!")
            );
        }

        // Verifica username
        Optional<Usuario> usernameExistente = usuarioRepository.findByUsuarioUserName(usuario.getUsuarioUserName());
        if (usernameExistente.isPresent()) {
            return ResponseEntity.badRequest().body(
                    new MensagemErro("Username já cadastrado!")
            );
        }

        // Salva usuário se não houver duplicata
        Usuario salvo = usuarioRepository.save(usuario);
        return ResponseEntity.ok(salvo);
    }

    // ✅ Listar todos os usuários
    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    // ✅ Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable String id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Atualizar usuário
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable String id,
                                                    @RequestBody Usuario usuarioAtualizado) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuario.setUsuarioNome(usuarioAtualizado.getUsuarioNome());
                    usuario.setUsuarioEmail(usuarioAtualizado.getUsuarioEmail());
                    usuario.setUsuarioDataNascimento(usuarioAtualizado.getUsuarioDataNascimento());
                    usuario.setUsuarioTipo(usuarioAtualizado.getUsuarioTipo());
                    usuario.setUsuarioAutobiografia(usuarioAtualizado.getUsuarioAutobiografia());
                    usuario.setUsuarioSenha(usuarioAtualizado.getUsuarioSenha());
                    usuario.setUsuarioAvatar(usuarioAtualizado.getUsuarioAvatar());
                    usuario.setUsuarioPontuacao(usuarioAtualizado.getUsuarioPontuacao());
                    usuario.setUsuarioUserName(usuarioAtualizado.getUsuarioUserName());
                    Usuario atualizado = usuarioRepository.save(usuario);
                    return ResponseEntity.ok(atualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Deletar usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable String id) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuarioRepository.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Classe auxiliar para mensagem de erro
    static class MensagemErro {
        public String mensagem;
        public MensagemErro(String mensagem) {
            this.mensagem = mensagem;
        }
    }
}



