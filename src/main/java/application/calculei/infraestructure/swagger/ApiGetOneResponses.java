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
        @ApiResponse(responseCode = "200", description = "Recurso encontrado com sucesso."),
        //TODO: fazer a response para a saida com sucesso dos dados!

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
                )),

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
        )
})
public @interface ApiGetOneResponses {
}
