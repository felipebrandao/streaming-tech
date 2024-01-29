# Streaming de Vídeo 🎥

## Ferramentas e Tecnologias Utilizadas

| Ferramenta                | Tecnologia                                                                                                                                                                                                            |
|---------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **🛠 IDE**                | [IntelliJ IDEA](https://www.jetbrains.com/idea/)                                                                                                                                                                      |
| **🚀 Framework**          | [Spring Boot](https://spring.io/projects/spring-boot), [Spring WebFlux](https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html), [Spring Data](https://spring.io/projects/spring-data) |
| **📊 Banco de Dados**     | [MongoDB](https://www.mongodb.com/)                                                                                                                                                                                   |
| **🪶 Lombok**             | Redução de código boilerplate                                                                                                                                                                                         |
| **📜 Clean Architecture** | [Princípios e Práticas](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)                                                                                                                 |
| **📋 Testes**             | Testes Unitários e de Integração                                                                                                                                                                                      |
| **🔗 Controle de Versão** | [Git (GitHub)](https://github.com/)                                                                                                                                                                                   |
| **📜 Documentação**       | [Swagger](https://swagger.io/)                                                                                                                                                                                        |

## Desafios Encontrados

Durante o desenvolvimento do projeto de streaming de vídeo, enfrentamos desafios na implementação de um sistema reativo e na integração com o MongoDB reativo. A arquitetura limpa (Clean Architecture) também proporcionou um desafio adicional na organização e separação das responsabilidades nas diferentes camadas do sistema.

## Funcionalidades Implementadas

### Vídeos

- **Criação, Atualização, Listagem e Exclusão**: Possibilidade de gerenciar vídeos com título, descrição, URL e data de publicação.
- **Paginação e Ordenação**: Listagem paginada e ordenável por data de publicação.
- **Filtros de Busca**: Implementação de filtros por título e data de publicação na listagem.
- **Marcação de Vídeos como Favoritos**: Sistema para que usuários possam marcar vídeos como favoritos.
- **Categorização de Vídeos**: Possibilidade de associar categorias aos vídeos, com filtragem por categoria.
- **Recomendação de Vídeos**: Sistema de recomendação com base nos vídeos marcados como favoritos pelo usuário.

## Observações

Certifique-se de ter o MongoDB instalado e em execução localmente antes de iniciar a aplicação.

```bash
docker run -d -p 27017:27017 --name mongodb mongo
```
