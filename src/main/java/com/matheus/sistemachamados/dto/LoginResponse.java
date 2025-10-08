// LoginResponse.java
package com.matheus.sistemachamados.dto;

import com.matheus.sistemachamados.model.Usuario;

public class LoginResponse {
    private boolean sucesso;
    private String mensagem;
    private Usuario usuario;

    // Construtor
    public LoginResponse(boolean sucesso, String mensagem, Usuario usuario) {
        this.sucesso = sucesso;
        this.mensagem = mensagem;
        this.usuario = usuario;
    }

    // Getters e Setters
    public boolean isSucesso() {
        return sucesso;
    }

    public void setSucesso(boolean sucesso) {
        this.sucesso = sucesso;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}