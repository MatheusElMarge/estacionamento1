package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "veiculo",schema = "public")

public class Veiculo extends AbstractEntity {
    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "modelo_id", nullable = false, unique = true)
    private Modelo modelo;

    @Getter @Setter
    @Column (name = "placa", length = 10, nullable = false, unique = true)
    private String placa;

    @Getter @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "cor", length = 20, nullable = false)
    private String cor;

    @Getter @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", length = 50, nullable = false)
    private String tipo;

    @Getter @Setter
    @Column(name = "ano", length = 4, nullable = false)
    private Integer ano;


}

