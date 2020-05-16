# DESAFIO B2W | API STAR WARS 
## API de gerenciamento de planetas do Star Wars!
Update date: 2020-05-16.

![Star Wars](https://drive.google.com/open?id=12t2iAgMne8nV5QF5c5HSVugDbRWJosTL)

<h3>Bem vindo ao ReadMe da API StarB2W.<h3>

Esta aplicação foi desenvolvida com arquitetura API RESTful e tem por objeto gerenciar dados de planetas do filme Star Wars. Considera-se por gerenciamento ações de cadastrar, listar e deletar dados de planetas.

**URL Base:** [http://localhost:8080/starB2W](http://localhost:8080/starB2W)
Observações:

 - O path raiz pode ser alterado no arquivo application.yml, em <b>server.servlet.context-path<b>.
 - A porta pode ser alterada no arquivo application.yml, em <b>server.port<b>.
 - Pode ser encontrado no path [/swagger-ui.html#/](http://localhost:8080/starB2W/swagger-ui.html#/) informações sobre os recursos da aplicação.

**A API StarB2W esta em constante evolução e até o momento foram utilizados no desenvolvimento:**

 - Linguagem de programação Java (JDK 11);
 - Apache Maven;
 - Spring Boot (2.2.7 Release);
 - Swagger (2.9.2);
 - Hibernate Validator (5.4.2 Final);
 - Spring Boot Starter Data JPA (2.2.7 Release);
 - Spring Boot Starter Data Mongodb (2.2.7 Release);
 - Spring Boot DevTools (2.2.7 Release);
 - Junit;
 - Open Feign (2.2.2 Release);
 - LomBok (1.18.12).

## **Recursos:**

**[GET] /v1/planet**
Obter lista de todos os planetas.
Pode ser passado  os parâmetros :page e size. (não obrigatório).
Por default: page = 1 e Size = 10.

**Exemplo curl:**

    curl -X GET "http://localhost:8080/starB2W/v1/planet?page=1&size=10" -H "accept: application/json" 

**Modelo de objeto de retorno em caso de sucesso (Status Code 201):**

> {  "statusCode":  200, 
>  "success":  true, 
>   "result":  {  "content":  [ 
>    {  "id":  "5ec039e9a07e180a31dfd9f7",  
>    "name":  "Tatooine",  
>    "climate":  "arid",  
>    "terrain":  "desert",  
>    "movieAppearances":  "2" }  ],  
>    "pageable":  "INSTANCE", 
>     "totalPages":  1,  
>     "totalElements":  1, 
>      "last":  true,  
>      "numberOfElements":  1,  
>      "first":  true,  
>      "number":  0,  
>      "size":  1, 
>       "sort":  {  "sorted":  false,  
>       "unsorted":  true,  
>       "empty":  true  },  
>       "empty":  false  }  }

**[GET] /v1/planet/{id}**
Obter planeta pesquisando pelo ID. Deve ser passado a variável "id" na URL.

**Exemplo curl:**

    curl -X GET "http://localhost:8080/starB2W/v1/planet/{id}?id=5ec03cdf7d99b934ad47f5e" -H "accept: application/json"

**Modelo de objeto de retorno em caso de sucesso (Status Code 200):**

> { 
>  "statusCode":  200,  
> "success":  true,  
> "result": 
>  {  "id":  "5ec039e9a07e180a31dfd9f7", 
>   "name":  "Tatooine",  
>   "climate":  "arid", 
>    "terrain":  "desert",  
>    "movieAppearances":  "5"  } 
>     }
   
**[GET] /v1/planet/{name}**
Obter planeta pesquisando pelo nome. Deve ser passado a variável "name" na URL.

**Exemplo curl:**

    curl -X GET "http://localhost:8080/starB2W/v1/planet/{name}?id=5ec03cdf7d99b934ad47f5e" -H "accept: application/json"

**Modelo de objeto de retorno em caso de sucesso (Status Code 200):**

> { 
>  "statusCode":  200,  
> "success":  true,  
> "result": 
>  {  "id":  "5ec039e9a07e180a31dfd9f7", 
>   "name":  "Tatooine",  
>   "climate":  "arid", 
>    "terrain":  "desert",  
>    "movieAppearances":  "5"  } 
>     }



**[POST] /v1/planet**
Cadastrar planeta. Deve ser passado um body conforme modelo:

       {
      "name": "Alderaan",
      "climate": "tropical",
      "terrain": "grasslands, mountains"
    }

**Exemplo curl:**

    curl -X POST "http://localhost:8080/starB2W/v1/planet" -H "accept: application/json" -H "Content-Type: application/json" -d "{ \"name\": \"Alderaan\", \"climate\": \"tropical\", \"terrain\": \"grasslands, mountains\"}"

**Modelo de objeto de retorno em caso de sucesso (Status Code 201):**

> {  "statusCode":  201,  
> "success":  true,  
> "result":  {  "id":  "5ec0531d39df1e529d451136",  
> "name":  "Alderaan",  
> "climate":  "tropical",  
> "terrain":  "grasslands, mountains",  
> "movieAppearances":  "2"  }  }


**[DELETE] /v1/planet/{id}**
Deletar planeta. Deve ser passado o id do planeta como variável na url.

**Exemplo curl:**

    curl -X DELETE "http://localhost:8080/starB2W/v1/planet/{id}?id=5ec03cdf7d99b934ad47f5e" -H "accept: application/json"

**Modelo de objeto de retorno em caso de sucesso (Status Code 200):**

> {  "statusCode":  200, 
>  "success":  true, 
>   "result":  "Planet Deleted."  }

## **Licença:**
Este projeto esta sob licença [MIT](https://github.com/itsadeadh2/starwars-b2w/blob/master/LICENSE).