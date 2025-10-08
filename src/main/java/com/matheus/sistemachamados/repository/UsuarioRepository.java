// ===== UsuarioRepository.java =====
package com.matheus.sistemachamados.repository;

import com.matheus.sistemachamados.model.TipoUsuario;
import com.matheus.sistemachamados.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Spring cria a query automaticamente pelo nome do método!
    Optional<Usuario> findByEmail(String email);

    // Busca por email e senha (para login)
    Optional<Usuario> findByEmailAndSenha(String email, String senha);

    // Verifica se email já existe
    boolean existsByEmail(String email);

    // Busca usuários por tipo
    List<Usuario> findByTipoUsuario(TipoUsuario tipo);

    // Busca usuários ativos
    List<Usuario> findByAtivoTrue();

    // Busca por nome (contendo o texto)
    List<Usuario> findByNomeContainingIgnoreCase(String nome);
}

