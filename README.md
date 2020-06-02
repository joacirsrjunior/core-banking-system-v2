# CORE-BANKING-SYSTEM *(v2)*
Projeto consiste em basicamente em simular aspectos de uma conta bancária.<br/>
Dentre os endpoints expostos, destaca-se 3 (três) principais: <br/>
* <b>CRIAR CONTA</b>: Permite que seja criada uma conta a partir do número do documento, caso já não exista.<br/>
* <b>DADOS DA CONTA</b>: Permite que seja verificado os dados, entre eles o saldo bancário de uma determinada conta.<br/>
* <b>TRANSAÇÃO</b>: Possibilita que seja efetuada operações de crédito (depósito de valores) ou de débito (retirada de valores) em uma conta corrente específica.<br/><br/>
Embora não fosse fundamental, alguns outros endpoints também foram desenvolvidos no intuito de auxiliar nos testes e cenários:<br/>
* Listar todas as contas.<br/>

## Stack
* Java *(version 11)*
* Gradle *(version 6.1.1)*
* Spring Boot *(version 2.3.0)*
* Spring Data JPA *(version 2.3.0)*
* Spring Actuator *(version 2.3.0)*
* Jackson Annotations *(version 2.10.4)*
* PostgreSQL *(version 42.2.5)*
* Flyway *(version 5.2.4)*
* JUnit *(version 5)*

## Banco de Dados<br/>
Neste projeto foi utilizado o banco de dados relacional PostgreSQL.<br/>
Porta padrão : 15432.<br/>

## Portas Utilizadas
Esta aplicação utiliza as portas 8084 (core-banking-system) e 15432 (PostgreSQL).<br/>
Caso a aplicação seja rodada localmente, certifique-se que estas portas estejam liberadas ou, caso preferir, altere-as no arquivo <i>application.properties</i> do projeto.

## Dummy Data / Dados para testes
Ao inicializar a aplicação, são inseridas 5 (cinco) contas de usuários para fins de simulações e testes.<br/>
A inserção dos dados é efetuada a partir de uma migration no Flyway (Migration V2).<br/>

## Controle de Sessão / Token Transacional
Tendo em conta a complexidade deste assunto e considerando o foco deste projeto, optei por abstrair questões como token a nível de sessão e a nível de aplicação.

## Docker / Inicialização
A aplicação funciona em qualquer sistema operacional (incluindo Linux e MacOS).<br/>
Foi utilizado Docker, justamente para garantir tal possibilidade, pois desta forma automaticamente a aplicação indica seus requisitos e os obtêm.<br/>
Os arquivos <i>Dockerfile</i> e <i>docker-compose.yml</i>, ambos localizados na raiz do projeto, detalham melhor isto.<br/><br/>
Por fim, abaixo seguem alguns comandos para inicialização da aplicação e montagem de imagens do docker.<br/>

* Etapa 1) Build pelo Gradle:
    * Por linha de comando, na pasta raiz do projeto, digite:<br/>
      ```            ./gradlew build```
* Etapa 2) Iniciando aplicação:
    * Por linha de comando, na pasta raiz do projeto, digite:<br/>
      ```            docker-compose up --build```
* Etapa 3) Parando aplicação:
    * Por linha de comando, na pasta raiz do projeto, digite:<br/>
      ```            docker-compose stop```
* Opcional : Criando uma imagem da aplicação:
    * Por linha de comando, na pasta raiz do projeto, digite:<br/>
      ```            docker build -t core-banking-system:latest .```

## Endpoint para Cadastrar uma Nova Conta
* POST Request:<br/>
```http://localhost:8084/core/accounts```
* Payload Example:<br/>
Neste exemplo, enviaremos um número de documento para criação da conta.<br/>
Presumimos que este número de documento não foi utilizado anteriormente.
```
{
	"document_number": "02156087004"
}
```
<!--
* Success Response Example:<br/>
```
EXEMPLO SUCESSO
```
-->

## Endpoint para Realizar Uma Transação de Crédito ou Débito
* POST Request:<br/>
```http://localhost:8084/core/transactions```
* Payload Example:<br/>
Neste exemplo, enviaremos um débito (DEBIT) de 3 reais para a conta de ID 4.<br/>
Os créditos e débitos são definidos pelo tipo de operação *(Verificar classe EOperationType)*.
```
{
	"account_id": 4,
	"operation_type_id": 4,
	"amount": 100
}
```
* Success Response Example:<br/>
```
{
    "meta": {
        "currentDate": "2020-06-02T07:16:26.763+00:00"
    },
    "data": {
        "id": 2,
        "createdAt": "2020-06-02T07:16:26.739+00:00",
        "account": {
            "id": 4,
            "createdAt": "2020-06-02T07:09:03.894+00:00",
            "documentNumber": "97475237010",
            "balance": 210.00
        },
        "operationType": "PAYMENT",
        "amount": 100
    }
}
```
<!--
* Error Response Example - Insufficient Funds (Only possible for debit transactions):
```
EXEMPLO SALDO
```
* Error Response Example - Account Not Found:
```
EXEMPLO CONTA NAO LOCALIZADA
```
-->
## Pontos de Melhoria : Arquitetura Futura - Kafka e Circuit Breaker<br/>
Considerando o projeto apresentado, na minha concepção o ideal seria aliá-lo a utilização do Kafka, pois desta forma, teríamos uma independência entre o transacional e o extrato, no qual poderia ser efetuado a partir dos eventos disponibilizados no Kafka.<br/>

O retorno de sucesso e/ou falha do processamento da requisição também poderia ser disponibilizado em outra fila do Kafka, para posterior consumo por parte do seu requisitante inicial ou demais aplicações e fluxos necessários, como por exemplo rotinas contábeis, rotinas de documentações legais, entre outros.<br/>

Outro ponto que poderia ser implementado seria o de Circuit Breaker a partir da utilização do Resilience4J. Com isto teríamos um maior controle sob fluxos alternativos e de tratamentos em caso de falhas, além de possíveis *retrys* das operações.
