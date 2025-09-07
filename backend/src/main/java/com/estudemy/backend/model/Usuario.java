package com.estudemy.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.Objects;

@Document(collection = "Usuario") // Nome da coleção no MongoDB
public class Usuario {

    @Id
    private String usuarioId;

    @NotBlank(message = "O nome não pode ser vazio.")
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres.")
    private String usuarioNome;

    @Email(message = "O e-mail deve ser válido.")
    @NotBlank(message = "O e-mail é obrigatório.")
    private String usuarioEmail;

    private LocalDate usuarioDataNascimento; // Agora usando LocalDate

    private UsuarioTipo usuarioTipo; // Agora usando enum para limitar valores

    @Size(max = 300, message = "A autobiografia não pode ter mais de 300 caracteres.")
    private String usuarioAutobiografia;

    @NotBlank(message = "A senha é obrigatória.")
    private String usuarioSenha;

    private String usuarioAvatar;

    private Integer usuarioPontuacao; // Agora inteiro

    @NotBlank(message = "O username é obrigatório.")
    private String usuarioUserName;

    // Construtor padrão (necessário para o Spring)
    public Usuario() {}

    // Construtor com campos principais
    public Usuario(String usuarioNome, String usuarioEmail, LocalDate usuarioDataNascimento,
                   UsuarioTipo usuarioTipo, String usuarioSenha, String usuarioUserName) {
        this.usuarioNome = usuarioNome;
        this.usuarioEmail = usuarioEmail;
        this.usuarioDataNascimento = usuarioDataNascimento;
        this.usuarioTipo = usuarioTipo;
        this.usuarioSenha = usuarioSenha;
        this.usuarioUserName = usuarioUserName;
    }

    // Getters e Setters
    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsuarioNome() {
        return usuarioNome;
    }

    public void setUsuarioNome(String usuarioNome) {
        this.usuarioNome = usuarioNome;
    }

    public String getUsuarioEmail() {
        return usuarioEmail;
    }

    public void setUsuarioEmail(String usuarioEmail) {
        this.usuarioEmail = usuarioEmail;
    }

    public LocalDate getUsuarioDataNascimento() {
        return usuarioDataNascimento;
    }

    public void setUsuarioDataNascimento(LocalDate usuarioDataNascimento) {
        this.usuarioDataNascimento = usuarioDataNascimento;
    }

    public UsuarioTipo getUsuarioTipo() {
        return usuarioTipo;
    }

    public void setUsuarioTipo(UsuarioTipo usuarioTipo) {
        this.usuarioTipo = usuarioTipo;
    }

    public String getUsuarioAutobiografia() {
        return usuarioAutobiografia;
    }

    public void setUsuarioAutobiografia(String usuarioAutobiografia) {
        this.usuarioAutobiografia = usuarioAutobiografia;
    }

    public String getUsuarioSenha() {
        return usuarioSenha;
    }

    public void setUsuarioSenha(String usuarioSenha) {
        this.usuarioSenha = usuarioSenha;
    }

    public String getUsuarioAvatar() {
        return usuarioAvatar;
    }

    public void setUsuarioAvatar(String usuarioAvatar) {
        this.usuarioAvatar = usuarioAvatar;
    }

    public Integer getUsuarioPontuacao() {
        return usuarioPontuacao;
    }

    public void setUsuarioPontuacao(Integer usuarioPontuacao) {
        this.usuarioPontuacao = usuarioPontuacao;
    }

    public String getUsuarioUserName() {
        return usuarioUserName;
    }

    public void setUsuarioUserName(String usuarioUserName) {
        this.usuarioUserName = usuarioUserName;
    }

    // toString para debug
    @Override
    public String toString() {
        return "Usuario{" +
                "usuarioId='" + usuarioId + '\'' +
                ", usuarioNome='" + usuarioNome + '\'' +
                ", usuarioEmail='" + usuarioEmail + '\'' +
                ", usuarioTipo=" + usuarioTipo +
                ", usuarioUserName='" + usuarioUserName + '\'' +
                '}';
    }

    // equals e hashCode para comparação
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario usuario)) return false;
        return Objects.equals(usuarioId, usuario.usuarioId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuarioId);
    }
}





