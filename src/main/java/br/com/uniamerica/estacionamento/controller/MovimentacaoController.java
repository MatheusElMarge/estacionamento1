package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.repository.ModeloRepository;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
@RequestMapping( value = "/api/movimentacao")
public class MovimentacaoController {
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    //get by id da controller movimentacao
    @GetMapping("/id")
    public ResponseEntity<?> FindByRequest(@PathVariable("id") final Long id) {
        final Movimentacao movimentacao = this.movimentacaoRepository.findById(id).orElse(null);

        return movimentacao == null
                ? ResponseEntity.badRequest().body("nenhuma movimentacao cadastrada")
                :ResponseEntity.ok(movimentacao);
    }

    //get by ativo da controller movimentacao
    @GetMapping("/ativo")
    public ResponseEntity<?> FindByAtivo(){ return ResponseEntity.ok(this.movimentacaoRepository.FindByAbertas()); }


    //get all da controller movimentacao
    @GetMapping("/lista")
    public ResponseEntity<?> listaMovimentacao() {
        return ResponseEntity.ok(this.movimentacaoRepository.findAll());
    }


    //post da controller movimentacao
    @PostMapping
    public ResponseEntity<?> CadastroMovimentacao(@RequestParam("id") final Movimentacao movimentacao ){
        try{
            this.movimentacaoRepository.save(movimentacao);
            return ResponseEntity.ok("edição de movimentacao concluida");
        }catch(Exception e ){
            return ResponseEntity.badRequest().body("erro de inserção de movimentacao");

        }
    }


    //put da controller movimentacao
    @PutMapping
    public ResponseEntity<?> EditarMovimentacao(@RequestParam ("id") final Long id, @RequestBody final Movimentacao movimentacao){
        try{
            final Movimentacao movBanco = this.movimentacaoRepository.findById(id).orElse(null);
            if(movBanco == null  || !movBanco.getId().equals(movBanco.getId())){
                throw new RuntimeException("Movimentacao inserida erroneamente");
            }
            this.movimentacaoRepository.save(movimentacao);
            return ResponseEntity.ok("Movimentacao editada com sucesso");

        }catch(DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error"+e.getCause().getCause().getMessage());
        }catch(RuntimeException e){
            return ResponseEntity.internalServerError().body("Error"+e.getMessage());
        }

    }

    //delete da controller movimentacao
    @DeleteMapping
    public ResponseEntity<?> deletar(@RequestParam("id") final Long id) {

        final Movimentacao movBanco = this.movimentacaoRepository.findById(id).orElse(null);
        this.movimentacaoRepository.delete(movBanco);
        if(movBanco == null){
            movBanco.setAtivo(false);
            this.movimentacaoRepository.save(movBanco);
        }
        return ResponseEntity.ok("movimentacao deletada com sucesso");

    }
}
