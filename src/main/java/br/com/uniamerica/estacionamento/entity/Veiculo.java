package br.com.uniamerica.estacionamento.entity;

import br.com.uniamerica.estacionamento.entity.enums.Cor;
import br.com.uniamerica.estacionamento.entity.enums.Tipo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;


@Entity
@Audited
@Table(name = "veiculo",schema = "public")
@AuditTable(value =  "veiculo_audit", schema = "audit")

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
    private Cor cor;

    @Getter @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", length = 50, nullable = false)
    private Tipo tipo;

    @Getter @Setter
    @Column(name = "ano", length = 4, nullable = false)
    private Integer ano;


}

