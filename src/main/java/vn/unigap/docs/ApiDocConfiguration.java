package vn.unigap.docs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Nhật Nguyễn",
                        email = "nhatnguyen@email.com",
                        url = "http://javierdemo.vn"
                ),
                version = "1.0.0",
                title = "API doc for recruitment service project"
        ),
        servers = {
                @Server(
                        url = "http://localhost:8080",
                        description = "Localhost"
                )
        }
)
@SecurityScheme(
        name = "Authorization",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@Configuration
public class ApiDocConfiguration {
}
