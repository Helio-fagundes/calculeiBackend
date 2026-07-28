package application.calculei.usecase.ufir;

import application.calculei.domain.models.Index;
import application.calculei.domain.repository.IndexRepository;
import application.calculei.domain.repository.IndiceBcPort;
import application.calculei.usecase.exceptions.DataNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UfirUseCaseTest {

    @Mock
    private IndexRepository  repository;

    @Mock
    private IndiceBcPort  indiceBcPort;

    @InjectMocks
    private UfirUseCase useCase;

    @Nested
    @DisplayName("Cenários de sucesso")
    class SuccessScenarios {

        @Test
        @DisplayName("Deve pegar o ultimo valor Ufir")
        void givenRequestValid_whenGetLastUfirValue_thenReturnBigDecimal() {

            //GIVEN
            Index index = new Index();
            index.setFator(BigDecimal.TEN);

            //WHEN
            when(repository.findByLastUpdate()).thenReturn(java.util.Optional.of(index));

            //THEN
            assertEquals(BigDecimal.TEN, useCase.getLastUfirValue());
            verify(repository, times(1)).findByLastUpdate();
        }
    }

    @Nested
    @DisplayName("Cenários de falha")
    class FailureScenarios {

        @Test
        @DisplayName("Deve lançar exceção quando não houver valor Ufir")
        void givenNoUfirValue_whenGetLastUfirValue_thenThrowException() {

            //WHEN
            when(repository.findByLastUpdate()).thenReturn(java.util.Optional.empty());

            //THEN
            assertThrows(DataNotFoundException.class, () ->  useCase.getLastUfirValue());
            verify(repository, times(1)).findByLastUpdate();
        }
    }

}