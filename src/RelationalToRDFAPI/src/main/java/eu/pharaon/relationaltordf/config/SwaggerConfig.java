package eu.pharaon.relationaltordf.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addServersItem(new Server().url("http://localhost:8080"))
                .info(new Info()
                        .title("Relational Data to RDF ETL API")
                        .version("1.0")
                        .description("""
                                API that converts JSON data into RDF (Resource Description Framework) using RML (RDF Mapping Language). \
                                                

                                The application exposes an API endpoint that accepts JSON data, processes it using a specified RML mapping, and returns the RDF data in Turtle format."""));

    }
}

