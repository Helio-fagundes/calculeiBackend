package application.calculei.usecase.cdi;

import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.usecase.cdi.dto.CalculateCdiBetweenDateRequest;
import application.calculei.usecase.cdi.dto.CalculateCdiBetweenDateResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CalculateCdiAccumulatedValueBetweenDatesTest {

    @Mock
    private IndexRepository repository;

    @InjectMocks
    private CalculateCdiAccumulatedValueBetweenDates useCase;

    @Nested
    @DisplayName("Cenários de sucesso")
    class SuccessScenarios {

        @Test
        @DisplayName("Deve Retornar Corretamete o Valor Final, Valor Acumulado, Percentual, e os Dias")
        void givenRequestValid_whenExecute_thenReturnCalculateCdiBetweenDateResponse() {

            //GIVEN
            LocalDate startDate = LocalDate.now().minusDays(10);
            LocalDate endDate = LocalDate.now();
            Double amount = 1000.00;
            CalculateCdiBetweenDateRequest request = new CalculateCdiBetweenDateRequest(amount, startDate, endDate);

            Index index1 = new Index();
            index1.setFator(BigDecimal.valueOf(1.01));

            Index index2 = new Index();
            index2.setFator(BigDecimal.valueOf(1.02));

            when(repository.findByDataInitBetween(startDate, endDate)).thenReturn(List.of(index1, index2));

            //WHEN
            CalculateCdiBetweenDateResponse response = useCase.execute(request);

            //THEN
            // Matemática esperada:
            // Fator acumulado = 1.01 * 1.02 = 1.0302
            // Valor Final = 1000 * 1.0302 = 1030.20
            // Percentual Acumulado = (1.0302 - 1) * 100 = 3.020000
            assertAll(
                    () -> assertNotNull(response),
                    () -> assertEquals(new BigDecimal("1030.20"), response.valueFinal(), "o valor final está calculado incorretamente."),
                    () -> assertEquals(new BigDecimal("3.020000"), response.accumulatedFactor(), "o percentual está calculado incorretamente."),
                    () -> assertEquals(startDate, response.startDate(), "a data inicial não está igual."),
                    () -> assertEquals(endDate, response.endDate(), "a data final não está igual."),
                    () -> assertEquals(10, response.businessDays(), "o número de dias úteis não está correto.")
            );
            verify(repository, times(1)).findByDataInitBetween(startDate, endDate);
        }
    }
}