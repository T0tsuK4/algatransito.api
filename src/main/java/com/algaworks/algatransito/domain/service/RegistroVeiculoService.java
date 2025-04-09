package com.algaworks.algatransito.domain.service;

import com.algaworks.algatransito.domain.exception.NegocioException;
import com.algaworks.algatransito.domain.model.Proprietario;
import com.algaworks.algatransito.domain.model.StatusVeiculo;
import com.algaworks.algatransito.domain.model.Veiculo;
import com.algaworks.algatransito.domain.repository.ProprietarioRepository;
import com.algaworks.algatransito.domain.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class RegistroVeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;
    @Autowired
    private ProprietarioRepository proprietarioRepository;

    @Transactional
    public Veiculo cadastrar(Veiculo novoVeiculo){
        if(novoVeiculo.getId() != null){
            throw new  NegocioException("Veiculo a ser cadastrado não deve possuir um ID");
        }
        //Essa verificação é uma regra de negócio que verifica se está sendo cadastrado um veiculo com uma placa igual
        // a outro veiculo cadastrado
        boolean placaEmUso = veiculoRepository.findByPlaca(novoVeiculo.getPlaca())
                .filter(veiculo -> !veiculo.equals(novoVeiculo)).isPresent();
            if(placaEmUso){
                throw new NegocioException("Já existe um veiculo com essa placa");
            }
        Proprietario proprietario = proprietarioRepository.findById(novoVeiculo.getProprietario().getId())
                        .orElseThrow(()-> new NegocioException("Proprietario não encontrado"));

            novoVeiculo.setProprietario(proprietario);
        novoVeiculo.setStatus(StatusVeiculo.REGULAR);
        novoVeiculo.setDataCadastro(LocalDateTime.now());
        return veiculoRepository.save(novoVeiculo);
    }
}
