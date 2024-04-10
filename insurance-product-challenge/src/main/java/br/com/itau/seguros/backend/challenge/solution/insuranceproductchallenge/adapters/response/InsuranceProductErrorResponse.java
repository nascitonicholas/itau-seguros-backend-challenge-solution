package br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.adapters.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InsuranceProductErrorResponse {
    @JsonProperty(value = "descricao_erro")
    private String errorMessage;
}
