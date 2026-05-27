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
        @ApiResponse(responseCode = "200", description = "Listagem realizada com sucesso.",
                content = @Content(
                        mediaType = "application/json",
                        examples = @ExampleObject(
                                name = "Exemplo de Sucesso",
                                summary = "Quando a listagem é realizada com sucesso.",
                                value = """
                                [
                                    "CDI",
                                    "SELIC",
                                    "IPCA",
                                    "IPCA_E",
                                    "IGP_DI",
                                    "TR",
                                    "IGP_M",
                                    "TJ11960",
                                    "TJ6899"
                                ]
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
public @interface ApiGetAllResponses {
}
