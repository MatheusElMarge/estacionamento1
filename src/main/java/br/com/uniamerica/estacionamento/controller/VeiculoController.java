package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.VeiculoRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
@RequestMapping( value = "/api/veiculo")
public class VeiculoController {

    @Autowired
    private VeiculoRepository veiculoRepository;



    //get by id controller veiculo
    @GetMapping("/{id}")
    public ResponseEntity<?> findbyIdRequest(@PathVariable("id")final Long id){
             final Veiculo veiculo = this.veiculoRepository.findById(id).orElse(null);

        return veiculo == null
            ? ResponseEntity.badRequest().body("nenhum registro de veiculo")
                :ResponseEntity.ok(veiculo);
    }

    //get by ativo controller veiculo
    @GetMapping("/ativo")
    public ResponseEntity <?> listarAtivo(){
        return ResponseEntity.ok(this.veiculoRepository.FindByativo());
    }


    //get all controller veiculo
    @GetMapping("/lista")
    public ResponseEntity <?> listacompleta (){
        return ResponseEntity.ok(this.veiculoRepository.findAll());
    }

    //post controller veiculo
    @PostMapping
    public ResponseEntity <?> cadastro(@RequestBody final Veiculo veiculo){
        try {
            this.veiculoRepository.save(veiculo);
            return ResponseEntity.ok("cadastro de veiculo com sucesso");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Erro de cadastro de veiculo"+ e.getStackTrace());
        }
    }

    //put da controller veiculo
    @PutMapping
    public ResponseEntity<?> EditarVeiculo(@RequestParam ("id") final Long id, @RequestBody final Veiculo veiculo){
        try{
            final Veiculo veiculoBanco= this.veiculoRepository.findById(id).orElse(null);
            if(veiculoBanco == null  || !veiculoBanco.getId().equals(veiculo.getId())){
                throw new RuntimeException("Veiculo inserida erroneamente");
            }
            this.veiculoRepository.save(veiculo);
            return ResponseEntity.ok("Veiculo editada com sucesso");

        }catch(DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error"+e.getCause().getCause().getMessage());
        }catch(RuntimeException e){
            return ResponseEntity.internalServerError().body("Error"+e.getMessage());
        }
    }


    //delete controller veiculo
    @DeleteMapping
    public ResponseEntity<?> deletar (@RequestParam("id") final Long id){
        final Veiculo veiculoBanco = this.veiculoRepository.findById(id).orElse(null);
        return ResponseEntity.ok("delete de veiculo com sucesso");
    }


}



