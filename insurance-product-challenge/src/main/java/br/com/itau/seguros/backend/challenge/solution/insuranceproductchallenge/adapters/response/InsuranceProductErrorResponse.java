package br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.adapters.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InsuranceProductErrorResponse {
    @JsonProperty(value = "descricao_erro")
    private String errorMessage;
}
