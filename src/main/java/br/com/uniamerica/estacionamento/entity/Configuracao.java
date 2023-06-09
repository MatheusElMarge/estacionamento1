package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.math.BigDecimal;
import java.time.LocalTime;
@Entity
@Audited
@Table (name ="configuracao", schema = "public")
@AuditTable(value =  "configuracao_audit", schema = "audit")
public class Configuracao extends AbstractEntity{

    @Getter @Setter
    @Column(name = "valorHora")
    private BigDecimal valorHora;

    @Getter @Setter
    @Column (name = "valorMinutoMulta")
    private BigDecimal valorMinutoMulta;

    @Getter @Setter
    @Column (name = "inicioExpediente")
    private LocalTime inicioExpediente;

    @Getter @Setter
    @Column (name = "fimExpediente")
    private LocalTime fimExpediente;

    @Getter @Setter
    @Column (name = "tempoParaDesconto")
    private LocalTime tempoParaDesconto;

    @Getter @Setter
    @Column (name = "tempoDeDesconto")
    private LocalTime tempoDeDesconto;

    @Getter @Setter
    @Column (name = "gerarDesconto")
    private Boolean gerarDesconto;

    @Getter @Setter
    @Column (name = "vagasMoto")
    private Integer vagasMoto;

    @Getter @Setter
    @Column (name = "vagasCarro")
    private Integer vagasCarro;

    @Getter @Setter
    @Column (name = "vagasVan")
    private Integer vagasVan;

}
