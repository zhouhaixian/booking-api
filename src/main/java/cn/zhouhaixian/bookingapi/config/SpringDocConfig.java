package cn.zhouhaixian.bookingapi.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
@Profile("dev")
public class SpringDocConfig {
    @Value("${application.project-url}")
    private String projectUrl;

    @Value("${application.name}")
    private String name;

    @Value("${application.description}")
    private String description;

    @Value("${application.version}")
    private String version;

    @Value("${application.license}")
    private String license;

    @Value("${application.author.name}")
    private String authorName;

    @Value("${application.author.email}")
    private String authorEmail;

    @Value("${application.author.github}")
    private String authorGithub;

    @Bean
    public OpenAPI defaultOpenAPI() {
        SecurityScheme securityScheme = new io.swagger.v3.oas.models.security.SecurityScheme()
                .type(io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT");
        Components components = new Components().addSecuritySchemes("Authorization", securityScheme);
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("Authorization", Arrays.asList("Bearer"));
        return new OpenAPI().info(new Info().title(name).description(description).version("v" + version)
            .license(new License().name(license).url(projectUrl)).contact(new Contact().name(authorName)
                .email(authorEmail).url(authorGithub))
        ).components(components).addSecurityItem(securityRequirement);
    }
}
