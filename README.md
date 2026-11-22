# AG Soluções Corporativas — API

API backend da aplicação AG Soluções Corporativas, desenvolvida com Java, Spring Boot e PostgreSQL.

## Requisitos

- Git
- Docker Desktop (Windows/macOS) ou Docker Engine (Linux), com Docker Compose v2
- Java 25 somente para executar a API diretamente na máquina; o modo totalmente Docker não exige Java instalado

Confira as instalações:

```bash
docker --version
docker compose version
```

## Preparação do ambiente

Clone o repositório e entre na pasta:

```bash
git clone https://github.com/mviisday/Agsolucoescorporativas-api.git
cd Agsolucoescorporativas-api
```

Crie seu arquivo local de configuração a partir do modelo:

```bash
cp .env.example .env
```

No PowerShell:

```powershell
Copy-Item .env.example .env
```

O `.env` contém configuração local e não deve ser commitado. Os valores do exemplo são apenas para desenvolvimento local; use credenciais próprias fora desse contexto. Se mudar `POSTGRES_DB`, `POSTGRES_USER` ou `POSTGRES_PASSWORD`, mantenha os valores correspondentes de `SPRING_DATASOURCE_*` alinhados.

## Executar tudo com Docker

Na raiz do projeto, com o Docker em execução:

```bash
docker compose --profile full up --build -d
```

O Compose inicia o PostgreSQL e aguarda o banco ficar saudável antes de iniciar a API. O primeiro build pode demorar porque baixa as imagens e dependências Gradle. A API fica disponível em `http://localhost:8080` e o PostgreSQL em `localhost:5433`.

Para conferir o estado e acompanhar a inicialização:

```bash
docker compose --profile full ps
docker compose --profile full logs -f api
```

Para parar os serviços sem apagar os dados do banco:

```bash
docker compose --profile full down
```

Os dados do PostgreSQL ficam em um volume Docker e permanecem após `down` e recriações dos containers. **Para apagar os dados locais e começar com um banco vazio**, use `docker compose --profile full down -v`; essa ação é irreversível para o volume local.

## Desenvolver com a API na máquina

Esse modo é útil para executar e depurar a aplicação pela IDE. O Java 25 é necessário.

1. Suba o banco:

   ```bash
   docker compose up -d postgres
   ```

2. Aguarde o banco ficar saudável:

   ```bash
   docker compose ps
   ```

3. Inicie a API pela IDE ou pelo Gradle Wrapper:

   ```bash
   ./gradlew bootRun
   ```

   No Windows, use `gradlew.bat bootRun`.

O Spring Boot lê as configurações do `.env` por meio do `application.properties`. Nessa execução a URL usa `localhost:5433`; dentro do Docker Compose a API usa o endereço interno `postgres:5432`.

Para parar apenas o banco:

```bash
docker compose stop postgres
```

## Endpoints de documentação

Quando a API estiver iniciada:

- Swagger UI: <http://localhost:8080/swagger-ui/index.html>
- OpenAPI: <http://localhost:8080/v3/api-docs>

## Comandos úteis

```bash
# Ver containers ativos
docker compose --profile full ps

# Logs de todos os serviços
docker compose --profile full logs -f

# Recriar a imagem da API após alterar o código
docker compose --profile full up --build -d api

# Parar e remover containers e rede, preservando o volume do banco
docker compose --profile full down
```

## Problemas comuns

- **Erro de arquivo `.env` ausente:** confirme que copiou `.env.example` para `.env` na raiz do projeto.
- **Porta 5433 ocupada:** encerre o serviço que a utiliza ou altere a porta publicada `5433:5432` em `docker-compose.yml`. Se alterá-la, atualize também a URL local `SPRING_DATASOURCE_URL` no `.env`.
- **Porta 8080 ocupada:** encerre o outro serviço ou altere a porta publicada `8080:8080` em `docker-compose.yml`.
- **Credenciais alteradas, mas o banco antigo continua falhando:** o volume conserva o banco criado anteriormente. Para recriá-lo do zero, rode `docker compose --profile full down -v` e suba novamente. Isso apaga os dados locais.
- **API não inicia:** consulte `docker compose --profile full logs -f api` e confirme que o PostgreSQL aparece como saudável em `docker compose --profile full ps`.

## Tecnologias

- Java 25 e Spring Boot
- Gradle Wrapper
- PostgreSQL 17
- Spring Data JPA e Flyway
- MapStruct, Lombok e Springdoc OpenAPI
