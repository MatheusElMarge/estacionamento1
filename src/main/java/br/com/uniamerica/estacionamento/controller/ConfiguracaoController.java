package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Configuracao;
import br.com.uniamerica.estacionamento.repository.ConfiguracaoRepository;
import com.electronwill.nightconfig.core.conversion.Path;
import org.aspectj.apache.bcel.classfile.annotation.RuntimeInvisAnnos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
@RequestMapping(value = "/api/configuracao")
public class ConfiguracaoController {
    @Autowired
    private ConfiguracaoRepository configuracaoRepository;

    // get by id da controller configuracao
    @GetMapping("/id")
    public ResponseEntity<?> FindByRequest(@PathVariable("id") final Long id) {
        final Configuracao configuracao = this.configuracaoRepository.findById(id).orElse(null);

        return configuracao == null
                ? ResponseEntity.badRequest().body("nenhuma configuracao cadastrada")
                :ResponseEntity.ok(configuracao);
    }

    //post da controller configuração
   @PostMapping
    public ResponseEntity<?> CadastroConfiguracao(@RequestParam("id") final Configuracao configuracao ){
        try{
            this.configuracaoRepository.save(configuracao);
                return ResponseEntity.ok("edição de cadastro concluida");
            }catch(Exception e ){
                return ResponseEntity.badRequest().body("erro de inserção de configuração");

        }
    }
    @PutMapping
    public ResponseEntity<?> EditarConfiguração(@RequestParam ("id") final Long id, @RequestBody final Configuracao configuracao){
        try{
            final Configuracao configuracaoBanco = this.configuracaoRepository.findById(id).orElse(null);
            if(configuracaoBanco == null  || !configuracaoBanco.getId().equals(configuracao.getId())){
                throw new RuntimeException("Configuração inserida erroneamente");
            }
            this.configuracaoRepository.save(configuracao);
            return ResponseEntity.ok("configuração editada com sucesso");

        }catch(DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error"+e.getCause().getCause().getMessage());
        }catch(RuntimeException e){
            return ResponseEntity.internalServerError().body("Error"+e.getMessage());
        }
    }

}
