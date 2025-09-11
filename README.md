📚 Estude.My - Backend

API do Estude.My, desenvolvida em Spring Boot + MongoDB, responsável por gerenciar usuários, autenticação, cursos e integrações com o frontend.

🛠️ Tecnologias Utilizadas

Java 21

Spring Boot 3.x

Spring Web

Spring Data MongoDB

Spring Boot DevTools

MongoDB

Gradle (ou Maven, dependendo do seu projeto)

Docker (para containerização)

📂 Estrutura de Pastas
backend/
 ├── build.gradle ou pom.xml   # Configurações do projeto
 ├── src/
 │   ├── main/
 │   │   ├── java/com/estudemy/backend/
 │   │   │   ├── Application.java        # Classe principal (ponto de entrada)
 │   │   │   ├── model/                  # Entidades e DTOs
 │   │   │   ├── repository/             # Interfaces de acesso ao banco (MongoRepository)
 │   │   │   ├── service/                # Regras de negócio
 │   │   │   └── controller/             # Endpoints REST
 │   │   └── resources/
 │   │       ├── application.properties  # Configurações do Spring Boot
 │   │       └── application.yml (opcional)
 │   └── test/                           # Testes unitários e de integração
 └── Dockerfile

⚙️ Configuração do Banco de Dados

No arquivo src/main/resources/application.properties configure o MongoDB:

spring.data.mongodb.uri=mongodb://localhost:27017/estudemy
spring.data.mongodb.database=estudemy
server.port=8080


💡 Em produção, use variáveis de ambiente para MONGODB_URI em vez de colocar credenciais no código.

▶️ Como Rodar Localmente
1️⃣ Pré-requisitos

Java 21+

Gradle
 ou Maven

MongoDB
 (local ou em container)

2️⃣ Executar o projeto

Com Gradle:

./gradlew bootRun


Com Maven:

./mvnw spring-boot:run


API disponível em: http://localhost:8080

🐳 Rodando com Docker

Build da imagem:

docker build -t estudemy-backend .


Rodar o container:

docker run -p 8080:8080 --name estudemy-backend estudemy-backend

🧪 Testes

Rodar testes unitários:

./gradlew test


ou

./mvnw test

📌 Endpoints Principais (Exemplo)
Método	Rota	Descrição
GET	/api/users	Lista todos os usuários
POST	/api/users	Cria um novo usuário
POST	/api/auth/login	Faz login e retorna JWT
GET	/api/courses	Lista cursos disponíveis

💡 Use o Swagger ou o Postman para explorar a API (se configurado no projeto).

🤝 Contribuição

Faça um fork do projeto

Crie uma branch para sua feature:

git checkout -b minha-feature


Commit suas alterações:

git commit -m "Adiciona minha feature"


Envie para o repositório:

git push origin minha-feature


Abra um Pull Request 🎉

📝 Licença

Este projeto está sob a licença MIT - veja o arquivo LICENSE
 para detalhes.
