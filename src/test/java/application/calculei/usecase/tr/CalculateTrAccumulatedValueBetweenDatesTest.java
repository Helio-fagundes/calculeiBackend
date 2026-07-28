package application.calculei.usecase.tr;

import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.domain.value_object.DateUtils;
import application.calculei.usecase.exceptions.DataNotFoundException;
import application.calculei.usecase.exceptions.InvalidPeriodException;
import application.calculei.usecase.exceptions.InvalidValueException;
import application.calculei.usecase.tr.dto.CalculateTrBetweenDateRequest;
import application.calculei.usecase.tr.dto.CalculateTrBetweenDateResponse;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CalculateTrAccumulatedValueBetweenDatesTest {

    @Mock
    private IndexRepository repository;

    @InjectMocks
    private CalculateTrAccumulatedValueBetweenDates useCase;

    @Nested
    @DisplayName("Cenários de sucesso")
    class SuccessScenarios {

        @Test
        @DisplayName("Deve Retornar Corretamente o Valor Final, Valor Acumulado, Percentual, e os Dias")
        void givenRequestValid_whenExecute_thenReturnCalculateTrBetweenDateResponse() {

            // GIVEN
            LocalDate startDate = LocalDate.of(2024, 1, 1);
            LocalDate endDate = LocalDate.of(2024, 3, 1);
            Double amount = 1000.0;
            CalculateTrBetweenDateRequest request = new CalculateTrBetweenDateRequest(amount, startDate, endDate);

            Index index1 = new Index();
            index1.setFator(new BigDecimal("1.01"));
            index1.setDataInit(LocalDate.of(2024, 1, 1));

            Index index2 = new Index();
            index2.setFator(new BigDecimal("1.02"));
            index2.setDataInit(LocalDate.of(2024, 2, 1));

            when(repository.findByDataInitBetween(startDate, endDate.minusDays(1)))
                    .thenReturn(List.of(index1, index2));

            // WHEN
            CalculateTrBetweenDateResponse response = useCase.execute(request);

            // THEN
            // Fator acumulado = 1.01 * 1.02 = 1.0302
            // Valor Final = 1000 * 1.0302 = 1030.20
            assertAll(
                    () -> assertNotNull(response),
                    () -> assertEquals(new BigDecimal("1030.20"), response.finalValue(), "o valor final está calculado incorretamente."),
                    () -> assertEquals(new BigDecimal("1.03020000"), response.accumulatedValue(), "o percentual está calculado incorretamente."),
                    () -> assertEquals(startDate, response.startDate(), "a data inicial não está igual."),
                    () -> assertEquals(endDate, response.endDate(), "a data final não está igual."),
                    () -> assertEquals(DateUtils.businessDays(startDate, endDate), response.businessDays(), "o número de dias úteis não está correto.")
            );

            verify(repository, times(1)).findByDataInitBetween(startDate, endDate.minusDays(1));
        }
    }

    @Nested
    @DisplayName("Cenários de erro")
    class ErrorScenarios {

        @Test
        @DisplayName("Deve lançar InvalidPeriodException quando a data final for anterior a data inicial")
        void givenEndDateBeforeStartDate_whenExecute_thenThrowInvalidPeriodException() {
            //GIVEN
            LocalDate startDate = LocalDate.now();
            LocalDate endDate = LocalDate.now().minusDays(1);
            Double amount = 1000.0;
            CalculateTrBetweenDateRequest request = new CalculateTrBetweenDateRequest(amount, startDate, endDate);

            //WHEN & THEN
            assertThrows(InvalidPeriodException.class, () -> useCase.execute(request));
            verifyNoInteractions(repository);
        }

        @Test
        @DisplayName("Deve lançar InvalidPeriodException quando a data inicial for posterior a data atual")
        void givenStartDateAfterCurrentDate_whenExecute_thenThrowInvalidPeriodException() {
            //GIVEN
            LocalDate startDate = LocalDate.now().plusDays(1);
            LocalDate endDate = LocalDate.now().plusDays(2);
            Double amount = 1000.0;
            CalculateTrBetweenDateRequest request = new CalculateTrBetweenDateRequest(amount, startDate, endDate);

            //WHEN & THEN
            assertThrows(InvalidPeriodException.class, () -> useCase.execute(request));
            verifyNoInteractions(repository);
        }

        @Test
        @DisplayName("Deve lançar InvalidPeriodException quando a data final for posterior a data atual")
        void givenEndDateAfterCurrentDate_whenExecute_thenThrowInvalidPeriodException() {
            //GIVEN
            LocalDate startDate = LocalDate.now();
            LocalDate endDate = LocalDate.now().plusDays(2);
            Double amount = 1000.0;
            CalculateTrBetweenDateRequest request = new CalculateTrBetweenDateRequest(amount, startDate, endDate);

            //WHEN & THEN
            assertThrows(InvalidPeriodException.class, () -> useCase.execute(request));
            verifyNoInteractions(repository);
        }

        @Test
        @DisplayName("Deve lançar InvalidValueException quando o valor informado for zero")
        void givenAmountIsZero_whenExecute_thenThrowInvalidValueException() {
            //GIVEN
            LocalDate startDate = LocalDate.now().minusDays(10);
            LocalDate endDate = LocalDate.now();
            Double amount = 0.0;
            CalculateTrBetweenDateRequest request = new CalculateTrBetweenDateRequest(amount, startDate, endDate);

            //WHEN & THEN
            assertThrows(InvalidValueException.class, () -> useCase.execute(request));
            verifyNoInteractions(repository);
        }

        @Test
        @DisplayName("Deve lançar InvalidValueException quando o valor informado for negativo")
        void givenAmountIsNegative_whenExecute_thenThrowInvalidValueException() {
            //GIVEN
            LocalDate startDate = LocalDate.now().minusDays(10);
            LocalDate endDate = LocalDate.now();
            Double amount = -1000.0;
            CalculateTrBetweenDateRequest request = new CalculateTrBetweenDateRequest(amount, startDate, endDate);

            //WHEN & THEN
            assertThrows(InvalidValueException.class, () -> useCase.execute(request));
            verifyNoInteractions(repository);
        }

        @Test
        @DisplayName("Deve lançar DataNotFoundException quando não houver índices para o período informado")
        void givenNoIndexesForPeriod_whenExecute_thenThrowDataNotFoundException() {
            //GIVEN
            LocalDate startDate = LocalDate.now().minusDays(10);
            LocalDate endDate = LocalDate.now();
            Double amount = 1000.0;
            CalculateTrBetweenDateRequest request = new CalculateTrBetweenDateRequest(amount, startDate, endDate);

            //WHEN
            when(repository.findByDataInitBetween(startDate, endDate.minusDays(1))).thenReturn(List.of());

            //THEN
            assertThrows(DataNotFoundException.class, () -> useCase.execute(request));
            verify(repository, times(1)).findByDataInitBetween(startDate, endDate.minusDays(1));
        }

    }
}