package br.com.uniamerica.estacionamento.entitys;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
@Entity
@Table(name = "Movimentacao", schema = "public")
public class Movimentacao extends AbstractEntity {

    @Getter @Setter
    @Column (name = "veiculo", nullable = false, unique = true)
    Veiculo veiculo = new Veiculo();//instância da classe veiculo


    @Getter @Setter
    @Column (name = "condutor", nullable = false)
    Condutor condutor = new Condutor();
    //========================================================
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
