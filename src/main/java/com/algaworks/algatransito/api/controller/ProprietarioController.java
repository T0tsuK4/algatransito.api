package com.algaworks.algatransito.api.controller;

import com.algaworks.algatransito.domain.exception.NegocioException;
import com.algaworks.algatransito.domain.model.Proprietario;
import com.algaworks.algatransito.domain.repository.ProprietarioRepository;
import com.algaworks.algatransito.domain.service.RegistroProprietarioService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping({"/api", "/proprietarios"})
public class ProprietarioController {

    private final RegistroProprietarioService registroProprietarioService;
    private final ProprietarioRepository proprietarioRepository;


    @GetMapping
        public List<Proprietario> listar() {
            return proprietarioRepository.findAll();
    }

    @GetMapping("{proprietarioId}")
    public ResponseEntity<Proprietario> buscar (@Valid @PathVariable Long proprietarioId){

    Optional<Proprietario> proprietario = proprietarioRepository.findById(proprietarioId);
        return proprietario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Proprietario adicionar (@RequestBody Proprietario proprietario){
       return registroProprietarioService.salvar(proprietario);
    }

    @PutMapping("/{proprietarioId}")
    public ResponseEntity<Proprietario> atualizar (@PathVariable Long proprietarioId,
                                                   @Valid @RequestBody Proprietario proprietario ){
        if (!proprietarioRepository.existsById(proprietarioId)){
            return ResponseEntity.notFound().build();
        }
        proprietario.setId(proprietarioId);
        Proprietario proprietarioAtualizado = registroProprietarioService.salvar(proprietario);

        return ResponseEntity.ok(proprietarioAtualizado);
    }

    @DeleteMapping("/{proprietarioId}")
    public ResponseEntity<Proprietario> deletar (@PathVariable Long proprietarioId){
        if (!proprietarioRepository.existsById(proprietarioId)){
            return ResponseEntity.notFound().build();
        }
        registroProprietarioService.excluir(proprietarioId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<String> capturar(NegocioException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
