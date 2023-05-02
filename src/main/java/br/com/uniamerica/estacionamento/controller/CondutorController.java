package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.CondutorRepository;
import br.com.uniamerica.estacionamento.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@ResponseBody
@RequestMapping( value = "/api/condutor")
public class CondutorController {

    @Autowired
    private CondutorRepository condutorRepository;

    //get by id da controller condutor
    @GetMapping("/{id}")
    public ResponseEntity<?> findbyIdRequest(@PathVariable("id")final Long id){
        final Condutor condutor = this.condutorRepository.findById(id).orElse(null);

        return condutor == null
                ? ResponseEntity.badRequest().body("nenhum registro de condutor")
                :ResponseEntity.ok(condutor);
    }

    //get all da controller condutor
    @GetMapping("/lista")
    public ResponseEntity <?> listacompleta (){
        return ResponseEntity.ok(this.condutorRepository.findAll());
    }

    //get by ativo da controller condutor
    @GetMapping("/ativo")
    public ResponseEntity<?> listarAtivo(){
        return ResponseEntity.ok(this.condutorRepository.FindByativo());
    }

    //post da controller condutor
    @PostMapping
    public ResponseEntity<?> cadastroCondutor(@RequestBody final Condutor condutor){
        try{
            this.condutorRepository.save(condutor);
            return ResponseEntity.ok("Cadastro do condutor concluido");
        }catch(DataIntegrityViolationException e){
            return ResponseEntity.badRequest().body("Erro de inserção de cadastro" +e.getStackTrace());
        }catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body("Error" + e.getMessage());
        }


    }

    @PutMapping
    public ResponseEntity<?> editar(@RequestParam("id") final Long id,@RequestBody final Condutor condutor){
        try{
            final Condutor condutorBanco = this.condutorRepository.findById(id).orElse(null);
            if(condutorBanco == null || !condutorBanco.getId().equals(condutor.getId())){
                throw new RuntimeException("Condutor informado nao foi identificado");
            }
        this.condutorRepository.save(condutor);
        return  ResponseEntity.ok("Condutor editado com sucesso");
        }catch (DataIntegrityViolationException e) {
            return ResponseEntity.internalServerError().body("error" + e.getCause().getCause().getMessage());

        }catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("error" + e.getMessage());
        }
    }

    //delete se o condutor estiver vinculado a uma movimentacao
    @DeleteMapping
    public ResponseEntity <?> deletar (@RequestParam ("id") final Long id){
        final Condutor condutorBanco = this.condutorRepository.findById(id).orElse(null);
        List<Movimentacao> movimentacaos = this.condutorRepository.findMovimentacaoByCondutor(condutorBanco);
        if(movimentacaos == null ){
            try{
                this.condutorRepository.delete(condutorBanco);
            }catch (DataIntegrityViolationException e) {
                return ResponseEntity.badRequest().body("Error " + e.getCause().getCause().getMessage());
            }
            catch (RuntimeException e) {
                return ResponseEntity.internalServerError().body("Error" + e.getMessage());
            }
        }
        else{
           condutorBanco.setAtivo(false);
           this.condutorRepository.save(condutorBanco);
        }
    return ResponseEntity.ok("condutor deletado com sucesso");

    }

}
