// ===== ChamadoRepository.java =====
package com.matheus.sistemachamados.repository;

import com.matheus.sistemachamados.model.Chamado;
import com.matheus.sistemachamados.model.StatusChamado;
import com.matheus.sistemachamados.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ChamadoRepository extends JpaRepository<Chamado, Long> {

    // Busca todos os chamados de um usuário específico
    List<Chamado> findByUsuarioAbertura(Usuario usuario);

    // Busca chamados por status
    List<Chamado> findByStatus(StatusChamado status);

    // Busca chamados de um técnico específico
    List<Chamado> findByTecnicoResponsavel(Usuario tecnico);

    // Busca chamados abertos (sem técnico)
    List<Chamado> findByStatusAndTecnicoResponsavelIsNull(StatusChamado status);

    // Busca por título ou descrição (sua ideia de buscar por tema!)
    List<Chamado> findByTituloContainingIgnoreCaseOrDescricaoContainingIgnoreCase(
            String titulo, String descricao
    );

    // Query customizada para estatísticas
    @Query("SELECT c.status, COUNT(c) FROM Chamado c GROUP BY c.status")
    List<Object[]> countByStatus();

    // Busca chamados do técnico por status
    List<Chamado> findByTecnicoResponsavelAndStatus(Usuario tecnico, StatusChamado status);

    // Ordenação: chamados mais recentes primeiro
    List<Chamado> findAllByOrderByCriadoEmDesc();
}
