package com.algaworks.algatransito.domain.service;
import com.algaworks.algatransito.domain.model.Proprietario;
import com.algaworks.algatransito.domain.repository.ProprietarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class RegistroProprietarioService {

    private final ProprietarioRepository proprietarioRepository;


    @Transactional
    public Proprietario salvar(Proprietario proprietario){
        boolean emailEmUso = proprietarioRepository.findByEmail(proprietario.getEmail()).filter(p -> !p.equals(proprietario)).isPresent();
        if (emailEmUso){
            throw new RuntimeException("Já existe um proprietario cadastrado com esse email!");
        }
       return proprietarioRepository.save(proprietario);
    }

    public void excluir (Long proprietarioId){
        proprietarioRepository.deleteById(proprietarioId);
    }
}
