package pe.edu.upc.spring.mongodb.security.configSwagger;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI myOpenAPI() {
        // General configuration

        var openApi = new OpenAPI();
        openApi
                .info(new Info()
                        .title("Finances API")
                        .description("Finances application REST API documentation.")
                        .version("v1.0.0")
                        .license(new License().name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .externalDocs(new ExternalDocumentation()
                        .description("Kmoneta Wiki Documentation")
                        .url("https://Finances.wiki.github.io/docs"));

        return openApi;
    }
}