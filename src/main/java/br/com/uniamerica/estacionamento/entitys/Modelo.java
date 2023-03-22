package br.com.uniamerica.estacionamento.entitys;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "modelo", schema = "public")

public class Modelo extends AbstractEntity {
    @Getter @Setter
    @Column (name = "nome", length = 50, nullable = false, unique = true)
    private String nome;

    @Getter @Setter
    @Column(name = "marca", nullable = false)
    Marca marca = new Marca();

}
