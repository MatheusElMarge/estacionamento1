package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import br.com.uniamerica.estacionamento.entity.Movimentacao;

import java.util.List;

@Repository
public interface CondutorRepository extends JpaRepository<Condutor, Long> {

    @Query("from Movimentacao where condutor = :condutorId")
    public List<Movimentacao> findMovimentacaoByCondutor(@Param("condutorId") final Condutor condutorId);
    @Query("from Condutor where ativo = 1")
    public List<Condutor> FindByativo();

}