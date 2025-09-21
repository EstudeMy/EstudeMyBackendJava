package com.estudemy.backend.controller;

import com.estudemy.backend.model.PasswordResetToken;
import com.estudemy.backend.model.Usuario;
import com.estudemy.backend.repository.UsuarioRepository;
import com.estudemy.backend.repository.PasswordResetTokenRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ==========================
    // CADASTRO
    // ==========================
    @PostMapping
    public ResponseEntity<?> cadastrarUsuario(@Valid @RequestBody Usuario usuario) {
        Optional<Usuario> existente = usuarioRepository.findByUsuarioEmail(usuario.getUsuarioEmail());

        if (existente.isPresent()) {
            return ResponseEntity.badRequest().body(new MensagemErro("E-mail já cadastrado!"));
        }

        usuario.setUsuarioSenha(passwordEncoder.encode(usuario.getUsuarioSenha()));
        Usuario salvo = usuarioRepository.save(usuario);

        return ResponseEntity.ok(salvo);
    }

    // ==========================
    // LISTAR TODOS
    // ==========================
    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    // ==========================
    // BUSCAR POR ID
    // ==========================
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable String id) {
        return usuarioRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ==========================
    // ATUALIZAR
    // ==========================
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable String id, @RequestBody Usuario usuarioAtualizado) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuario.setUsuarioNome(usuarioAtualizado.getUsuarioNome());
                    usuario.setUsuarioEmail(usuarioAtualizado.getUsuarioEmail());
                    usuario.setUsuarioDataNascimento(usuarioAtualizado.getUsuarioDataNascimento());
                    usuario.setUsuarioTipo(usuarioAtualizado.getUsuarioTipo());
                    usuario.setUsuarioAutobiografia(usuarioAtualizado.getUsuarioAutobiografia());
                    usuario.setUsuarioSenha(passwordEncoder.encode(usuarioAtualizado.getUsuarioSenha()));
                    usuario.setUsuarioAvatar(usuarioAtualizado.getUsuarioAvatar());
                    usuario.setUsuarioPontuacao(usuarioAtualizado.getUsuarioPontuacao());
                    usuario.setUsuarioUserName(usuarioAtualizado.getUsuarioUserName());
                    return ResponseEntity.ok(usuarioRepository.save(usuario));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // ==========================
    // DELETAR
    // ==========================
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarUsuario(@PathVariable String id) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuarioRepository.deleteById(id);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // ==========================
    // LOGIN
    // ==========================
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        Optional<Usuario> existente = usuarioRepository.findByUsuarioEmail(usuario.getUsuarioEmail());

        if (existente.isEmpty()) {
            return ResponseEntity.badRequest().body(new MensagemErro("Usuário não encontrado!"));
        }

        Usuario encontrado = existente.get();

        if (!passwordEncoder.matches(usuario.getUsuarioSenha(), encontrado.getUsuarioSenha())) {
            return ResponseEntity.badRequest().body(new MensagemErro("Senha incorreta!"));
        }

        return ResponseEntity.ok(encontrado);
    }

    // ==========================
    // SOLICITAR RESET DE SENHA
    // ==========================
    @PostMapping("/solicitar-reset")
    public ResponseEntity<?> solicitarReset(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsuarioEmail(email);

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("mensagem", "E-mail não encontrado!"));
        }

        // Apaga tokens antigos
        tokenRepository.deleteByUsuarioEmail(email);

        // Gera token aleatório
        String token = UUID.randomUUID().toString();
        String tokenHash = passwordEncoder.encode(token); // hash seguro

        Instant expiration = Instant.now().plusSeconds(15 * 60); // 15 minutos
        PasswordResetToken resetToken = new PasswordResetToken(email, tokenHash, expiration);
        tokenRepository.save(resetToken);

        // Aqui você envia o token por e-mail (simulado)
        System.out.println("Enviar e-mail para " + email + " com token: " + token);

        return ResponseEntity.ok(Map.of(
                "mensagem", "Token de redefinição enviado por e-mail (em produção)."
        ));
    }

    // ==========================
    // RESETAR SENHA
    // ==========================
    @PostMapping("/reset-senha")
    public ResponseEntity<?> resetSenha(@RequestBody Map<String, String> payload) {
        String tokenRecebido = payload.get("token");
        String novaSenha = payload.get("novaSenha");

        Optional<PasswordResetToken> tokenOpt = tokenRepository.findAll()
                .stream()
                .filter(t -> passwordEncoder.matches(tokenRecebido, t.getTokenHash()))
                .findFirst();

        if (tokenOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("mensagem", "Token inválido!"));
        }

        PasswordResetToken resetToken = tokenOpt.get();

        if (resetToken.getExpiration().isBefore(Instant.now())) {
            return ResponseEntity.badRequest().body(Map.of("mensagem", "Token expirado!"));
        }

        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsuarioEmail(resetToken.getUsuarioEmail());
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("mensagem", "Usuário não encontrado!"));
        }

        Usuario usuario = usuarioOpt.get();
        usuario.setUsuarioSenha(passwordEncoder.encode(novaSenha));
        usuarioRepository.save(usuario);

        tokenRepository.delete(resetToken); // invalida token usado

        return ResponseEntity.ok(Map.of("mensagem", "Senha redefinida com sucesso!"));
    }

    // ==========================
    // CLASSE DE MENSAGEM
    // ==========================
    static class MensagemErro {
        public String mensagem;
        public MensagemErro(String mensagem) {
            this.mensagem = mensagem;
        }
    }
}

