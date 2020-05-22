﻿

# DESAFIO B2W | API STAR WARS

## API de gerenciamento de planetas do Star Wars! 

Update date: 2020-05-21 

![https://cdn.pocket-lint.com/r/s/1200x/assets/images/147767-tv-feature-what-order-should-you-watch-all-the-star-wars-films-image1-1wdfjceytb.jpg](https://cdn.pocket-lint.com/r/s/1200x/assets/images/147767-tv-feature-what-order-should-you-watch-all-the-star-wars-films-image1-1wdfjceytb.jpg)

### Bem vindo ao ReadMe da API StarB2W.

Esta aplicação foi desenvolvida com arquitetura API REST e tem por objeto gerenciar dados de planetas do filme Star Wars. Considera-se por gerenciamento ações de cadastrar, listar e deletar dados de planetas. 

**URL Base:** [http://localhost:8080/starB2W/](http://localhost:8080/starB2W/) 

Observações: 
- O path raiz pode ser alterado no arquivo application.yml, em **server.servlet.context-path**. 
-  A porta pode ser alterada no arquivo application.yml, em **server.port**. 
-  Pode ser encontrado no path [http://localhost:8080/starB2W/swagger-ui.html](http://localhost:8080/starB2W/swagger-ui.html) informações sobre os recursos da aplicação. 
-  Pode ser encontrado a collection no PostMan, através do link: 
[Collection - PostMan](https://www.getpostman.com/collections/8a3d5017a28d81807c09)

**A API StarB2W está em constante evolução e até o momento foram utilizados no desenvolvimento:** 
- Linguagem de programação Java (JDK 11); 
-  Apache Maven; 
-  Spring Boot (2.2.7 Release); 
-  Swagger (2.9.2); 
-  Hibernate Validator (5.4.2 Final); 
-  Spring Boot Starter Data JPA (2.2.7 Release); 
-  Spring Boot Starter Data Mongodb (2.2.7 Release); 
- Spring Boot Starter Security (2.2.7 Release)
- JWT (io.jsonwebtoken 0.9.1)
-  Mongock Spring (2.0.0) 
-  Spring Boot DevTools (2.2.7 Release); 
- Junit; 
-  Mock/Mockito 
-  Open Feign (2.2.2 Release); 
-  LomBok (1.18.12). 
- Docker 
-  Docker Compose 
 _______ 
 ## Tests:
  Para rodar os testes da aplicação é necessário ter o Maven instalado e digitar o comando abaixo na cli de preferência: 
  > mvn test 

É necessário estar dentro da pasta da aplicação. 

## Docker: 
Para rodar a aplicação com o Docker é necessário como pré requisito ter instalados Docker & Docker Compose. 
Na cli, dentro da pasta da aplicação, basta digitar: 

> docker-compose down && > mvn clean install && > mvn package && > docker-compose up --build 
 _______ 
## Autenticação:

Foi utilizado o Spring Security com padrão JWT para a segurança da aplicação. A autenticação é realizada via token, que deve ser recuperado do endpoint **/auth** passando no corpo da requisição email e senha. Para fins de demonstração, segue abaixo corpo da requisição funcional :

> {   "email": "eng.paulomoreira@gmail.com",   
> "pass": "Paulo1234" }

Temos no corpo de resposta o token e o tipo de autenticação:

> {  "token": 
> "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJBUEkgU1RBUiBCMlciLCJzdWIiOiI1ZWM2MDdjNTQwNDQxMjVlZmMxODQ0Y2YiLCJpYXQiOjE1OTAwNjY1MzQsImV4cCI6MTU5MDE1MjkzNH0.hiQ6PL8r7AgryfKjnTzde4l_NYPF9REsQNcYB-2wJk",
> "authType":  "Bearer"  }

No caso de informações inválidas, é retornado status 400 e no corpo:

> {  "MESSAGE:":  "User or Password invalid."}

Os endpoints abaixo estão protegidos,  o restante não necessita de autenticação:

 - [DELETE] [/v1/planet/{id}](http://localhost:8080/starB2W/swagger-ui.html#/operations/Operations%20about%20planet%20of%20Star%20Wars./deletePlanetByIdUsingDELETE)
 - [POST] [/v1/planet](http://localhost:8080/starB2W/swagger-ui.html#/operations/Operations%20about%20planet%20of%20Star%20Wars./createPlanetUsingPOST)

O token tem expiração de 1 dia, esse tempo pode ser ajustado no arquivo application.yml, parametro **starB2W.jwt.expiration** o valor deve ser passado em milissegundo.
 _______ 

## Climas e Terrenos:
 De forma a padronizar os climas e terrenos aceitos existem validações na aplicação para que sejam apenas permitidos os seguintes climas e terrenos durante a requisição de criação pelo usuário:

 **CLIMAS**
 
arid | artic | artificial | temperate | frigid | frozen | hot | humid | moist | murky | polluted | rocky | subartic | superheated | temperate | tropical | unknown | windy

 **TERRENOS**
 
 acid pools | airless asteroid | ash | barren | bogs | canyons | caves | cities | cityscape | cliffs | desert | fields | forests | fungus forests | gas giant | glaciers | grass | grasslands | grassy | hills | ice canyons | ice caves | islands | jungle | jungles | lakes | mesas | mountain ranges | mountains | ocean | oceans | plains | plateaus | rainforests | reefs | rivers | rock | rock arches | rocky canyons | rocky deserts | rocky islands | savannahs | savannas | scrublands | seas | swamp | swamps | toxic cloudsea | tundra | unknown | urban | valleys | verdant | vines | volcanoes 
 _______ 

## Recursos:

**[GET] /health**

 Para verificar o status da aplicação pode-se executar uma requisição get para o path /health. No caso de sucesso temos como resposta:

> {  "Status":  "UP"  }
______
**[GET] /auth**

Para gerar o token de autorização da aplicação deve ser passado em uma requisição get com path **/auth** o corpo de requisição abaixo:

> {   "email": "eng.paulomoreira@gmail.com",   
> "pass": "Paulo1234" }

No caso de sucesso, como resposta status 200 e corpo:

> {  "token": 
> "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJBUEkgU1RBUiBCMlciLCJzdWIiOiI1ZWM2MDdjNTQwNDQxMjVlZmMxODQ0Y2YiLCJpYXQiOjE1OTAwNjY1MzQsImV4cCI6MTU5MDE1MjkzNH0.hiQ6PL8r7AgryfKjnTzde4l_NYPF9REsQNcYB-2wJk",
> "authType":  "Bearer"  }

______

**[GET] /v1/planets** 

Obter lista de todos os planetas. Pode ser passado o parâmetro page (não obrigatório). Se o parâmetro não for informado será retornado todos os planetas cadastrados. A paginação começa do número 1. Exemplo: Primeira página parâmetro (?page=1). 

**Exemplo curl:** 

     curl -X GET "http://localhost:8080/starB2W/v1/planets?page=2" -H "accept: application/json"

**Modelo de objeto de retorno em caso de sucesso (Status Code 200):** 

> { "result": [ 
>  { "id": "5ec5a7e1c82e3326d0b7f22e", 
>  "name": "Coruscant", 
>   "climates": [ "temperate" ], 
>    "terrains": [ "cityscape", "mountains" ], 
>    "movieAppearances": "4" }, 
>     { "id": "5ec5a7eac82e3326d0b7f22f", 
>     "name": "Kamino",  
>     "climates":  [ "temperate" ],  
>     "terrains": [ "ocean" ],
>      "movieAppearances": "1"  } ],
>        "totalPages": 2 } 

**Modelo de objeto de retorno em caso de não existir planeta cadastrado (Status Code 404):** 

>  { "MESSAGE: ": "Planet not found." }<

  _______________________

**[GET\] /v1/planet/{id}**

Obter planeta pesquisando pelo ID. Deve ser passado a variável "id" na URL. 
**Exemplo curl:** 

     curl -X GET
    "http://localhost:8080/starB2W/v1/planet/5ec0531d39df1e529d451136" -H "accept: application/json"

**Modelo de objeto de retorno em caso de sucesso (Status Code 200):** 

> { "id": "5ec579f97386f526970d3b20", 
>  "name": "Tatooine", 
> "climates": [ "arid" ], 
> "terrains": [ "desert" ], 
> "movieAppearances": "5" } 

**Modelo de objeto de retorno em caso de planeta não encontrado (Status Code 404):**

>   { "MESSAGE: ": "Planet not found." } 
>   ____________ 

**[GET\] /v1/planet?name=** 

Obter planeta pesquisando pelo parâmetro nome. Deve ser passado a variável "name" na URL. 
**Exemplo curl:** 


    curl -X GET "http://localhost:8080/starB2W/v1/planet?name=Alderaan" -H "accept: application/json"

**Modelo de objeto de retorno em caso de sucesso (Status Code 200):** 

>  { "id": "5ec579f97386f526970d3b20", 
>   "name": "Tatooine", 
> "climates": [ "arid" ], 
>  "terrains": [ "desert" ], 
> "movieAppearances": "5" } 

**Modelo de objeto de retorno em caso de planeta não encontrado (Status Code 404):**

 > { "MESSAGE: ": "Planet not found." }

 ________ 
 **\[POST\] /v1/planet**

 Cadastrar planeta. Deve ser passado um body conforme modelo e no header o parametro Authorization, passando a palavra Bearer e o token: 

> { "name": "Dagobah", 
>  "climates": \["frozen" \], 
>   "terrains": \[ "tundra","ice caves","mountain ranges" \] } 

**Exemplo curl:** 

    curl -X POST "http://localhost:8080/starB2W/v1/planet" -H "accept: application/json" -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJBUEkgU1RBUiBCMlciLCJzdWIiOiI1ZWM2MDdjNTQwNDQxMjVlZmMxODQ0Y2YiLCJpYXQiOjE1OTAxMDc1OTQsImV4cCI6MTU5MDE5Mzk5NH0.3BpSOd5J1WyBUpfeHzem3BggCi0OQm1Pl21_Aev8Lk" -H "Content-Type: application/json" -d "{ \"name\": \"string\", \"climates\": [ \"arid\" ], \"terrains\": [ \"swamp\" ]}"

 **Modelo de objeto de retorno em caso de sucesso (Status Code 201):** 
> { "id": "5ec57d237386f526970d3b29", 
> "name": "dagobah", 
>  "climates": [ "frozen" ], 
>   "terrains": [ "tundra", "ice caves", "mountain ranges" ], "movieAppearances": "3" } 

Durante a requisição POST, é consumida a API Swapi para verificar o número de aparições em filmes, caso o nome não seja encontrado na API Swapi é retornado "0" no atributo movie Appearances. 

Se houver erro no consumo da API Swapi durante a requisição POST, é retornando no atributo movieAppearances a mensagem, "It was not possible to identify the number of appearances."

**Modelo de objeto de retorno em caso o planeta já exista no banco de dados da aplicação starB2W (Status Code 400):** 

> { "MESSAGE: ": "This planet has already been created." } 

**Modelos de objeto de retorno em caso de o clima ou terreno serem inválidos (Status Code 400):** 

> { "MESSAGE:": " Please enter a valid climate." }  
> { "MESSAGE:": " Please enter a valid terrain." } 
 ______ 
 **\[POST\] /v1/planet/{name}**

 Cadastrar planeta. Deve ser passado na url da requisição a variável "name". 

Nesta requisição a API starB2W consome a API Swapi buscando o planeta pelo nome, se o planeta for encontrado, os dados são persistidos no banco e o retorno tem status 201 Created , em caso negativo o retorno é 404 Not Found. 

No caso do planeta já se encontrar cadastrado no banco de dados da API starB2W, o retorno é 400 Bad Request. 

**Exemplo curl:** 

    curl -X POST 
     "http://localhost:8080/starB2W/v1/planet/Yavin%20IV" -H "accept:
     application/json"

**Modelo de objeto de retorno em caso de sucesso (Status Code 201):** 

> { "id": "5ec57d7b7386f526970d3b2a", 
>  "name": "Yavin IV", 
>   "climates": [ "temperate", " tropical" ], 
>    "terrains": [ "jungle", " rainforests" ], 
>     "movieAppearances": "1" }

  **Modelo de objeto de retorno em caso de não encontrado (Status Code 404):** 

> { "MESSAGE:": "Planet not found." } 

**Modelo de objeto de retorno em caso de planeta já cadastrado (Status Code 400):** 

> { "MESSAGE:": "This planet has already been created." }
  _____ 
 **\[DELETE\] /v1/planet/{id}** 

Deletar planeta. Deve ser passado o id do planeta como variável na url e o authorization no header da requisição, no formato: Bearer token.
**Exemplo curl:** 

     curl -X DELETE "http://localhost:8080/starB2W/v1/planet/123" -H
     "accept: application/json" -H "Authorization: Bearer 111"

**Modelo de objeto de retorno em caso de sucesso (Status Code 200):** 

> { "MESSAGE: ": "Planet Deleted." } 

**Modelo de objeto de retorno em caso de planeta não encontrado. (Status Code 404):** 
> { "MESSAGE: ": "Planet not found." } 
________ 

Caso haja erro no sistema, o log de erro será informado no console e será retornado uma mensagem ao usuário com Http Status 500, no padrão abaixo: 

>  { "messages": [  {  "message":  "We were unable to process your request. Try again later." }   ] }
 _______ 
## **Licença:** 
Este projeto esta sob licença [MIT](https://github.com/itsadeadh2/starwars-b2w/blob/master/LICENSE)

_________
Para quaisquer dúvida enviar email para:
eng.paulomoreira@gmail.com
