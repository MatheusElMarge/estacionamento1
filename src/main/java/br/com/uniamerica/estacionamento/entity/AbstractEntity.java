package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@MappedSuperclass// anotate
public abstract class AbstractEntity {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;


    @Getter
    @Setter
    @Column(name = "cadastro", nullable = false)
    private LocalDateTime cadastro;


    @Getter
    @Setter
    @Column(name = "atualizacao")
    private LocalDateTime edicao;

    @Getter
    @Setter
    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    @PrePersist
    private void prePersist() {
        this.cadastro = LocalDateTime.now();
        this.ativo = true;
    }

    private void preUpdate() {
        this.edicao = LocalDateTime.now();
    }
}
