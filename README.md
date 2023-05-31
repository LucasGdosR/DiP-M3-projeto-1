# **DEVin[House] · Módulo FullStack · Projeto 1**
## **Escopo**
O projeto consiste em uma API Rest para controle de check-in em um voo. Existem passageiros pré-cadastrados com um script de SQL. É possível listar os passageiros com suas respectivas reservas, listar um passageiro específico, fazer o check-in, e listar todos os assentos da aeronave. Algumas regras de negócio são aplicadas no check-in:
- Menores de idade não podem sentar nas fileiras 5 e 6;
- Quem sentar nas fileiras 5 e 6 obrigatoriamente tem que despachar as bagagens;
- Um assento não pode ser reservado por mais de uma pessoa;
- O passageiro ganha milhas conforme sua categoria do programa de fidelidade;
- **Extra**: inseri a regra de que um passageiro não pode reservar mais de um assento.

Diferentemente dos projetos anteriores, este requer testes unitários das camadas de Service e Controller. Fui além, e fiz um teste de integração das duas camadas para os Assentos.

## **Tecnologias**
O projeto foi feito em Java utilizando Spring Boot, com um banco de dados em memória H2. Os testes utilizaram o JUnit 5. Foi utilizado o conceito de DTOs de requisição e de resposta.

### Extra
O código ficou bastante limpo e enxuto. Destaco:
1. Construtores que recebem a entidade nos DTOs;
2. Aritmética de caracteres para popular o repositório de assentos;
3. Uso de derived query do JPA;
4. Concentração de todas as validações de regra de negócio em um método isolado;
5. Enum no lugar de if/switch;
6. Uso de ResponseStatusException, dispensando a criação de um Handler.

Além disso, conforme mencionado anteriormente, ao invés de fazer apenas testes unitários, fiz um teste de integração. Também incluí uma regra de negócio para consertar um bug em potencial não pretendido, que acontecia caso um passageiro fizesse mais de um check-in, ocupando dois assentos, mas ficando com apenas um em sua reserva.
