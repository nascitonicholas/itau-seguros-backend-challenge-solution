package br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.adapters.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
public class ValidateBodyException extends RuntimeException {
    public ValidateBodyException(String message) {
        super(message);
    }
}