# Margem de Erro dos Índices

Base de cálculo: R$ 1.000.000.000,00 — período 01/2013 a 01/2020

| Índice        | Erro (R$) | Margem (%)    | Série SGS |
|---------------|-----------|---------------|-----------|
| IGP-DI        | R$ 6      | ~0.0000004%   | 190       |
| IGP-M         | R$ 13     | ~0.0000009%   | 28655     |
| INPC          | R$ 35     | ~0.0000023%   | 188       |
| IPCA          | R$ 11     | ~0.0000007%   | 433       |
| IPCA-E        | R$ 27     | ~0.0000018%   | 10764     |
| IPCA-BR       | R$ 24     | ~0.0000016%   | 191       |
| Poupança Nova | R$ 23     | ~0.0000015%   | 196       |
| TR            | R$ 31     | ~0.0000021%   | 7811      |

## Causa
A API pública do BCB retorna valores arredondados (2 a 4 casas decimais).
O BCB utiliza mais casas decimais internamente, sem disponibilizá-las publicamente.
Exceção: IGP-M utiliza a série 28655 que disponibiliza 10 casas decimais,
resultando na menor margem de erro entre os índices mensais.

## Impacto prático
Para valores abaixo de R$ 43.000.000,00 a diferença é inferior a R$ 1,00
em todos os índices.
