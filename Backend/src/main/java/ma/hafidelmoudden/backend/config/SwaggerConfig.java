package ma.hafidelmoudden.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI bankCreditOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Bank Credit Management API")
                        .description("REST API for managing bank credits, clients, and remboursements")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Hafid Elmoudden")
                                .email("hafidelmoudden2003@gmail.com")
                                .url("https://hafidelmoudden.github.io/"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html")));
    }
} 