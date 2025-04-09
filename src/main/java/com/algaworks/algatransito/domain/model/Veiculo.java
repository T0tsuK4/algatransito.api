package com.algaworks.algatransito.domain.model;

import com.algaworks.algatransito.domain.validation.ValidationGroups;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.groups.ConvertGroup;
import jakarta.validation.groups.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
// Toda essa parte é referente ao mapeamento da Entidade Objeto "Veículo"
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity //É a anotação que define esse objeto como uma entidade
public class Veiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Valid
    @ConvertGroup(from =  Default.class, to = ValidationGroups.ProprietarioId.class)
    @NotNull
    @ManyToOne // Anotação de Muitos para Um em um objeto relacional (Muitos Vaiculos para um Proprietario)
   // @JoinColumn(name = "proprietario_id") // Permite especificar qual o nome da propriedade que faz esse relacionamento no modelo relacional
    private Proprietario proprietario;
    @NotBlank
    private String marca;
    @NotBlank
    private String modelo;
    @NotBlank
    //Usando a anotação @Pattern criamos uma validação e padronização de cadastro das placas de veiculos
    // usando um expreção regular para isso
    @Pattern(regexp = "[A-Z]{3}[0-9][0-9 A-Z][0-9]{2}")
    private String placa;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Enumerated(EnumType.STRING)
    private StatusVeiculo status;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime dataCadastro;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime dataApreencao;
}
