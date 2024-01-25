package ie.fieldublin.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                description = "OpenApi documentation for the Fiel Dublin Back-end project",
                title = "OpenApi specification - Fiel Dublin Back-end",
                version = "1.0",
                license = @License(
                        name = "Licence name",
                        url = "https://licenceurl"
                ),
                termsOfService = "Terms of service",
                contact = @Contact(
                        email = "matos.rafael@gmail.com",
                        name = "Rafael Infante",
                        url = "https://github.com/rafaelinfante"
                )
        ),
        servers = {
                @Server(
                        description = "Local env",
                        url = "http://localhost:8080/"
                ),
                @Server(
                        description = "Sandbox env",
                        url = "https://rafaelinfante.net/"
                )
        }
)
public class OpenApiConfig {
}
