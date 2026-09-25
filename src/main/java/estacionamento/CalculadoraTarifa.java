package estacionamento;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class CalculadoraTarifa {

    public static final int MINUTOS_CORTESIA = 20;
    public static final double VALOR_PRIMEIRA_HORA = 15.0;
    public static final double VALOR_HORA_ADICIONAL = 5.0;
    public static final double VALOR_PERNOITE = 50.0;

    private static final LocalTime ABERTURA = LocalTime.of(8, 0);
    private static final LocalTime FECHAMENTO = LocalTime.of(2, 0);

    public double calcular(LocalDateTime entrada, LocalDateTime saida, boolean vip) {
        validar(entrada, saida);

        double valor = calcularValorBase(entrada, saida);
        if (vip) {
            valor = valor * 0.5;
        }
        return valor;
    }

    private double calcularValorBase(LocalDateTime entrada, LocalDateTime saida) {
        int pernoites = contarPernoites(entrada, saida);
        if (pernoites > 0) {
            return pernoites * VALOR_PERNOITE;
        }

        long minutos = Duration.between(entrada, saida).toMinutes();
        if (minutos <= MINUTOS_CORTESIA) {
            return 0.0;
        }
        if (minutos <= 60) {
            return VALOR_PRIMEIRA_HORA;
        }

        // cada hora iniciada depois da primeira conta como uma hora inteira
        long horasAdicionais = (minutos - 60 + 59) / 60;
        return VALOR_PRIMEIRA_HORA + horasAdicionais * VALOR_HORA_ADICIONAL;
    }

    // conta quantas vezes o carro "passou" das 08:00 de um dia seguinte
    private int contarPernoites(LocalDateTime entrada, LocalDateTime saida) {
        LocalDate diaSaida = saida.toLocalDate();
        if (saida.toLocalTime().isBefore(ABERTURA)) {
            diaSaida = diaSaida.minusDays(1);
        }
        return (int) ChronoUnit.DAYS.between(entrada.toLocalDate(), diaSaida);
    }

    private void validar(LocalDateTime entrada, LocalDateTime saida) {
        if (entrada == null || saida == null) {
            throw new IllegalArgumentException("Entrada e saida sao obrigatorias");
        }
        if (entrada.toLocalTime().isBefore(ABERTURA)) {
            throw new IllegalArgumentException("Entrada fora do horario permitido");
        }
        if (saida.isBefore(entrada)) {
            throw new IllegalArgumentException("Saida anterior a entrada");
        }
        LocalTime horaSaida = saida.toLocalTime();
        if (!horaSaida.isBefore(FECHAMENTO) && horaSaida.isBefore(ABERTURA)) {
            throw new IllegalArgumentException("Saida fora do horario permitido");
        }
    }
}
