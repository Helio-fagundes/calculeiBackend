# 🏛️ Calculei - Ministério Público

![Java](https://img.shields.io/badge/Java-21_LTS_(Temurin)-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Red Hat OpenShift](https://img.shields.io/badge/Red_Hat_OpenShift-EE0000?style=for-the-badge&logo=redhat&logoColor=white)

## 📖 Visão Geral

O **Calculei** é uma plataforma automatizada para cálculos judiciais e correção monetária, desenvolvida para atender às demandas do Ministério Público. 

A aplicação sincroniza-se automaticamente com os índices oficiais do **Banco Central (Bc)**, garantindo extrema precisão e atualização em tempo real para cálculos de juros e correções (como Selic e TR). O sistema foi projetado para oferecer uma interação fluida e segura para o usuário final, com documentação interativa para a equipe técnica.

## 🏗️ Arquitetura e Engenharia de Software

Este projeto foi construído seguindo rigorosos padrões de qualidade de software do mercado corporativo, visando manutenibilidade, escalabilidade e resiliência:

* **Clean Architecture (Arquitetura Limpa):** Isolamento total das regras de negócio do Ministério Público em relação a frameworks e infraestrutura externa.
* **Domain-Driven Design (DDD):** Modelagem focada no domínio do problema (Cálculos Judiciais), com domínios ricos e bem definidos.
* **SOLID & Clean Code:** Código expressivo, testável e de fácil leitura, garantindo que novos desenvolvedores possam contribuir rapidamente.

## 🚀 Tecnologias Utilizadas

* **Linguagem:** Java 21 LTS (Eclipse Temurin)
* **Framework:** Spring Boot (Web, Data JPA, Validation)
* **Banco de Dados:** PostgreSQL
* **Documentação:** Swagger / Springdoc OpenAPI
* **Deploy/Orquestração:** Preparado para ambiente **Red Hat OpenShift**

## ⚙️ Pré-requisitos

Para executar este projeto localmente, você precisará de:

1.  [JDK 21](https://adoptium.net/pt-BR/) (Recomendado: Eclipse Temurin) instalado e configurado na variável de ambiente `JAVA_HOME`.
2.  [Apache Maven](https://maven.apache.org/) instalado.
3.  [PostgreSQL](https://www.postgresql.org/) rodando localmente na porta 5432 (ou configurado conforme suas variáveis).

## 🔑 Variáveis de Ambiente

O projeto utiliza variáveis de ambiente para garantir a segurança das credenciais e facilitar o deploy no Red Hat OpenShift. Configure as seguintes variáveis na sua máquina ou na sua IDE antes de iniciar:

| Variável | Descrição | Exemplo Local |
| :--- | :--- | :--- |
| `PORT` | Porta onde o Spring Boot vai rodar | `8080` |
| `SWHOST` | Host base para a documentação do Swagger | `localhost:8080` |
| `OPENSHIFT_DB` | URL JDBC de conexão com o PostgreSQL | `jdbc:postgresql://localhost:5432/calculei` |
| `dbusername` | Usuário do banco de dados | `postgres` |
| `dbpassword` | Senha do banco de dados | `sua_senha_aqui` |

## 🛠️ Como Executar o Projeto

1. **Clone o repositório:**
   ```bash
   git clone [https://github.com/Helio-fagundes/calculeiBackend.git](https://github.com/Helio-fagundes/calculeiBackend.git)
   cd calculeiBackend

2. **Compile o projeto com o Maven:**
   ```bash
   mvn clean install

3. **Inicie a aplicação:**
   ```bash
   mvn spring-boot:run

## 🧪 Estratégia de Testes (BDD)

O projeto adota a prática de **BDD (Behavior-Driven Development)** para garantir que as regras de cálculo de juros e correção monetária estejam blindadas e documentadas de forma viva.

Como utilizamos a **Clean Architecture**, os testes unitários são focados no isolamento dos **Use Cases**. Isso significa que as regras de negócio são validadas de forma pura, sem a necessidade de levantar o banco de dados PostgreSQL ou o contexto do Spring, tornando a execução extremamente rápida.

### Como Executar os Testes

Para rodar a suíte de testes unitários e comportamentais, execute o comando:

```bash
mvn test
```
## 📚 Documentação da API (Swagger)

A API possui uma interface interativa (Swagger UI) que permite testar todos os endpoints, visualizar os schemas de entrada/saída e analisar os possíveis retornos de erro tratados pelo sistema.

Com a aplicação rodando, acesse no seu navegador:
👉 http://localhost:8080/swagger-ui.html (Ajuste a porta conforme sua variável PORT)

Desenvolvido com excelência técnica para o Ministério Público.
