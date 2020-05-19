# DESAFIO B2W | API STAR WARS 
## API de gerenciamento de planetas do Star Wars!
Update date: 2020-05-17.

![Star Wars](https://cdn.pocket-lint.com/r/s/1200x/assets/images/147767-tv-feature-what-order-should-you-watch-all-the-star-wars-films-image1-1wdfjceytb.jpg)

<h3>Bem vindo ao ReadMe da API StarB2W.<h3>

Esta aplicação foi desenvolvida com arquitetura API RESTful e tem por objeto gerenciar dados de planetas do filme Star Wars. Considera-se por gerenciamento ações de cadastrar, listar e deletar dados de planetas.

**URL Base:** [http://localhost:8080/starB2W](http://localhost:8080/starB2W)
Observações:

 - O path raiz pode ser alterado no arquivo application.yml, em <b>server.servlet.context-path<b>.
 - A porta pode ser alterada no arquivo application.yml, em <b>server.port<b>.
 - Pode ser encontrado no path [/swagger-ui.html#/](http://localhost:8080/starB2W/swagger-ui.html#/) informações sobre os recursos da aplicação.
 - Pode ser encontrado a collection no PostMan, através do link: [Collection - PostMan](https://www.getpostman.com/collections/8a3d5017a28d81807c09) 

**A API StarB2W está em constante evolução e até o momento foram utilizados no desenvolvimento:**

 - Linguagem de programação Java (JDK 11);
 - Apache Maven;
 - Spring Boot (2.2.7 Release); 
 - Swagger (2.9.2);
 - Hibernate Validator (5.4.2 Final);
 - Spring Boot Starter Data JPA (2.2.7 Release);
 - Spring Boot Starter Data Mongodb (2.2.7 Release);
 - Spring Boot DevTools (2.2.7 Release);
 - Junit;
 - Mock/Mockito
 - Open Feign (2.2.2 Release);
 - LomBok (1.18.12).
 - Docker

## **Tests:**

Para rodar os testes da aplicação é necessário ter o Maven instalado e digitar o comando abaixo no shell:

> mvn test

É necessário estar dentro da pasta da aplicação.

## **Docker:**
Para rodar a aplicação com o Docker é necessário como pré requisito ter instalados Docker & Docker Compose.

No shell, dentro da pasta da aplicação,  basta digitar:

> docker-compose down && 
> mvn clean install && 
> mvn package && 
> docker-compose up --build

_______

## **Recursos:**
**[GET] /v1/planets**
Obter lista de todos os planetas.
Pode ser passado  os parâmetros :page e size. (não obrigatório).
Por default: page = 1 e Size = 10.

**Exemplo curl:**

    curl -X GET "http://localhost:8080/starB2W/v1/planets?page=1&size=10" -H "accept: application/json" 

**Modelo de objeto de retorno em caso de sucesso (Status Code 200):**

>[  
>{  "id":  "5ec03f2e6df50106137331d8",  
"name":  "Tatooine",  
"climate":  "arid",  
"terrain":  "desert",  
"movieAppearances":  "10"  }
]

**Modelo de objeto de retorno em caso de não existir planeta cadastrado (Status Code 404):**

> {  "MESSAGE: ":  "Planet not found."  }

_______________________

**[GET] /v1/planet/{id}**
Obter planeta pesquisando pelo ID. Deve ser passado a variável "id" na URL.

**Exemplo curl:**

    curl -X GET "http://localhost:8080/starB2W/v1/planet/5ec0531d39df1e529d451136" -H "accept: application/json"

**Modelo de objeto de retorno em caso de sucesso (Status Code 200):**

>{ 
> "id":  "5ec03f2e6df50106137331d8",  
"name":  "Tatooine",  
"climate":  "arid",  
"terrain":  "desert",  
"movieAppearances":  "5" 
 }

**Modelo de objeto de retorno em caso de planeta não encontrado (Status Code 404):**

>  {  "MESSAGE: ":  "Planet not found."  }

____________
   
**[GET] /v1/planet?name=**
Obter planeta pesquisando pelo parâmetro nome. Deve ser passado a variável "name" na URL.

**Exemplo curl:**

    curl -X GET "http://localhost:8080/starB2W/v1/planet?name=Alderaan" -H "accept: application/json"

**Modelo de objeto de retorno em caso de sucesso (Status Code 200):**

> {  "id":  "5ec0531d39df1e529d451136",  
> "name":  "Alderaan",  
> "climate":  "tropical",  
> "terrain":  "grasslands, mountains",  
> "movieAppearances":  "2"  }

**Modelo de objeto de retorno em caso de planeta não encontrado (Status Code 404):**

> {  "MESSAGE: ":  "Planet not found."  }

________

**[POST] /v1/planet**
Cadastrar planeta. Deve ser passado um body conforme modelo:

>  {   "name": "Tatooine",   
>  "climate": "arid",   
>  "terrain": "desert" }

**Exemplo curl:**

    curl -X POST "http://localhost:8080/starB2W/v1/planet" -H "accept: application/json" -H "Content-Type: application/json" -d "{ \"name\": \"Tatooine\", \"climate\": \"arid\", \"terrain\": \"desert\"}"

**Modelo de objeto de retorno em caso de sucesso (Status Code 201):**

> {  "id":  "5ec158343565383866d58ed5",  
> "name":  "Tatooine", 
> "climate":  "arid",  
> "terrain":  "desert",  
> "movieAppearances":  "5" 
> }

Durante a requisição POST, é consumida a API Swapi para verificar o número de aparições em filmes, caso o nome não seja encontrado na API Swapi é retornado "0" no atributo movie Appearances.

Se houver erro no consumo da API Swapi durante a requisição POST, é retornando no atributo movieAppearances a mensagem, ""It was not possible to identify the number of appearances."

**Modelo de objeto de retorno em caso o planeta já exista no banco de dados da aplicação starB2W (Status Code 400):**

> {  "MESSAGE: ":  "This planet has already been created."  }

______

**[POST] /v1/planet/{name}**
Cadastrar planeta. Deve ser passado na url da requisição a variável "name". Nesta requisição a API starB2W consome a API Swapi buscando o planeta pelo nome, se o planeta for encontrado, os dados são persistidos no banco e o retorno tem status 201 Created , em caso negativo o retorno é 404 Not Found. No caso do planeta já se encontrar cadastrado no banco de dados da API starB2W, o retorno é 400 Bad Request.

**Exemplo curl:**

    curl -X POST "http://localhost:8080/starB2W/v1/planet/Tatooine" -H "accept: application/json"

**Modelo de objeto de retorno em caso de sucesso (Status Code 201):**

> {  "id":  "5ec2fdb7c599de3b7c7572dc", 
>  "name":  "Alderaan",  
>  "climate":  "temperate",  
>  "terrain":  "grasslands, mountains",  
>  "movieAppearances":  null 
>   }

**Modelo de objeto de retorno em caso de não encontrado (Status Code 404):**

> {  "MESSAGE:":  "Planet not found."  }

**Modelo de objeto de retorno em caso de planeta já cadastrado (Status Code 400):**

> {  "MESSAGE:":  "This planet has already been created."  }

_____
**[DELETE] /v1/planet/{id}**
Deletar planeta. Deve ser passado o id do planeta como variável na url.

**Exemplo curl:**

    curl -X DELETE "http://localhost:8080/starB2W/v1/{id}?id=5ec03f2e6df50106137331d8" -H "accept: application/json"

**Modelo de objeto de retorno em caso de sucesso (Status Code 200):**

> {  "MESSAGE: ":  "Planet Deleted."  }

**Modelo de objeto de retorno em caso de planeta não encontrado. (Status Code 404):**

> {  "MESSAGE: ":  "Planet not found."  }
_________

Caso haja erro no sistema, o log de erro será informado no console e será retornado uma mensagem ao usuário com Http Status 500, no padrão abaixo:

> {  "messages":  [  {  
> "message":  
> "We were unable to process your request. Try again later."  }  ]  }


## **Licença:**
Este projeto esta sob licença [MIT](https://github.com/itsadeadh2/starwars-b2w/blob/master/LICENSE).
