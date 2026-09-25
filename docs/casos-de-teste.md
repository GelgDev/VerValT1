# Casos de Teste: Cálculo da Tarifa de Estacionamento

## Decisões de interpretação

1. A cortesia vale até 20 minutos inclusive (20 min paga R$ 0,00, 21 min já paga).
2. Horas adicionais são contadas por hora iniciada: 61 min a 120 min soma R$ 5,00, 121 min a 180 min soma R$ 10,00 e assim por diante.
3. Pernoite: a saída a partir das 08:00 do dia seguinte à entrada já conta como pernoite. Cada manhã (08:00) que passa conta mais um pernoite de R$ 50,00. Com pernoite não se somam horas.
4. O desconto VIP (50%) é aplicado sobre o valor final.
5. Entrada só entre 08:00 e 23:59. Saída proibida entre 02:00 e 07:59. Saída antes da entrada é inválida. Casos inválidos lançam `IllegalArgumentException`.

## Classes de equivalência

| Variável | Classe | Tipo |
|---|---|---|
| Hora de entrada | 08:00 a 23:59 | Válida |
| Hora de entrada | 00:00 a 07:59 | Inválida |
| Hora de saída | 08:00 a 01:59 | Válida |
| Hora de saída | 02:00 a 07:59 | Inválida |
| Ordem | saída igual ou depois da entrada | Válida |
| Ordem | saída antes da entrada | Inválida |
| Permanência | 0 a 20 min (cortesia) | Válida |
| Permanência | 21 a 60 min (valor fixo) | Válida |
| Permanência | mais de 60 min sem pernoite | Válida |
| Permanência | com pernoite | Válida |
| Cliente | VIP / não VIP | Válida |

## Valores limite

| Limite | Valores testados |
|---|---|
| Cortesia | 0, 20, 21 min |
| Primeira hora | 60, 61 min |
| Horas adicionais | 120, 121, 181 min e o máximo sem pernoite (08:00 até 01:59) |
| Pernoite | saída 01:59 e 08:00 do dia seguinte, 01:59 e 08:00 dois dias depois |
| Horário de entrada | 07:59 (inválido), 08:00, 23:59 |
| Horário de saída | 01:59, 02:00 (inválido), 07:59 (inválido), 08:00 |

## Tabela de casos de teste

| ID | Entrada | Saída | VIP | Classe / limite | Saída esperada |
|---|---|---|---|---|---|
| CT01 | 25/09/2026 10:00 | 25/09/2026 10:00 | Não | Cortesia, 0 min | R$ 0,00 |
| CT02 | 25/09/2026 10:00 | 25/09/2026 10:20 | Não | Cortesia, 20 min | R$ 0,00 |
| CT03 | 25/09/2026 23:59 | 26/09/2026 00:19 | Não | Cortesia virando o dia, 20 min | R$ 0,00 |
| CT04 | 25/09/2026 10:00 | 25/09/2026 10:21 | Não | Valor fixo, 21 min | R$ 15,00 |
| CT05 | 25/09/2026 10:00 | 25/09/2026 11:00 | Não | Valor fixo, 60 min | R$ 15,00 |
| CT06 | 25/09/2026 10:00 | 25/09/2026 11:01 | Não | Horas adicionais, 61 min | R$ 20,00 |
| CT07 | 25/09/2026 10:00 | 25/09/2026 12:00 | Não | Horas adicionais, 120 min | R$ 20,00 |
| CT08 | 25/09/2026 10:00 | 25/09/2026 12:01 | Não | Horas adicionais, 121 min | R$ 25,00 |
| CT09 | 25/09/2026 10:00 | 25/09/2026 13:01 | Não | Horas adicionais, 181 min | R$ 30,00 |
| CT10 | 25/09/2026 23:59 | 26/09/2026 01:59 | Não | Entrada 23:59, saída 01:59 | R$ 20,00 |
| CT11 | 25/09/2026 08:00 | 26/09/2026 01:59 | Não | Maior permanência sem pernoite | R$ 100,00 |
| CT12 | 25/09/2026 10:00 | 26/09/2026 08:00 | Não | Pernoite, saída 08:00 | R$ 50,00 |
| CT13 | 25/09/2026 23:00 | 26/09/2026 08:00 | Não | Pernoite com entrada tarde | R$ 50,00 |
| CT14 | 25/09/2026 10:00 | 26/09/2026 20:00 | Não | Pernoite, saída à noite | R$ 50,00 |
| CT15 | 25/09/2026 10:00 | 27/09/2026 01:59 | Não | Um pernoite, limite antes do segundo | R$ 50,00 |
| CT16 | 25/09/2026 10:00 | 27/09/2026 08:00 | Não | Dois pernoites | R$ 100,00 |
| CT17 | 25/09/2026 10:00 | 25/09/2026 10:20 | Sim | VIP cortesia | R$ 0,00 |
| CT18 | 25/09/2026 10:00 | 25/09/2026 10:21 | Sim | VIP valor fixo | R$ 7,50 |
| CT19 | 25/09/2026 10:00 | 25/09/2026 11:01 | Sim | VIP horas adicionais | R$ 10,00 |
| CT20 | 25/09/2026 10:00 | 25/09/2026 12:01 | Sim | VIP horas adicionais | R$ 12,50 |
| CT21 | 25/09/2026 10:00 | 26/09/2026 08:00 | Sim | VIP pernoite | R$ 25,00 |
| CT22 | 25/09/2026 07:59 | 25/09/2026 10:00 | Não | Entrada antes de abrir | Exceção |
| CT23 | 26/09/2026 00:30 | 26/09/2026 09:00 | Não | Entrada de madrugada | Exceção |
| CT24 | 25/09/2026 10:00 | 26/09/2026 02:00 | Não | Saída 02:00 | Exceção |
| CT25 | 25/09/2026 10:00 | 26/09/2026 07:59 | Não | Saída 07:59 | Exceção |
| CT26 | 25/09/2026 10:00 | 25/09/2026 09:59 | Não | Saída antes da entrada | Exceção |
| CT27 | nula | nula | Não | Dados ausentes | Exceção |
