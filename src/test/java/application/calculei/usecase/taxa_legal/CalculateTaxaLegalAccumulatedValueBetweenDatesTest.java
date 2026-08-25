package application.calculei.usecase.taxa_legal;

import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.usecase.exceptions.DataNotFoundException;
import application.calculei.usecase.exceptions.InvalidPeriodException;
import application.calculei.usecase.exceptions.InvalidValueException;
import application.calculei.usecase.taxa_legal.dto.CalculateTaxaLegalBetweenDateRequest;
import application.calculei.usecase.taxa_legal.dto.CalculateTaxaLegalBetweenDateResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CalculateTaxaLegalAccumulatedValueBetweenDatesTest {

    @Mock
    private IndexRepository repository;

    @InjectMocks
    private CalculateTaxaLegalAccumulatedValueBetweenDates useCase;

    @Nested
    @DisplayName("Cenários de sucesso")
    class SuccessScenarios {

        @Test
        @DisplayName("Deve Retornar Corretamete o Valor Final, Valor Acumulado, Percentual, e os Dias")
        void givenRequestValid_whenExecute_thenReturnCalculateIpcaeBetweenDateResponse() {

            //GIVEN
            LocalDate startDate = LocalDate.now().minusMonths(3).withDayOfMonth(1);
            LocalDate endDate = LocalDate.now();
            Double amount = 1000.0;
            CalculateTaxaLegalBetweenDateRequest request = new CalculateTaxaLegalBetweenDateRequest(amount, startDate, endDate);

            LocalDate newDate = YearMonth.from(endDate).atEndOfMonth();

            Index index1 =  new Index();
            index1.setFator(BigDecimal.valueOf(1.01));
            index1.setDataInit(startDate);

            Index index2 =  new Index();
            index2.setFator(BigDecimal.valueOf(1.02));
            index2.setDataInit(startDate.plusMonths(1));

            //WHEN
            when(repository.findByDataInitBetween(startDate, newDate)).thenReturn(List.of(index1, index2));
            CalculateTaxaLegalBetweenDateResponse response = useCase.execute(request);

            //THEN
            // Matemática esperada (Juros Simples - Soma das taxas):
            // Taxa 1 = 1.01 - 1 = 0.01
            // Taxa 2 = 1.02 - 1 = 0.02
            // Soma das taxas = 0.03 -> Fator acumulado = 1 + 0.03 = 1.03
            // Valor Final = 1000 * 1.03 = 1030.00
            assertAll(
                    () -> assertNotNull(response),
                    () -> assertEquals(new BigDecimal("1030.00"), response.finalValue(), "o valor final está calculado incorretamente."),
                    () -> assertEquals(new BigDecimal("1.03000000"), response.accumulatedFactor(), "o percentual está calculado incorretamente."),
                    () -> assertEquals(startDate, response.startDate(), "a data inicial não está igual."),
                    () -> assertEquals(endDate, response.endDate(), "a data final não está igual."),
                    () -> assertEquals(114, response.businessDays(), "o número de dias úteis não está correto.")
            );
            verify(repository, times(1)).findByDataInitBetween(startDate, newDate);
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
            CalculateTaxaLegalBetweenDateRequest request = new CalculateTaxaLegalBetweenDateRequest(amount, startDate, endDate);

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
            CalculateTaxaLegalBetweenDateRequest request = new CalculateTaxaLegalBetweenDateRequest(amount, startDate, endDate);

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
            CalculateTaxaLegalBetweenDateRequest request = new CalculateTaxaLegalBetweenDateRequest(amount, startDate, endDate);

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
            CalculateTaxaLegalBetweenDateRequest request = new CalculateTaxaLegalBetweenDateRequest(amount, startDate, endDate);

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
            CalculateTaxaLegalBetweenDateRequest request = new CalculateTaxaLegalBetweenDateRequest(amount, startDate, endDate);

            //WHEN & THEN
            assertThrows(InvalidValueException.class, () -> useCase.execute(request));
            verifyNoInteractions(repository);
        }

        @Test
        @DisplayName("Deve lançar DataNotFoundException quando não houver índices para o período informado")
        void givenNoIndexesForPeriod_whenExecute_thenThrowDataNotFoundException() {
            //GIVEN
            LocalDate startDate = LocalDate.now().minusMonths(2).withDayOfMonth(1);
            LocalDate endDate = LocalDate.now();
            Double amount = 1000.0;
            CalculateTaxaLegalBetweenDateRequest request = new CalculateTaxaLegalBetweenDateRequest(amount, startDate, endDate);

            LocalDate newDate = YearMonth.from(request.endDate()).atEndOfMonth();

            //WHEN
            when(repository.findByDataInitBetween(startDate.withDayOfMonth(1), newDate)).thenReturn(List.of());

            //THEN
            assertThrows(DataNotFoundException.class, () -> useCase.execute(request));
            verify(repository, times(1)).findByDataInitBetween(startDate, newDate);
        }

    }

}