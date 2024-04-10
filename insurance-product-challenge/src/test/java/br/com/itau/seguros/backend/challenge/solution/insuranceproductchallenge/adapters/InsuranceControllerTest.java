//package br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.adapters;
//
//import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.domain.InsuranceCategory;
//import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.domain.InsuranceProduct;
//import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.usecases.CreateInsuranceProductUseCase;
//import br.com.itau.seguros.backend.challenge.solution.insuranceproductchallenge.usecases.impl.CreateInsuranceProductUseCaseImpl;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import java.math.BigDecimal;
//
//import static org.mockito.Mockito.when;
//@SpringJUnitConfig
//@WebMvcTest(InsuranceController.class)
//public class InsuranceControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Mock
//    private CreateInsuranceProductUseCaseImpl createInsuranceProductUseCase;
//
//    @InjectMocks
//    private InsuranceController insuranceController;
//
//    @Test
//    public void testCalculateTariff() throws Exception {
//        InsuranceProduct product = new InsuranceProduct("Insurance product 1", InsuranceCategory.VIDA, BigDecimal.valueOf(100.00));
//
//        when(createInsuranceProductUseCase.execute(product)).thenReturn(BigDecimal.valueOf(1500.0));
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .post("/calculate")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{ \"name\": \"Insurance product 1\", \"category\": \"VIDA\", \"basePrice\": 100.00 }")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isCreated());
//    }
//}