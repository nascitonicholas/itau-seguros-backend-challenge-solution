package br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.adapters;

import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.adapters.response.InsuranceProductResponse;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.domain.InsuranceProduct;
import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.usecases.impl.CreateInsuranceProductUseCaseImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InsuranceController.class)
public class InsuranceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateInsuranceProductUseCaseImpl createInsuranceProductUseCase;

    @Test
    public void testCreateInsuranceProductWithEmptyName() throws Exception {
        String requestBody = "{ \"nome\": \"\", \"categoria\": \"Vida\", \"preco_base\": 100.99 }";

        mockMvc.perform(post("/insurance-products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.descricao_erro").value("Obrigatório informar um nome para o produto."));
    }

    @Test
    public void testCreateInsuranceProductWithNullName() throws Exception {
        String requestBody = "{ \"nome\": null, \"categoria\": \"Vida\", \"preco_base\": 100.99 }";

        mockMvc.perform(post("/insurance-products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.descricao_erro").value("Obrigatório informar um nome para o produto."));
    }

    @Test
    public void testCreateInsuranceProductWithEmptyCategory() throws Exception {
        String requestBody = "{ \"nome\": \"Teste1\", \"categoria\": \"\", \"preco_base\": 100.99 }";

        mockMvc.perform(post("/insurance-products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.descricao_erro").value("Obrigatório informar uma categoria válida."));
    }

    @Test
    public void testCreateInsuranceProductWithNullCategory() throws Exception {
        String requestBody = "{ \"nome\": \"Teste\", \"categoria\": null, \"preco_base\": 100.99 }";

        mockMvc.perform(post("/insurance-products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.descricao_erro").value("Obrigatório informar uma categoria válida."));
    }

    @Test
    public void testCreateInsuranceProductWithNullBasePrice() throws Exception {
        String requestBody = "{ \"nome\": \"Teste1\", \"categoria\": \"Vida\", \"preco_base\": null }";

        mockMvc.perform(post("/insurance-products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.descricao_erro").value("Obrigatório informar um preço base maior que zero."));
    }

    @Test
    public void testCreateInsuranceProductWithBasePriceLessThanZero() throws Exception {
        String requestBody = "{ \"nome\": \"Teste1\", \"categoria\": \"VIDA\", \"preco_base\": -100.99 }";

        mockMvc.perform(post("/insurance-products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.descricao_erro").value("Obrigatório informar um preço base maior que zero."));
    }

    @Test
    public void testCreateInsuranceProductWithBasePriceEqualZero() throws Exception {
        String requestBody = "{ \"nome\": \"Teste1\", \"categoria\": \"Vida\", \"preco_base\": 0 }";

        mockMvc.perform(post("/insurance-products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.descricao_erro").value("Obrigatório informar um preço base maior que zero."));
    }

    @Test
    public void testCreateInsuranceProductWithInternalServerError() throws Exception {
        String requestBody = "{ \"nome\": \"Teste1\", \"categoria\": \"Vida\", \"preco_base\": 10 }";

        when(createInsuranceProductUseCase.execute(any(InsuranceProduct.class))).thenThrow(RuntimeException.class);

        mockMvc.perform(post("/insurance-products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testCreateInsuranceProductWithInvalidCategory() throws Exception {
        String requestBody = "{ \"nome\": \"Teste\", \"categoria\": \"categoria\", \"preco_base\": 100.99 }";

        mockMvc.perform(post("/insurance-products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.descricao_erro").value("Obrigatório informar uma categoria válida."));
    }

    @Test
    public void testCreateVidaInsuranceProduct() throws Exception {
        String requestBody = "{ \"nome\": \"Seguro Vida\", \"categoria\": \"Vida\", \"preco_base\": 100.99 }";

        InsuranceProductResponse response = new InsuranceProductResponse("1", "Seguro Vida", "Vida", BigDecimal.valueOf(100.99), BigDecimal.valueOf(102));
        when(createInsuranceProductUseCase.execute(any(InsuranceProduct.class))).thenReturn(response);

        mockMvc.perform(post("/insurance-products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(response.getId()))
                .andExpect(jsonPath("$.nome").value(response.getName()))
                .andExpect(jsonPath("$.categoria").value(response.getCategory()))
                .andExpect(jsonPath("$.preco_base").value(response.getBasePrice()))
                .andExpect(jsonPath("$.preco_tarifado").value(response.getTariffedPrice()));
    }

    @Test
    public void testCreateAutoInsuranceProduct() throws Exception {
        String requestBody = "{ \"nome\": \"Seguro Auto\", \"categoria\": \"Auto\", \"preco_base\": 10.99 }";

        InsuranceProductResponse response = new InsuranceProductResponse("2", "Seguro Auto", "Auto", BigDecimal.valueOf(10.99), BigDecimal.valueOf(12));
        when(createInsuranceProductUseCase.execute(any(InsuranceProduct.class))).thenReturn(response);

        mockMvc.perform(post("/insurance-products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(response.getId()))
                .andExpect(jsonPath("$.nome").value(response.getName()))
                .andExpect(jsonPath("$.categoria").value(response.getCategory()))
                .andExpect(jsonPath("$.preco_base").value(response.getBasePrice()))
                .andExpect(jsonPath("$.preco_tarifado").value(response.getTariffedPrice()));
    }

    @Test
    public void testCreateViagemInsuranceProduct() throws Exception {
        String requestBody = "{ \"nome\": \"Seguro Viagem\", \"categoria\": \"Viagem\", \"preco_base\": 10.9 }";

        InsuranceProductResponse response = new InsuranceProductResponse("3", "Seguro Viagem", "Viagem", BigDecimal.valueOf(10.9), BigDecimal.valueOf(12.565));
        when(createInsuranceProductUseCase.execute(any(InsuranceProduct.class))).thenReturn(response);

        mockMvc.perform(post("/insurance-products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(response.getId()))
                .andExpect(jsonPath("$.nome").value(response.getName()))
                .andExpect(jsonPath("$.categoria").value(response.getCategory()))
                .andExpect(jsonPath("$.preco_base").value(response.getBasePrice()))
                .andExpect(jsonPath("$.preco_tarifado").value(response.getTariffedPrice()));
    }


    @Test
    public void testCreateResidencialInsuranceProduct() throws Exception {
        String requestBody = "{ \"nome\": \"Seguro Residencial\", \"categoria\": \"Residencial\", \"preco_base\": 800.00 }";

        InsuranceProductResponse response = new InsuranceProductResponse("4", "Seguro Residencial", "RESIDENCIAL", BigDecimal.valueOf(800.00), BigDecimal.valueOf(900.00));
        when(createInsuranceProductUseCase.execute(any(InsuranceProduct.class))).thenReturn(response);

        mockMvc.perform(post("/insurance-products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(response.getId()))
                .andExpect(jsonPath("$.nome").value(response.getName()))
                .andExpect(jsonPath("$.categoria").value(response.getCategory()))
                .andExpect(jsonPath("$.preco_base").value(response.getBasePrice()))
                .andExpect(jsonPath("$.preco_tarifado").value(response.getTariffedPrice()));
    }

    @Test
    public void testCreatePatrimonialInsuranceProduct() throws Exception {
        String requestBody = "{ \"nome\": \"Seguro Patrimonial\", \"categoria\": \"Patrimonial\", \"preco_base\": 850.00 }";

        InsuranceProductResponse response = new InsuranceProductResponse("5", "Seguro Patrimonial", "PATRIMONIAL", BigDecimal.valueOf(850.00), BigDecimal.valueOf(900.00));
        when(createInsuranceProductUseCase.execute(any(InsuranceProduct.class))).thenReturn(response);

        mockMvc.perform(post("/insurance-products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(response.getId()))
                .andExpect(jsonPath("$.nome").value(response.getName()))
                .andExpect(jsonPath("$.categoria").value(response.getCategory()))
                .andExpect(jsonPath("$.preco_base").value(response.getBasePrice()))
                .andExpect(jsonPath("$.preco_tarifado").value(response.getTariffedPrice()));
    }
}