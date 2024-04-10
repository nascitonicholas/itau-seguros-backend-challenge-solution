package br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.adapters;

import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.adapters.exception.NotFoundIdException;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.adapters.request.InsuranceProductRequest;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.adapters.response.InsuranceProductErrorResponse;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.adapters.response.InsuranceProductResponse;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.domain.InsuranceProduct;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.usecases.impl.CreateInsuranceProductUseCaseImpl;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.usecases.impl.UpdateInsuranceProductUseCaseImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InsuranceControllerTest {

    @Mock
    private CreateInsuranceProductUseCaseImpl createInsuranceProductUseCase;
    @Mock
    private UpdateInsuranceProductUseCaseImpl updateInsuranceProductUseCase;
    @InjectMocks
    private InsuranceController controller;

    @Test
    public void testCreateInsuranceProductWithEmptyName() throws Exception {
        InsuranceProductRequest request = new InsuranceProductRequest("", "Vida", BigDecimal.valueOf(100.99));
        ResponseEntity<?> response =  controller.createInsuranceProducts(request);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Obrigatório informar um nome para o produto.", ((InsuranceProductErrorResponse) response.getBody()).getErrorMessage());
    }

    @Test
    public void testCreateInsuranceProductWithNullName() throws Exception {
        InsuranceProductRequest request = new InsuranceProductRequest(null, "Vida", BigDecimal.valueOf(100.99));
        ResponseEntity<?> response =  controller.createInsuranceProducts(request);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Obrigatório informar um nome para o produto.", ((InsuranceProductErrorResponse) response.getBody()).getErrorMessage());
    }

    @Test
    public void testCreateInsuranceProductWithEmptyCategory() throws Exception {
        InsuranceProductRequest request = new InsuranceProductRequest("Teste1", "", BigDecimal.valueOf(100.99));
        ResponseEntity<?> response =  controller.createInsuranceProducts(request);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Obrigatório informar uma categoria válida.", ((InsuranceProductErrorResponse) response.getBody()).getErrorMessage());
    }

    @Test
    public void testCreateInsuranceProductWithNullCategory() throws Exception {
        InsuranceProductRequest request = new InsuranceProductRequest("Teste1", null, BigDecimal.valueOf(100.99));
        ResponseEntity<?> response =  controller.createInsuranceProducts(request);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Obrigatório informar uma categoria válida.", ((InsuranceProductErrorResponse) response.getBody()).getErrorMessage());
    }

    @Test
    public void testCreateInsuranceProductWithNullBasePrice() throws Exception {
        InsuranceProductRequest request = new InsuranceProductRequest("Teste1", "Vida", null);
        ResponseEntity<?> response =  controller.createInsuranceProducts(request);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Obrigatório informar um preço base maior que zero.", ((InsuranceProductErrorResponse) response.getBody()).getErrorMessage());
    }

    @Test
    public void testCreateInsuranceProductWithBasePriceLessThanZero() throws Exception {
        InsuranceProductRequest request = new InsuranceProductRequest("Teste1", "VIDA", BigDecimal.valueOf(-100.99));
        ResponseEntity<?> response =  controller.createInsuranceProducts(request);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Obrigatório informar um preço base maior que zero.", ((InsuranceProductErrorResponse) response.getBody()).getErrorMessage());
    }

    @Test
    public void testCreateInsuranceProductWithBasePriceEqualZero() throws Exception {
        InsuranceProductRequest request = new InsuranceProductRequest("Teste1", "VIDA", BigDecimal.ZERO);
        ResponseEntity<?> response =  controller.createInsuranceProducts(request);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Obrigatório informar um preço base maior que zero.", ((InsuranceProductErrorResponse) response.getBody()).getErrorMessage());
    }

    @Test
    public void testCreateInsuranceProductWithInternalServerError() throws Exception {
        InsuranceProductRequest request = new InsuranceProductRequest("Teste1", "VIDA", BigDecimal.TEN);

        when(createInsuranceProductUseCase.execute(any(InsuranceProduct.class))).thenThrow(RuntimeException.class);

        ResponseEntity<?> response =  controller.createInsuranceProducts(request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testCreateInsuranceProductWithInvalidCategory() throws Exception {
        InsuranceProductRequest request = new InsuranceProductRequest("Teste1", "null", BigDecimal.TEN);
        ResponseEntity<?> response =  controller.createInsuranceProducts(request);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Obrigatório informar uma categoria válida.", ((InsuranceProductErrorResponse) response.getBody()).getErrorMessage());
    }

    @Test
    public void testCreateVidaInsuranceProduct() throws Exception {
        InsuranceProductRequest request = new InsuranceProductRequest("Teste1", "Vida", BigDecimal.TEN);
        InsuranceProductResponse productResponse = new InsuranceProductResponse("1", "Seguro Vida", "Vida", BigDecimal.valueOf(100.99), BigDecimal.valueOf(102));

        when(createInsuranceProductUseCase.execute(any(InsuranceProduct.class))).thenReturn(productResponse);

        ResponseEntity<?> response =  controller.createInsuranceProducts(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(productResponse.getId(), ((InsuranceProductResponse) response.getBody()).getId());
        assertEquals(productResponse.getName(), ((InsuranceProductResponse) response.getBody()).getName());
        assertEquals(productResponse.getCategory(), ((InsuranceProductResponse) response.getBody()).getCategory());
        assertEquals(productResponse.getBasePrice(), ((InsuranceProductResponse) response.getBody()).getBasePrice());
        assertEquals(productResponse.getTariffedPrice(), ((InsuranceProductResponse) response.getBody()).getTariffedPrice());
    }

    @Test
    public void testCreateAutoInsuranceProduct() throws Exception {
        InsuranceProductRequest request = new InsuranceProductRequest("Teste1", "Auto", BigDecimal.TEN);
        InsuranceProductResponse productResponse = new InsuranceProductResponse("2", "Seguro Auto", "Auto", BigDecimal.valueOf(10.99), BigDecimal.valueOf(12));

        when(createInsuranceProductUseCase.execute(any(InsuranceProduct.class))).thenReturn(productResponse);

        ResponseEntity<?> response =  controller.createInsuranceProducts(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(productResponse.getId(), ((InsuranceProductResponse) response.getBody()).getId());
        assertEquals(productResponse.getName(), ((InsuranceProductResponse) response.getBody()).getName());
        assertEquals(productResponse.getCategory(), ((InsuranceProductResponse) response.getBody()).getCategory());
        assertEquals(productResponse.getBasePrice(), ((InsuranceProductResponse) response.getBody()).getBasePrice());
        assertEquals(productResponse.getTariffedPrice(), ((InsuranceProductResponse) response.getBody()).getTariffedPrice());
    }

    @Test
    public void testCreateViagemInsuranceProduct() throws Exception {
        InsuranceProductRequest request = new InsuranceProductRequest("Teste1", "Viagem", BigDecimal.TEN);
        InsuranceProductResponse productResponse = new InsuranceProductResponse("3", "Seguro Viagem", "Viagem", BigDecimal.valueOf(10.9), BigDecimal.valueOf(12.565));

        when(createInsuranceProductUseCase.execute(any(InsuranceProduct.class))).thenReturn(productResponse);

        ResponseEntity<?> response = controller.createInsuranceProducts(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(productResponse.getId(), ((InsuranceProductResponse) response.getBody()).getId());
        assertEquals(productResponse.getName(), ((InsuranceProductResponse) response.getBody()).getName());
        assertEquals(productResponse.getCategory(), ((InsuranceProductResponse) response.getBody()).getCategory());
        assertEquals(productResponse.getBasePrice(), ((InsuranceProductResponse) response.getBody()).getBasePrice());
        assertEquals(productResponse.getTariffedPrice(), ((InsuranceProductResponse) response.getBody()).getTariffedPrice());
    }


    @Test
    public void testCreateResidencialInsuranceProduct() throws Exception {
        InsuranceProductRequest request = new InsuranceProductRequest("Teste1", "Residencial", BigDecimal.TEN);
        InsuranceProductResponse productResponse = new InsuranceProductResponse("4", "Seguro Residencial", "Residencial", BigDecimal.valueOf(800.00), BigDecimal.valueOf(900.00));

        when(createInsuranceProductUseCase.execute(any(InsuranceProduct.class))).thenReturn(productResponse);

        ResponseEntity<?> response = controller.createInsuranceProducts(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(productResponse.getId(), ((InsuranceProductResponse) response.getBody()).getId());
        assertEquals(productResponse.getName(), ((InsuranceProductResponse) response.getBody()).getName());
        assertEquals(productResponse.getCategory(), ((InsuranceProductResponse) response.getBody()).getCategory());
        assertEquals(productResponse.getBasePrice(), ((InsuranceProductResponse) response.getBody()).getBasePrice());
        assertEquals(productResponse.getTariffedPrice(), ((InsuranceProductResponse) response.getBody()).getTariffedPrice());
    }

    @Test
    public void testCreatePatrimonialInsuranceProduct() throws Exception {
        InsuranceProductRequest request = new InsuranceProductRequest("Teste1", "Patrimonial", BigDecimal.TEN);
        InsuranceProductResponse productResponse = new InsuranceProductResponse("5", "Seguro Patrimonial", "Patrimonial", BigDecimal.valueOf(850.00), BigDecimal.valueOf(900.00));

        when(createInsuranceProductUseCase.execute(any(InsuranceProduct.class))).thenReturn(productResponse);

        ResponseEntity<?> response = controller.createInsuranceProducts(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(productResponse.getId(), ((InsuranceProductResponse) response.getBody()).getId());
        assertEquals(productResponse.getName(), ((InsuranceProductResponse) response.getBody()).getName());
        assertEquals(productResponse.getCategory(), ((InsuranceProductResponse) response.getBody()).getCategory());
        assertEquals(productResponse.getBasePrice(), ((InsuranceProductResponse) response.getBody()).getBasePrice());
        assertEquals(productResponse.getTariffedPrice(), ((InsuranceProductResponse) response.getBody()).getTariffedPrice());
    }

    @Test
    public void testUpdatePatrimonialInsuranceProduct() throws Exception {
        InsuranceProductRequest request = new InsuranceProductRequest("Teste1", "Patrimonial", BigDecimal.TEN);

        ResponseEntity<?> response = controller.updateInsuranceProducts(1L, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    public void testUpdateInsuranceProductWithInvalidCategory() throws Exception {
        InsuranceProductRequest request = new InsuranceProductRequest("Teste1", "null", BigDecimal.TEN);
        ResponseEntity<?> response =  controller.updateInsuranceProducts(1L, request);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Obrigatório informar uma categoria válida.", ((InsuranceProductErrorResponse) response.getBody()).getErrorMessage());
    }
    @Test
    public void testUpdateInsuranceProductWithNotFoundId() throws Exception {
        InsuranceProductRequest request = new InsuranceProductRequest("Teste1", "Vida", BigDecimal.TEN);
        doThrow(NotFoundIdException.class).when(updateInsuranceProductUseCase).execute(any(),any());
        ResponseEntity<?> response =  controller.updateInsuranceProducts(1L, request);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}