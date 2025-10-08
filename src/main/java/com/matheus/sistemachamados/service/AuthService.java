package com.matheus.sistemachamados.service;

import com.matheus.sistemachamados.dto.LoginRequest;
import com.matheus.sistemachamados.dto.LoginResponse;
import com.matheus.sistemachamados.model.Usuario;
import com.matheus.sistemachamados.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // Marca como um serviço do Spring
public class AuthService {

    @Autowired // Spring injeta o repository automaticamente
    private UsuarioRepository usuarioRepository;

    /**
     * Realiza o login do usuário
     * @param loginRequest contém email e senha
     * @return LoginResponse com dados do usuário ou erro
     */
    public LoginResponse login(LoginRequest loginRequest) {
        // Busca usuário por email e senha
        Usuario usuario = usuarioRepository
                .findByEmailAndSenha(loginRequest.getEmail(), loginRequest.getSenha())
                .orElse(null);

        // Se não encontrou, retorna erro
        if (usuario == null) {
            return new LoginResponse(false, "Email ou senha inválidos", null);
        }

        // Verifica se usuário está ativo
        if (!usuario.getAtivo()) {
            return new LoginResponse(false, "Usuário inativo", null);
        }

        // Login com sucesso!
        return new LoginResponse(
                true,
                "Login realizado com sucesso",
                usuario
        );
    }

    /**
     * Registra um novo usuário
     * @param usuario dados do novo usuário
     * @return o usuário criado ou null se email já existe
     */
    public Usuario registrar(Usuario usuario) {
        // Verifica se email já existe
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("Email já cadastrado");
        }

        // TODO: Criptografar senha antes de salvar!
        // Por enquanto, salvamos em texto simples (NUNCA faça isso em produção!)

        // Salva e retorna o usuário
        return usuarioRepository.save(usuario);
    }
}