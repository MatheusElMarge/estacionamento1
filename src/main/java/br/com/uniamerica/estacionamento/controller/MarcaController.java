package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Configuracao;
import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@ResponseBody
@RequestMapping(value = "/api/marca")
public class MarcaController {

    @Autowired
    public MarcaRepository marcaRepository;



    //get by id da controller marca
    @GetMapping("/id")
    public ResponseEntity<?> FindByRequest(@PathVariable("id") final Long id) {
        final Marca marca = this.marcaRepository.findById(id).orElse(null);

        return marca == null
                ? ResponseEntity.badRequest().body("nenhuma marca cadastrada")
                :ResponseEntity.ok(marca);
    }

    //get all da controller marca
    @GetMapping("/lista")
    public ResponseEntity<?> listaMarca() {
        return ResponseEntity.ok(this.marcaRepository.findAll());
    }


    //get by ativo da controller marca
    @GetMapping("/ativo")
    public ResponseEntity<?> listarAtivo(){
        return ResponseEntity.ok(this.marcaRepository.FindByativo());
    }


    //post da controller marca
    @PostMapping
    public ResponseEntity<?> CadastroMarca(@RequestParam("id") final Marca marca ){
        try{
            this.marcaRepository.save(marca);
            return ResponseEntity.ok("edição de marca concluida");
        }catch(Exception e ){
            return ResponseEntity.badRequest().body("erro de inserção de marca");

        }
    }

    //put da controller marca
    @PutMapping
    public ResponseEntity<?> EditarMarca(@RequestParam ("id") final Long id, @RequestBody final Marca marca){
        try{
            final Marca marcaBanco = this.marcaRepository.findById(id).orElse(null);
            if(marcaBanco == null  || !marcaBanco.getId().equals(marca.getId())){
                throw new RuntimeException("Marca inserida erroneamente");
            }
            this.marcaRepository.save(marca);
            return ResponseEntity.ok("Marca editada com sucesso");

        }catch(DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error"+e.getCause().getCause().getMessage());
        }catch(RuntimeException e){
            return ResponseEntity.internalServerError().body("Error"+e.getMessage());
        }
    }

    //delete da controller marca
    @DeleteMapping
    public ResponseEntity<?> deletar(@RequestParam("id") final Long id) {
        final Marca marcabanco = this.marcaRepository.findById(id).orElse(null);
        List<Modelo> modeloList = this.marcaRepository.findModeloBymarca(marcabanco);
        if(modeloList == null){
            try{
                this.marcaRepository.delete(marcabanco);
            }catch(DataIntegrityViolationException e){
                return ResponseEntity.internalServerError().body("erro"+e.getCause().getCause().getMessage());
            }catch(RuntimeException e){
                return  ResponseEntity.internalServerError().body("erro"+e.getMessage());

            }
        }
        else{
            marcabanco.setAtivo(false);
            this.marcaRepository.save(marcabanco);
        }

        return ResponseEntity.ok("delete com sucesso");
    }

}
