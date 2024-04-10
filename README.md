# itau-seguros-backend-challenge-solution
Repository created with the aim of solving [Itau's public challenge](https://github.com/itausegdev/backend-challenge) for the insurance area

# Instruções de execução do projeto

## Pré-requisitos
- Maven Instalado, [link para documentação](https://maven.apache.org/install.html)
- Java Instalado, [link para documentação](https://www.oracle.com/br/java/technologies/downloads/)
- Docker Instalado, [link para documentação](https://docs.docker.com/get-docker/)
- Docker-compose Instalado, [link para documentação](https://docs.docker.com/compose/install/)
- Git Instalado, [link para documentação](https://git-scm.com/book/pt-br/v2/Começando-Instalando-o-Git)
- No diretório Docker, dentro do diretório raiz do repositório, acessar o subdiretório prometheus e alterar o arquivo prometheus.yml para receber o ip de sua máquina no lugar demarcado.

## Execução
Após realizar a instalação dos pré-requisitos, no diretório de sua preferência, realize o clone do projeto seguindo o comando abaixo:
```
git clone https://github.com/nascitonicholas/itau-seguros-backend-challenge-solution.git
```
No terminal de sua preferência, acesse a pasta **Docker**, localizada na raiz do projeto clonado. Exemplo:
```
cd ./itau-seguros-backend-challenge-solution/Docker
```
Execute o comando docker-compose para subir os containers com a infraestrutura necessária para utilizar a aplicação:
```
docker-compose up
```
Caso prefira manter o Docker rodando em segundo plano, adicionar o parâmetro -d no final do comando anterior. 

Com todos os containers rodando corretamente (pode-se executar o comando ``docker ps`` para visualizar os containers ativos),
acesse o diretório insurance-product-challenge localizado na raiz do projeto clonado e execute a limpeza e instalação do maven.
```
mvn clean install
```
Após o término da execução do comando, acesse a pasta target criada e executar
o jar gerado para executar a aplicação:
```
java -jar insurance-product-challenge-0.0.1-SNAPSHOT.jar
```
Garanta que as portas 8080, 9090, 3000, 6379, 3306, 9200 e 5601 não estejam sendo utilizadas por nenhum processo em seu computador.

Com o aplicativo rodando com sucesso, já é possível realizar requisições em dois endpoints expostos:
````shell
curl --location --request POST 'http://localhost:8080/insurance-products' \
--header 'Content-Type: application/json' \
--data-raw '{
    "nome": "<Product-Name>",
    "categoria":"<Category: VIDA, AUTO, VIAGEM, RESIDENCIAL, PATRIMONIO>",
    "preco_base": <BasePrice: 100.55>
}'
````
````shell
curl --location --request PUT 'http://localhost:8080/insurance-products/{id}' \
--header 'Content-Type: application/json' \
--data-raw '{
    "nome": "<Product-Name>",
    "categoria":"<Category: VIDA, AUTO, VIAGEM, RESIDENCIAL, PATRIMONIO>",
    "preco_base": <BasePrice: 100.55>
}'
````
O primeiro endpoint é responsável por criar um novo produto de seguro e o segundo
responsável pela atualização de um produto de seguro.

# Solução

## Premissas
Para construir a solução, foi necessário considerar as seguintes afirmações verdadeiras:
- O sistema produto desejado é um sistema de borda, sendo necessário um protocolo de comunicação
bastante difundido no mercado (para tal a escolha foi a exposição de um endpoint REST)
- Não é possível criar um produto com o nome vazio ou nulo
- Não é possível criar um produto com a categoria vazia, nula ou inválida.
- Não é possível criar um produto com o preço base menor ou igual a zero.
- Para realizar a atualização de um produto, é necessário que ele tenha sido previamente
cadastrado e possuir o seu respectivo id.

## Escolhas técnicas:

### Organização do Repositório
Em um ambiente produtivo, os sistemas possuem objetivos e responsabilidades bem definidas,
separando os repositórios de definição e configuração de infraestrutura,
de sistemas produtos etc. Para tentar manter uma segreção de responsabilidades e ao mesmo tempo
facilitar a análise da solução, dentro do repositório (itau-seguros-backend-challenge-solution),
foi criado uma pasta chamada **Docker**, contendo toda a definição de infraestrutura necessária,
e uma pasta contendo o sistema produto (**insurance-product-challenge**).

### Design do software
O sistema produto foi desenvolvido utilizando como base os conceitos de arquitetura limpa
(recomendado no enunciado do exercício), seguindo o seguinte padrão:
- **/adapters**: Os adapters (ou adaptadores) são responsáveis por traduzir as interfaces do sistema para as interfaces externas e vice-versa.
  Por exemplo, os adapters podem conter classes que implementam controladores web (para processar requisições HTTP).
- **/configuration**: O package de configuração é responsável por centralizar a configuração do sistema e inicializar os componentes necessários.
Por exemplo a configuração de cache e o mapeamento das classes para utilização do pattern Strategy.
- **/domain**: O package de domínio contém as entidades e regras de negócio fundamentais do sistema.
- **/infrastructure**: O package de infraestrutura contém componentes que lidam com detalhes de implementação e tecnologia específica.
Exemplo arquivo para a persistência de dados.
- **/service**: O package de serviço contém componentes que implementam a lógica de negócios da aplicação.
- **/usecases**: O package de caso de uso contém as implementações específicas de cada caso de uso do sistema.

### Design Pattern
O design pattern escolhido foi o [Strategy](https://refactoring.guru/pt-br/design-patterns/strategy) pois, simplificadamente, ele
permite a espeficidade de comportamentos comuns. 

No caso específico, cada categoria
de produto criado possuía um cálculo distinto do preço tarifado com base no preço base.
Com o uso desse pattern, foi possível, centralizar a chamada do useCase em um único service, que com base na categoria informada pelo consumidor da aplicação,
poderia definir qual a classe mais adequada para a realização do cálculo do preço tarifado.

Assim caso haja a necessidade da criação de uma nova categoria e/ou do ajuste do cálculo do preço tarifado para apenas uma categoria, por exemplo,
conseguimos manter essas especificidades do comportamento comum (cálculo de preço tarifado), isoladas,
afetar as demais categorias do produto, seguindo um dos pilares do **SOLID**, o **Open/Closed Principle**.

### Explicação Geral

**Escolha de endpoints REST:** foi escolhido a exposição de endpoints REST, pois
no enunciado não foram definidos possíveis consumidores para o sistema produto.
Sendo assim, endpoints RESTs tornam-se versáteis pois clientes de dentro e de fora da organização,
poderão ter acesso as funcionalidades disponíveis.

**Utilização de Cache com Redis:** a escolha do cache tem como pilar central não permitir com que sejam criados produtos de seguro
com exatamente os mesmos parametros, sendo o Redis uma fonte extremamente rápida para essa validação.

**Logs:** para os logs, foi utilizado a biblioteca Log4J2, que são coletados pelo elasticsearch
e injetados no Kibana para a possibilidade de Trace distribuído, execução de queries, padronização, etc.

**Métricas:** para criação e exporte de métricas foi utilizado o micrometer (prometheus) e essas
métricas são importadas pelo Grafana para a construção de dashboards, por exemplo o
painel que realiza a contabilidade de produtos criados por categoria.

**Banco de Dados:** para a persistência dos produtos criados, foi escolhido uma base MySQL
pela sua robustez, por ser bem difundida no mercado (sem grandes problemas de curva de aprendizado),
que tem possibilidade de uso em serviços gerenciados de grandes cloud providers (como o Aurora da AWS), etc.

