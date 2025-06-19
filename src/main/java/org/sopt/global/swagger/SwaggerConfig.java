package org.sopt.global.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());
    }

    @Bean
    public Info apiInfo(){
        return new Info()
                .title("API Test")
                .description("솝트 게시판 서비스 Swagger UI")
                .version("1.0.1");
    }
}
