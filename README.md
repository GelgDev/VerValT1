# VerVal T1: Tarifa de Estacionamento

Trabalho 1 de Verificação e Validação de Software (PUCRS).

Cálculo do valor do ticket de estacionamento com testes JUnit 5 parametrizados, projetados por particionamento em classes de equivalência e análise de valor limite.

## Estrutura

- `src/main/java/estacionamento/CalculadoraTarifa.java`: classe que calcula a tarifa
- `src/test/java/estacionamento/CalculadoraTarifaTest.java`: testes parametrizados
- `docs/casos-de-teste.md`: classes, limites e tabela de casos (exportar para PDF)
- `docs/relatorio-defeitos.md`: modelo do relatório de defeitos (exportar para PDF)

## Como rodar

Requer Java 17 ou superior e Maven.

```
mvn test
```

## Entrega

ZIP com o código-fonte, o PDF da tabela de casos de teste e o PDF do relatório de defeitos.
