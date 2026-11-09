# Sentinela do Território — Risco de Alagamento (CitizenTech)

Global Solution 2º/2026 — Object-Oriented Programming (FIAP). Plataforma de monitoramento territorial que lê sensores, prevê o comportamento do nível do rio e emite alertas automáticos para apoiar decisões diante do risco de alagamento.

**Recorte escolhido:** Clima local — chuva intensa e risco de alagamento em pontos vulneráveis.

## Integrantes

| Nome | RM |
|------|-----|
| Lincoln Simão Pereira | RM567284 |
| Miguel Silva Bezerra | RM566763 |
| Nicolas Sakaue Nishimura | RM567752 |

Professor: Ygor Moraes Martins dos Anjos

## Público beneficiado

Quem sofre o problema são os **moradores de bairros ribeirinhos** (no cenário, o fictício Bairro Ribeira) sujeitos a enchentes quando o rio sobe após chuvas intensas, especialmente com o solo já saturado. O sistema apoia a **decisão da Defesa Civil e da administração local** sobre *quando acionar o plano de contingência*: avisar a população pelos painéis, mobilizar equipes e, em último caso, orientar a retirada preventiva. O sistema não decide pela pessoa — ele antecipa o risco e explica o porquê, para que a autoridade decida com tempo.

## Como executar

Pré-requisito: JDK instalado (`javac` e `java` no PATH).

```bash
javac *.java
java SistemaMonitoramento
```

Também é possível importar os arquivos `.java` em um projeto Java no Eclipse (pacote padrão / default package) e rodar a classe `SistemaMonitoramento`.

O menu permite: verificar sensores, rodar previsões e alertas, gerenciar os dados do território (com código de acesso), simular próximas leituras, exibir o status completo e rodar uma simulação de 24 horas que mostra os alertas escalando ao longo do tempo.

## Estrutura e conceitos de POO

| Arquivo | Papel | Conceito |
|---------|-------|----------|
| `ComponenteTerritorial.java` | classe **abstrata** base dos componentes | Classe abstrata + método abstrato `verificarStatus()` |
| `EstacaoDeColeta.java`, `PainelInformativo.java` | componentes concretos | Herança (`extends`); a estação TEM sensores (associação) |
| `Sensor.java` | **interface** (contrato do sensor) | Interface |
| `SensorChuva.java`, `SensorNivelRio.java`, `SensorUmidade.java` | sensores | `implements Sensor` (3 implementações) |
| `DadosTerritorio.java` | dados do território | Encapsulamento (atributos privados, limiares protegidos por código, validação) |
| `EstrategiaPrevisao.java` | classe **abstrata** de previsão | Herança + `super()` + método abstrato `prever()` |
| `PrevisaoMediaMovel.java`, `PrevisaoComparacao.java` | estratégias | Sobrescrita de `prever()` com atributos próprios |
| `SistemaMonitoramento.java` | menu principal | Loop, entrada do usuário, motor de alertas |

**Sistema de alertas:** a previsão do nível do rio é comparada com os limiares do território (`ATENCAO`, `ALERTA`, `CRITICO`). Cada alerta explica o porquê: a fonte da previsão, o valor previsto e o limite estourado.

## Reflexão crítica

**Quando o sistema deve se calar?** Com poucas leituras, uma previsão vira chute. Por isso cada estratégia declara um mínimo de leituras e, abaixo disso, se recusa a prever — a `PrevisaoComparacao`, que precisa de dois períodos, fica em silêncio enquanto não houver dados suficientes, em vez de emitir um número sem lastro. Prever errado mina a confiança no alerta tanto quanto não prever.

**O que os dados não representam?** Um único sensor de nível por região diz pouco sobre um ponto específico: a média do rio pode estar dentro do limite enquanto uma rua baixa já alaga. As leituras são simuladas e não capturam bueiros entupidos, obras, maré ou a vulnerabilidade social de cada quarteirão. O número é um indício, não a realidade completa do território.

**Por que o alerta apoia a decisão da pessoa, em vez de decidir por ela?** Porque a decisão de evacuar envolve custo, risco e contexto local que o sistema não enxerga. O Sentinela entrega antecedência e justificativa — "nível previsto 5,8 m ultrapassa o limiar de ALERTA" — e deixa a autoridade humana ponderar e agir. Automatizar a leitura é útil; automatizar a decisão de tirar pessoas de casa seria irresponsável.

## Uso de IA

A equipe usou IA como apoio para tirar dúvidas de POO, revisar a modelagem das classes e sugerir a estrutura do menu. Todo o código foi lido, compreendido e testado pelos integrantes; qualquer um do grupo consegue explicar qualquer trecho do próprio código.

## Divisão de tarefas

- **Nicolas Sakaue Nishimura** — Sistema de sensores: interface `Sensor`, os três sensores (`SensorChuva`, `SensorNivelRio`, `SensorUmidade`), a classe abstrata `ComponenteTerritorial` e os componentes `EstacaoDeColeta` e `PainelInformativo`.
- **Miguel Silva Bezerra** — Sistema de previsão: classe abstrata `EstrategiaPrevisao` e as estratégias `PrevisaoMediaMovel` e `PrevisaoComparacao`.
- **Lincoln Simão Pereira** — Dados do território e menu: `DadosTerritorio` (encapsulamento), `SistemaMonitoramento` (menu, alertas e simulação) e a documentação.

## Bônus

A opção "Simular 24 horas" roda uma projeção hora a hora e mostra os alertas escalando de `normal` a `CRITICO` conforme o nível do rio sobe ao longo do dia.
