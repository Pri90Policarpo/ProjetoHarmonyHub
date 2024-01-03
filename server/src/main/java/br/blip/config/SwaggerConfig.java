package br.blip.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Configuração do Swagger para Documentação da API HarmonyHub.
 *
 * Esta classe fornece configurações do Swagger para gerar documentação interativa da API.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * Configuração do Docket para o Swagger.
     *
     * @return Docket configurado para a API HarmonyHub.
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
          .select()
          .apis(RequestHandlerSelectors.basePackage("br.blip"))
          .build()
          .apiInfo(apiInfo());
    }

    /**
     * Informações da API para exibição na documentação Swagger.
     *
     * @return Objeto ApiInfo contendo detalhes da API.
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("HarmonyHub")
            .description("\"Consumidor de dados do chatbot Blip.\"")
            .version("1.0.0")
            .contact(new Contact("Priscila", "https://github.com/Pri90Policarpo", "policarpopriscilla@gmail.com"))
            .build();
    }
}
