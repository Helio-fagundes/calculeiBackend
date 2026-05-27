package application.calculei.infraestructure.swagger;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Recurso encontrado com sucesso.",
                content = @Content(
                        mediaType = "application/json",
                        examples = @ExampleObject(
                                name = "Exemplo de resposta bem-sucedida",
                                summary = "Quando o recurso é encontrado e retornado com sucesso.",
                                value = """
                                        {
                                          "geradoEm": "2026-05-27T19:38:27.000Z",
                                          "totalLancamentos": 1,
                                          "lancamentos": [
                                            {
                                              "id": 1,
                                              "descricao": "Descrição do lançamento",
                                              "descricaoComplementar": "Informação adicional (opcional)",
                                              "dataInicial": "2023-01-01",
                                              "dataCalculo": "2023-12-31",
                                              "valorPrincipal": 1000.00,
                                              "indiceCorrecao": "ipca",
                                              "valorAtualizado": 1046.20,
                                              "dias": 364,
                                              "percentualCorrecao": 4.62,
                                              "indiceJuros": "poupança",
                                              "dataInicioJuros": "2023-01-01",
                                              "dataFimJuros": "2023-12-31",
                                              "diasJuros": 364,
                                              "fatorJuros": 1.0617,
                                              "percentualJurosAcumulado": 6.17,
                                              "juros": 64.55,
                                              "total": 1110.75,
                                              "itensJuros": [
                                                {
                                                  "indice": "poupança",
                                                  "taxa": "0,5% a.m.",
                                                  "dataInicio": "2023-01-01",
                                                  "dataFim": "2023-12-31",
                                                  "dias": 364,
                                                  "percentual": 6.17,
                                                  "valor": 64.55
                                                }
                                              ]
                                            }
                                          ]
                                        }
                                        """
                        )
                )
        ),

        @ApiResponse(responseCode = "400", description = "Requisição malformada ou dados inválidos.",
                content = @Content(
                        mediaType = "application/json",
                        examples = @ExampleObject(
                                name = "Exemplo de requisição malformada",
                                summary = "Quando o formato do token é inválido.",
                                value = """
                                     {
                                         "status": 400,
                                         "erro": "Valor nulo ou vazio",
                                         "mensagem": "o(a) Url está contendo valor nulo ou vazio.",
                                         "timestamp": "2026-05-27T16:35:10.976729"
                                     }
                                """
                        )
                )
        ),

        @ApiResponse(responseCode = "404", description = "Recurso não encontrado para o ID fornecido.",
                content = @Content(
                        mediaType = "application/json",
                        examples = {
                                @ExampleObject(
                                    name = "Exemplo de historico não encontrado",
                                    summary = "Quando o recurso com o ID especificado não existe.",
                                    value = """
                                        {
                                            "status": 404,
                                            "erro": "Histórico não encontrado",
                                            "mensagem": "Histórico com token '123456789' não encontrado.",
                                            "timestamp": "2026-05-27T16:19:11.667631"
                                        }
                                    """

                                ),
                                @ExampleObject(
                                        name = "Exemplo de recurso não encontrado",
                                        summary = "Quando o recurso solicitado não existe ou não pode ser encontrado.",
                                        value = """
                                        {
                                            "status": 404,
                                            "erro": "Recurso não encontrado",
                                            "mensagem": "O recurso solicitado não foi encontrado. Por favor, verifique a URL e tente novamente.",
                                            "timestamp": "2026-05-27T16:45:50.366444"
                                        }
                                        """
                                )
                        }
                )
        ),

        @ApiResponse(responseCode = "500", description = "Erro interno no servidor.",
                content = @Content(
                        mediaType = "application/json",
                        examples = {
                                @ExampleObject(
                                        name = "Exemplo de erro interno",
                                        summary = "Quando ocorre um erro inesperado no servidor.",
                                        value = """
                                        {
                                            "status": 500,
                                            "erro": "Erro inesperado",
                                            "mensagem": "Ocorreu um erro interno. Nossa equipe já foi notificada.",
                                            "timestamp": "2026-05-26T15:38:57.073775"
                                        }
                                        """
                                ),
                                @ExampleObject(
                                        name = "Exemplo de banco de dados inacessível",
                                        summary = "Quando o banco de dados está inacessível.",
                                        value = """
                                        {
                                            "status": 500,
                                            "erro": "Erro interno no servidor",
                                            "mensagem": "Desculpe, ocorreu uma instabilidade temporária em nossos serviços. Por favor, tente novamente em alguns minutos.",
                                            "timestamp": "2026-05-26T15:38:57.073775"
                                        }
                                        """
                                )
                        }
                )
        )
})
public @interface ApiGetOneResponses {
}
