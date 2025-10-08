// ===== CategoriaRepository.java =====
package com.matheus.sistemachamados.repository;

import com.matheus.sistemachamados.model.Categoria;
import com.matheus.sistemachamados.model.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    // Busca categorias de um departamento
    List<Categoria> findByDepartamento(Departamento departamento);

    // Busca categorias ativas
    List<Categoria> findByAtivoTrue();

    // Busca por nome
    List<Categoria> findByNomeContainingIgnoreCase(String nome);
}