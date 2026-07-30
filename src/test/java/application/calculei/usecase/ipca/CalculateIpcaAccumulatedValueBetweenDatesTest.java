package application.calculei.usecase.ipca;

import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.usecase.exceptions.DataNotFoundException;
import application.calculei.usecase.exceptions.InvalidPeriodException;
import application.calculei.usecase.exceptions.InvalidValueException;
import application.calculei.usecase.ipca.dto.CalculateIpcaBetweenDateRequest;
import application.calculei.usecase.ipca.dto.CalculateIpcaBetweenDateResponse;
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
class CalculateIpcaAccumulatedValueBetweenDatesTest {

    @Mock
    private IndexRepository repository;

    @InjectMocks
    private CalculateIpcaAccumulatedValueBetweenDates useCase;

    @Nested
    @DisplayName("Cenários de sucesso")
    class SuccessScenarios {

        @Test
        @DisplayName("Deve Retornar Corretamete o Valor Final, Valor Acumulado, Percentual, e os Dias")
        void givenRequestValid_whenExecute_thenReturnCalculateIpcaBetweenDateResponse() {

            //GIVEN
            LocalDate startDate = LocalDate.now().minusDays(3);
            LocalDate endDate = LocalDate.now();
            Double amount = 1000.0;
            CalculateIpcaBetweenDateRequest request = new CalculateIpcaBetweenDateRequest(amount, startDate, endDate);

            Index index1 = new Index();
            index1.setFator(BigDecimal.valueOf(1.01));

            Index index2 = new Index();
            index2.setFator(BigDecimal.valueOf(1.02));

            //WHEN
            when(repository.findByDataInitBetween(startDate, endDate)).thenReturn(List.of(index1, index2));
            CalculateIpcaBetweenDateResponse response = useCase.execute(request);

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
                    () -> assertEquals(3, response.businessDays(), "o número de dias úteis não está correto.")
            );
            verify(repository, times(1)).findByDataInitBetween(startDate, endDate);
        }
    }

    @Nested
    @DisplayName("Cenários de falha")
    class FailureScenarios {

        @Test
        @DisplayName("Deve lançar InvalidPeriodException quando a data inicial for posterior a data atual")
        void  givenStartDateAfterCurrentDate_whenExecute_thenThrowInvalidPeriodException() {

            //GIVEN
            LocalDate startDate = LocalDate.now().plusDays(3);
            LocalDate endDate = LocalDate.now().plusDays(5);
            Double amount = 1000.0;
            CalculateIpcaBetweenDateRequest request = new CalculateIpcaBetweenDateRequest(amount, startDate, endDate);

            //WHEN & THEN
            assertThrows(InvalidPeriodException.class, () -> useCase.execute(request));
            verifyNoInteractions(repository);
        }

        @Test
        @DisplayName("Deve lançar InvalidPeriodException quando a data final for posterior a data atual")
        void givenEndDateAfterCurrentDate_whenExecute_thenThrowInvalidPeriodException() {

            //GIVEN
            LocalDate startDate = LocalDate.now().minusDays(5);
            LocalDate endDate = LocalDate.now().plusDays(3);
            Double amount = 1000.0;
            CalculateIpcaBetweenDateRequest request = new CalculateIpcaBetweenDateRequest(amount, startDate, endDate);

            //WHEN & THEN
            assertThrows(InvalidPeriodException.class, () -> useCase.execute(request));
            verifyNoInteractions(repository);
        }

        @Test
        @DisplayName("Deve lançar InvalidPeriodException quando a data final for anterior a data inicial")
        void givenEndDateBeforeStartDate_whenExecute_thenThrowInvalidPeriodException() {

            //GIVEN
            LocalDate startDate = LocalDate.now();
            LocalDate endDate = LocalDate.now().minusDays(3);
            Double amount = 1000.0;
            CalculateIpcaBetweenDateRequest request = new CalculateIpcaBetweenDateRequest(amount, startDate, endDate);

            //WHEN & THEN
            assertThrows(InvalidPeriodException.class, () -> useCase.execute(request));
            verifyNoInteractions(repository);
        }

        @Test
        @DisplayName("Deve lançar InvalidValueException quando o valor informado for zero")
        void givenAmountIsZero_whenExecute_thenThrowInvalidValueException() {

            //GIVEN
            LocalDate startDate = LocalDate.now().minusDays(5);
            LocalDate endDate = LocalDate.now();
            Double amount = 0.0;
            CalculateIpcaBetweenDateRequest request = new CalculateIpcaBetweenDateRequest(amount, startDate, endDate);

            //WHEN & THEN
            assertThrows(InvalidValueException.class, () -> useCase.execute(request));
            verifyNoInteractions(repository);
        }

        @Test
        @DisplayName("Deve lançar InvalidValueException quando o valor informado for negativo")
        void givenAmountIsNegative_whenExecute_thenThrowInvalidValueException() {

            //GIVEN
            LocalDate startDate = LocalDate.now().minusDays(5);
            LocalDate endDate = LocalDate.now();
            Double amount = -1000.0;
            CalculateIpcaBetweenDateRequest request = new CalculateIpcaBetweenDateRequest(amount, startDate, endDate);

            //WHEN & THEN
            assertThrows(InvalidValueException.class, () -> useCase.execute(request));
            verifyNoInteractions(repository);
        }

        @Test
        @DisplayName("Deve lançar DataNotFoundException quando não houver índices no período informado")
        void givenNoIndexesInPeriod_whenExecute_thenThrowDataNotFoundException() {

            //GIVEN
            LocalDate startDate = LocalDate.now().minusDays(5);
            LocalDate endDate = LocalDate.now();
            Double amount = 1000.0;
            CalculateIpcaBetweenDateRequest request = new CalculateIpcaBetweenDateRequest(amount, startDate, endDate);

            //WHEN
            when(repository.findByDataInitBetween(startDate, endDate)).thenReturn(List.of());

            //THEN
            assertThrows(DataNotFoundException.class, () -> useCase.execute(request));
            verify(repository, times(1)).findByDataInitBetween(startDate, endDate);
        }
    }
}