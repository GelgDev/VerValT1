package estacionamento;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CalculadoraTarifaTest {

    private CalculadoraTarifa calculadora;

    @BeforeEach
    void setUp() {
        calculadora = new CalculadoraTarifa();
    }

    @ParameterizedTest(name = "{0}: {1} ate {2} -> {3}")
    @CsvSource({
        "CT01, 2026-09-25T10:00, 2026-09-25T10:00, 0.0",
        "CT02, 2026-09-25T10:00, 2026-09-25T10:20, 0.0",
        "CT03, 2026-09-25T23:59, 2026-09-26T00:19, 0.0"
    })
    void cortesia(String id, String entrada, String saida, double esperado) {
        verificar(entrada, saida, false, esperado);
    }

    @ParameterizedTest(name = "{0}: {1} ate {2} -> {3}")
    @CsvSource({
        "CT04, 2026-09-25T10:00, 2026-09-25T10:21, 15.0",
        "CT05, 2026-09-25T10:00, 2026-09-25T11:00, 15.0"
    })
    void primeiraHora(String id, String entrada, String saida, double esperado) {
        verificar(entrada, saida, false, esperado);
    }

    @ParameterizedTest(name = "{0}: {1} ate {2} -> {3}")
    @CsvSource({
        "CT06, 2026-09-25T10:00, 2026-09-25T11:01, 20.0",
        "CT07, 2026-09-25T10:00, 2026-09-25T12:00, 20.0",
        "CT08, 2026-09-25T10:00, 2026-09-25T12:01, 25.0",
        "CT09, 2026-09-25T10:00, 2026-09-25T13:01, 30.0",
        "CT10, 2026-09-25T23:59, 2026-09-26T01:59, 20.0",
        "CT11, 2026-09-25T08:00, 2026-09-26T01:59, 100.0"
    })
    void horasAdicionais(String id, String entrada, String saida, double esperado) {
        verificar(entrada, saida, false, esperado);
    }

    @ParameterizedTest(name = "{0}: {1} ate {2} -> {3}")
    @CsvSource({
        "CT12, 2026-09-25T10:00, 2026-09-26T08:00, 50.0",
        "CT13, 2026-09-25T23:00, 2026-09-26T08:00, 50.0",
        "CT14, 2026-09-25T10:00, 2026-09-26T20:00, 50.0",
        "CT15, 2026-09-25T10:00, 2026-09-27T01:59, 50.0",
        "CT16, 2026-09-25T10:00, 2026-09-27T08:00, 100.0"
    })
    void pernoite(String id, String entrada, String saida, double esperado) {
        verificar(entrada, saida, false, esperado);
    }

    @ParameterizedTest(name = "{0}: {1} ate {2} -> {3}")
    @CsvSource({
        "CT17, 2026-09-25T10:00, 2026-09-25T10:20, 0.0",
        "CT18, 2026-09-25T10:00, 2026-09-25T10:21, 7.5",
        "CT19, 2026-09-25T10:00, 2026-09-25T11:01, 10.0",
        "CT20, 2026-09-25T10:00, 2026-09-25T12:01, 12.5",
        "CT21, 2026-09-25T10:00, 2026-09-26T08:00, 25.0"
    })
    void clienteVip(String id, String entrada, String saida, double esperado) {
        verificar(entrada, saida, true, esperado);
    }

    @ParameterizedTest(name = "{0}: {1} ate {2} deve ser rejeitado")
    @CsvSource({
        "CT22, 2026-09-25T07:59, 2026-09-25T10:00",
        "CT23, 2026-09-26T00:30, 2026-09-26T09:00",
        "CT24, 2026-09-25T10:00, 2026-09-26T02:00",
        "CT25, 2026-09-25T10:00, 2026-09-26T07:59",
        "CT26, 2026-09-25T10:00, 2026-09-25T09:59"
    })
    void horariosInvalidos(String id, String entrada, String saida) {
        assertThrows(IllegalArgumentException.class,
                () -> calculadora.calcular(LocalDateTime.parse(entrada), LocalDateTime.parse(saida), false));
    }

    @Test
    void dadosNulos() {
        assertThrows(IllegalArgumentException.class, () -> calculadora.calcular(null, null, false));
    }

    private void verificar(String entrada, String saida, boolean vip, double esperado) {
        double valor = calculadora.calcular(LocalDateTime.parse(entrada), LocalDateTime.parse(saida), vip);
        assertEquals(esperado, valor, 0.001);
    }
}
