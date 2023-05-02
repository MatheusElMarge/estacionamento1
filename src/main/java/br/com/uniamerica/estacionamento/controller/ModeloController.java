package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.ModeloRepository;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ClientHttpResponseDecorator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.channels.ScatteringByteChannel;
import java.util.List;

@Controller
@ResponseBody
@RequestMapping( value = "/api/modelo")
public class ModeloController {

    @Autowired
    private ModeloRepository modeloRepository;

    //get by id da controller modelo
    @GetMapping("/{id}")
    public ResponseEntity<?> FindByIdRequest(@PathVariable("id") final Long id) {
        final Modelo modelo = this.modeloRepository.findById(id).orElse(null);

        return modelo == null
                ? ResponseEntity.badRequest().body("nenhum registro cadastrado")
                : ResponseEntity.ok(modelo);
    }

    //get all da controller modelo
    @GetMapping("/lista")
    public ResponseEntity<?> listacompleta() {
        return ResponseEntity.ok(this.modeloRepository.findAll());
    }

    //get by ativo da controller modelo
    @GetMapping("/ativo")
    public ResponseEntity<?> listarAtivo(){
        return ResponseEntity.ok(this.modeloRepository.FindByAtivo());
    }


    //post da controller modelo
    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Modelo modelo) {
        try {
            this.modeloRepository.save(modelo);
            return ResponseEntity.ok("cadastro feito com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error:" + e.getStackTrace());
        }
    }
    // put da controller modelo
    @PutMapping
    public ResponseEntity<?> editar(@RequestParam("id") final Long id, @RequestBody final Modelo modelo) {
        try {
            final Modelo modeloBanco = this.modeloRepository.findById(id).orElse(null);
            if (modeloBanco == null || !modeloBanco.getId().equals(modelo.getId())) {
                throw new RuntimeException("Registro informado nao foi poissivel ser identificado");
            }
            this.modeloRepository.save(modelo);
            return ResponseEntity.ok("registro cadastrado");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.internalServerError().body("error" + e.getCause().getCause().getMessage());

        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body("Error" + e.getMessage());
        }
    }
    //delete da controller modelo
   @DeleteMapping
   public ResponseEntity<?> deletar(@RequestParam("id") final Long id) {
        final Modelo modeloBanco = this.modeloRepository.findById(id).orElse(null);
       List<Veiculo> veiculoList = this.modeloRepository.findModeloByVeiculo(modeloBanco);
            if(veiculoList == null){
                try{
                    this.modeloRepository.delete(modeloBanco);
                }catch(DataIntegrityViolationException e){
                    return ResponseEntity.internalServerError().body("erro"+e.getCause().getCause().getMessage());
                }catch(RuntimeException e){
                    return  ResponseEntity.internalServerError().body("erro"+e.getMessage());

                }
            }
            else{
                modeloBanco.setAtivo(false);
                this.modeloRepository.save(modeloBanco);
            }

        return ResponseEntity.ok("delete com sucesso");
   }
}