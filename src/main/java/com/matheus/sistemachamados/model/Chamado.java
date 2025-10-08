package com.matheus.sistemachamados.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "chamados")
public class Chamado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusChamado status = StatusChamado.ABERTO;

    // Relacionamento: Muitos chamados para Um usuário
    @ManyToOne
    @JoinColumn(name = "usuario_abertura_id", nullable = false)
    private Usuario usuarioAbertura;

    // Técnico pode ser nulo (quando chamado está aberto)
    @ManyToOne
    @JoinColumn(name = "tecnico_responsavel_id")
    private Usuario tecnicoResponsavel;

    // Relacionamento com Categoria
    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

    @Column(name = "data_solucao")
    private LocalDateTime dataSolucao;

    // Construtor vazio
    public Chamado() {
    }

    // Construtor útil para criar novos chamados
    public Chamado(String titulo, String descricao, Usuario usuarioAbertura, Categoria categoria) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.usuarioAbertura = usuarioAbertura;
        this.categoria = categoria;
        this.status = StatusChamado.ABERTO; // Sempre inicia como aberto
    }

    // Métodos de ciclo de vida JPA
    @PrePersist
    public void prePersist() {
        criadoEm = LocalDateTime.now();
        atualizadoEm = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        atualizadoEm = LocalDateTime.now();

        // Se mudou para solucionado, registra a data
        if (status == StatusChamado.SOLUCIONADO && dataSolucao == null) {
            dataSolucao = LocalDateTime.now();
        }
    }

    // Métodos de negócio (facilitam o uso)
    public boolean podeSerAtribuidoPara(Usuario tecnico) {
        // Só pode atribuir se estiver aberto e o usuário for técnico
        return status == StatusChamado.ABERTO &&
                tecnico.getTipoUsuario() == TipoUsuario.TECNICO;
    }

    public boolean podeAlterarStatus(Usuario usuario) {
        // Só o técnico responsável pode alterar o status
        return tecnicoResponsavel != null &&
                tecnicoResponsavel.getId().equals(usuario.getId());
    }

    public void atribuirTecnico(Usuario tecnico) {
        if (podeSerAtribuidoPara(tecnico)) {
            this.tecnicoResponsavel = tecnico;
            this.status = StatusChamado.EM_TRATATIVA;
        } else {
            throw new IllegalStateException("Não é possível atribuir este técnico ao chamado");
        }
    }

    public void solucionar() {
        if (status == StatusChamado.EM_TRATATIVA) {
            this.status = StatusChamado.SOLUCIONADO;
        } else {
            throw new IllegalStateException("Chamado deve estar em tratativa para ser solucionado");
        }
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public StatusChamado getStatus() {
        return status;
    }

    public void setStatus(StatusChamado status) {
        this.status = status;
    }

    public Usuario getUsuarioAbertura() {
        return usuarioAbertura;
    }

    public void setUsuarioAbertura(Usuario usuarioAbertura) {
        this.usuarioAbertura = usuarioAbertura;
    }

    public Usuario getTecnicoResponsavel() {
        return tecnicoResponsavel;
    }

    public void setTecnicoResponsavel(Usuario tecnicoResponsavel) {
        this.tecnicoResponsavel = tecnicoResponsavel;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public LocalDateTime getAtualizadoEm() {
        return atualizadoEm;
    }

    public LocalDateTime getDataSolucao() {
        return dataSolucao;
    }
}