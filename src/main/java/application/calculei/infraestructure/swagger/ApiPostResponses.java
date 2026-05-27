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
        @ApiResponse(responseCode = "200", description = "Requisição feita com sucesso.",
                content = @Content(
                        mediaType = "application/json",
                        examples = @ExampleObject(
                                name = "Exemplo de Sucesso",
                                summary = "Quando o body está correto e validado.",
                                value = """
                                        {
                                            "startDate": "2020-01-01",
                                            "endDate": "2026-01-01",
                                            "businessDays": 1,
                                            "finalValue": 1.00,
                                            "accumulatedFactor": 1.00
                                        }
                                        """
                        )
                )),

        @ApiResponse(responseCode = "400", description = "Requisição malformada ou dados inválidos.",
                content = @Content(
                        mediaType = "application/json",
                        examples = {
                                @ExampleObject(
                                        name = "Exemplo de periodo Invertido",
                                        summary = "Quando a data final é anterior a data inicial",
                                        value = """
                                                {
                                                    "status": 400,
                                                    "erro": "Data inválida",
                                                    "mensagem": "A data de término (2019-01-01) deve ser anterior à data de início (2020-01-01).",
                                                    "timestamp": "2026-05-26T15:38:57.073775"
                                                }
                                                """
                                ),
                                @ExampleObject(
                                        name = "Exemplo de data no futuro",
                                        summary = "Quando a data é posterior a data atual",
                                        value = """
                                                {
                                                    "status": 400,
                                                    "erro": "Data inválida",
                                                    "mensagem": "A data de início não pode ser posterior à data atual.",
                                                    "timestamp": "2026-05-26T15:38:57.073775"
                                                }
                                                """
                                ),
                                @ExampleObject(
                                        name = "Exemplo de valor Invalido",
                                        summary = "Quando o valor é negativo ou nulo",
                                        value = """
                                                {
                                                    "status": 400,
                                                    "erro": "Valor inválido",
                                                    "mensagem": "O valor para a correção monetária deve ser superior a 0.",
                                                    "timestamp": "2026-05-26T15:38:57.073775"
                                                }
                                                """
                                ),
                                @ExampleObject(
                                        name = "Exemplo de valor nulo",
                                        summary = "Quando o valor para correção monetária ou a data é nulo",
                                        value = """
                                                {
                                                    "status": 400,
                                                    "erro": "Valor inválido",
                                                    "mensagem": "Um valor necessário para a operação está nulo. Por favor, verifique os dados fornecidos e tente novamente.",
                                                    "timestamp": "2026-05-26T15:38:57.073775"
                                                }
                                                """
                                ),
                                @ExampleObject(
                                        name = "Exemplo de data com formato errado",
                                        summary = "Quando a data é fornecida em um formato diferente de 'yyyy-MM-dd'",
                                        value = """
                                                {
                                                    "status": 400,
                                                    "erro": "Formato de data inválida",
                                                    "mensagem": "O formato da data fornecida é inválido. Por favor, utilize o formato 'yyyy-MM-dd' e tente novamente.",
                                                    "timestamp": "2026-05-26T15:38:57.073775"
                                                }
                                                """
                                )
                        }
                )),

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
        ),

        @ApiResponse(responseCode = "422", description = "Entidade não processável - Quando o body da requisição está malformado ou contém dados que não podem ser processados.",
                content = @Content(
                        mediaType = "application/json",
                        examples = @ExampleObject(
                                name = "Exemplo de não encontrar dados em determinada data",
                                summary = "Quando a data fornecida não contém valores para a correção monetária.",
                                value = """
                                        {
                                            "status": 422,
                                            "erro": "Dado não encontrado",
                                            "mensagem": "Nenhum dado encontrado: Nenhum índice CDI encontrado para o período informado.",
                                            "timestamp": "2026-05-26T17:47:36.010516"
                                        }
                                        """
                        )
                )
        ),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado - Quando o recurso solicitado não existe ou não pode ser encontrado.",
                content = @Content(
                        mediaType = "application/json",
                        examples = @ExampleObject(
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
                )
        )
})
public @interface ApiPostResponses {
}
