package application.calculei.usecase.cdi;

import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.usecase.cdi.dto.CalculateCdiBetweenDateRequest;
import application.calculei.usecase.cdi.dto.CalculateCdiBetweenDateResponse;
import application.calculei.usecase.exceptions.DataNotFoundException;
import application.calculei.usecase.exceptions.InvalidPeriodException;
import application.calculei.usecase.exceptions.InvalidValueException;
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

            //WHEN
            when(repository.findByDataInitBetween(startDate, endDate.minusDays(1))).thenReturn(List.of(index1, index2));
            CalculateCdiBetweenDateResponse response = useCase.execute(request);

            //THEN
            // Matemática esperada:
            // Fator acumulado = 1.01 * 1.02 = 1.0302
            // Valor Final = 1000 * 1.0302 = 1030.20
            assertAll(
                    () -> assertNotNull(response),
                    () -> assertEquals(new BigDecimal("1030.20"), response.valueFinal(), "o valor final está calculado incorretamente."),
                    () -> assertEquals(new BigDecimal("1.03020000"), response.accumulatedFactor(), "o percentual está calculado incorretamente."),
                    () -> assertEquals(startDate, response.startDate(), "a data inicial não está igual."),
                    () -> assertEquals(endDate, response.endDate(), "a data final não está igual."),
                    () -> assertEquals(10, response.businessDays(), "o número de dias úteis não está correto.")
            );
            verify(repository, times(1)).findByDataInitBetween(startDate, endDate.minusDays(1));
        }
    }

    @Nested
    @DisplayName("Cenários de erro")
    class ErrorScenarios {

        @Test
        @DisplayName("Deve lançar InvalidPeriodException quando a data final for anterior à data inicial")
        void givenEndDateBeforeStartDate_whenExecute_thenThrowInvalidPeriodException() {
            // GIVEN
            LocalDate startDate = LocalDate.now();
            LocalDate endDate = LocalDate.now().minusDays(5);
            CalculateCdiBetweenDateRequest request = new CalculateCdiBetweenDateRequest(1000.0, startDate, endDate);

            // WHEN & THEN
            assertThrows(InvalidPeriodException.class, () -> useCase.execute(request));
            verifyNoInteractions(repository);
        }

        @Test
        @DisplayName("Deve lançar InvalidPeriodException quando a data inicial for no futuro")
        void givenStartDateInFuture_whenExecute_thenThrowInvalidPeriodException() {
            // GIVEN
            LocalDate startDate = LocalDate.now().plusDays(1);
            LocalDate endDate = LocalDate.now().plusDays(5);
            CalculateCdiBetweenDateRequest request = new CalculateCdiBetweenDateRequest(1000.0, startDate, endDate);

            // WHEN & THEN
            assertThrows(InvalidPeriodException.class, () -> useCase.execute(request));
            verifyNoInteractions(repository);
        }

        @Test
        @DisplayName("Deve lançar InvalidPeriodException quando a data final for no futuro")
        void givenEndDateInFuture_whenExecute_thenThrowInvalidPeriodException() {
            // GIVEN
            LocalDate startDate = LocalDate.now().minusDays(5);
            LocalDate endDate = LocalDate.now().plusDays(1);
            CalculateCdiBetweenDateRequest request = new CalculateCdiBetweenDateRequest(1000.0, startDate, endDate);

            // WHEN & THEN
            assertThrows(InvalidPeriodException.class, () -> useCase.execute(request));
            verifyNoInteractions(repository);
        }

        @Test
        @DisplayName("Deve lançar InvalidValueException quando o valor informado for zero")
        void givenAmountIsZero_whenExecute_thenThrowInvalidValueException() {
            // GIVEN
            LocalDate startDate = LocalDate.now().minusDays(5);
            LocalDate endDate = LocalDate.now();
            CalculateCdiBetweenDateRequest request = new CalculateCdiBetweenDateRequest(0.0, startDate, endDate);

            // WHEN & THEN
            assertThrows(InvalidValueException.class, () -> useCase.execute(request));
            verifyNoInteractions(repository);
        }

        @Test
        @DisplayName("Deve lançar InvalidValueException quando o valor informado for negativo")
        void givenAmountIsNegative_whenExecute_thenThrowInvalidValueException() {
            // GIVEN
            LocalDate startDate = LocalDate.now().minusDays(5);
            LocalDate endDate = LocalDate.now();
            CalculateCdiBetweenDateRequest request = new CalculateCdiBetweenDateRequest(-500.0, startDate, endDate);

            // WHEN & THEN
            assertThrows(InvalidValueException.class, () -> useCase.execute(request));
            verifyNoInteractions(repository);
        }

        @Test
        @DisplayName("Deve lançar DataNotFoundException quando o repositório não retornar nenhum índice para o período")
        void givenNoIndexesFound_whenExecute_thenThrowDataNotFoundException() {
            // GIVEN
            LocalDate startDate = LocalDate.now().minusDays(5);
            LocalDate endDate = LocalDate.now();
            CalculateCdiBetweenDateRequest request = new CalculateCdiBetweenDateRequest(1000.0, startDate, endDate);

            //WHEN
            when(repository.findByDataInitBetween(startDate, endDate.minusDays(1))).thenReturn(List.of());

            //THEN
            assertThrows(DataNotFoundException.class, () -> useCase.execute(request));
            verify(repository, times(1)).findByDataInitBetween(startDate, endDate.minusDays(1));
        }
    }
}