# pessoa-api-attournatus

# Sobre o projeto
Projeto de avaliação para processo Desenvolvedor Back Attournatus. Com utilização de ferramentas Springframework.
Aplicação projetada para simular um ambiente de gerenciamento de pessoas. Na aplicação foram usadas as ferramentas disponíveis pelo Spring Framework, entre outras bibliotecas. Assim como, teve cobertura de 100% das linhas das Services em testes unitarios, utilizando-se do Mockito. 

A aplicação segue configurada e documentada pela ferramenta Swagger, pelo qual podem ser acessadas suas funcionalidades, bastando clonar este repositorio para sua máquina, rodar a aplicação através de uma IDEA como o Intelijj, e acessar os endpoints de suas funcionalidades através do Swagger, apenas conectando no seu navegador pelo link: 

http://localhost:8080/

Após acessar o link, percebera os endpoints separados pelas Controllers: Pessoa e Endereço.

# COMO UTILIZAR API:

#Ds endpoints disponiveis em PessoaController:

Busca PAGINADA por registros de Pessoas:
GET
http://localhost:8080/pessoa/listar-paginado

O endpoint GET./pessoa/listar-paginado, lhe oferece as opções de busca pelo id da pessoa que deseja pesquisar, assim como pelo nome da mesma. O filtro por nome pode ser realizado a partir das letras presentes no nome da pessoa, ignorando o uso de letras maiusculas ou minusculas. Para usar paginação, basta colocar o numero da pagina desejada, e a quantidade de itens que poderão ser dispostos nessa página.

Buscar por endereços através do filtro de tipos de endereço:
GET
http://localhost:8080/pessoa/buscar-endereco

O endpoint GET./pessoa/buscar-endereco, lhe oferece as opções de busca pelo nome da pessoa que deseja pesquisar, listando os endereços cadastrados em seu nome, de acordo com o tipo de endereço que deseja filtrar. O filtro por nome pode ser realizado a partir das letras presentes no nome da pessoa, ignorando o uso de letras maiusculas ou minusculas. Caso não use o filtro nome, serão listados todos os endereços do tipo pesquisado, de diversos titulares registrados. 

Cadastro de Pessoa
POST
http://localhost:8080/pessoa

Para cadastrar uma pessoa, acesse o endpoint POST. /pessoa, através desse você poderá registrar os dados da pessoa(Nome e Data de Nascimento), assim como, já realizar o cadastro de seu endereço. Podendo ser endereço Principal, Secundario ou Comercial. Posteriormente, ele podera registrar novos endereços, e adicionar a lista de endereços dessa pessoa:

Editar dados da Pessoa:
PUT
http://localhost:8080/pessoa/{id}

Utilizando o endpoint PUT./pessoa/{id}, o usuario deve buscar o cadastro que ele deseja editar, através do ID da pessoa registrada. Podendo então editar nome e data de nascimento do cadastro desejado.

Deletar pessoa:
DELETE
http://localhost:8080/pessoa/{id}

Utilizando o endpoint DELETE./pessoa/{id}, o usuario deve buscar o cadastro que ele deseja deletar, através do ID da pessoa registrada. Podendo então deletar os dados da pessoa. Considerando que esta é uma aplicação teórica, os deletes são físicos e não lógicos. Portanto os dados serão perdidos por completo.

#Ds endpoints disponiveis em EnderecoController:

Busca PAGINADA por endereços através do  ID de endereço:
GET
http://localhost:8080/endereco/listar-paginado

O endpoint GET./endereco/listar-paginado, lhe oferece as opções de busca pelo ID de endereco que deseja pesquisar. O uso do ID é opcional, não sendo colado a busca ira devolver lista com todos os endereços registrados de acordo com a paginação. Para usar paginação, basta colocar o numero da pagina desejada, e a quantidade de itens que poderão ser dispostos nessa página.

Cadastro de Endereco
POST
http://localhost:8080/endereco/{ID}

Este endpoint POST./endereco/{ID}, lhe permite registrar novos endereços a uma pessoa cadastrada. Basta colocar o id da pessoa para qual você deseja adicionar este novo endereços.

Editar dados da Endereço:
PUT
http://localhost:8080/endereco/{id}

Utilizando o endpoint PUT./endereco/{id}, o usuario deve buscar o endereço que ele deseja editar, através do ID do endereço registrada. Podendo então editar todos os dados deste endereço.

Deletar pessoa:
DELETE
http://localhost:8080/endereco/{id}

Utilizando o endpoint DELETE./endereco/{id}, o usuario deve buscar o enderço que ele deseja deletar, através do ID do endereço registrado. Podendo então deletar os endereços desejados. Considerando que esta é uma aplicação teórica, os deletes são físicos e não lógicos. Portanto os dados serão perdidos por completo.

## ✔️ Tecnologias utilizadas

* Spring Web MVC;
* Spring Boot;
* Spring Data JPA;
* Openfeign api;
* Swagger;
* Banco de dados H2;
* JUnit & Mockito;
- ``Java 17``
- ``H2 DB``

