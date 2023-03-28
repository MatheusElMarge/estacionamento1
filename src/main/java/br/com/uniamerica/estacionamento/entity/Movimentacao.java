package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
@Entity
@Table(name = "Movimentacao", schema = "public")
public class Movimentacao extends AbstractEntity {

    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "veiculo_id", nullable = false, unique = true)
    private Veiculo veiculo;//instância da classe veiculo


    @Getter @Setter
    @ManyToOne
    @JoinColumn (name = "condutor_id", nullable = false)
    private Condutor condutor;

    
    @Getter @Setter
    @Column (name = "entrada", nullable = false)
    private LocalDateTime entrada;

    @Getter @Setter
    @Column (name = "saida")
    private LocalDateTime saida;

    @Getter @Setter
    @Column (name = "tempo")
    private LocalTime tempo;

    @Getter @Setter
    @Column (name = "tempoDesconto")
    private LocalTime tempoDesconto;

    @Getter @Setter
    @Column (name = "tempoMulta")
    private LocalTime tempoMulta;

    @Getter @Setter
    @Column (name = "valorDesconto")
    private BigDecimal valorDesconto;

    @Getter @Setter
    @Column (name = "valorMulta")
    private BigDecimal valorMulta;


    @Getter @Setter
    @Column (name = "condutor")
    private BigDecimal valorTotal;

    @Getter @Setter
    @Column (name = "valorHora")
    private BigDecimal valorHora;

    @Getter @Setter
    @Column (name = "valorHoraMulta")
    private BigDecimal valorHoraMulta;

}
