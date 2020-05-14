package br.com.paulomoreira.starB2W.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
            .apis(RequestHandlerSelectors.basePackage("br.com.paulomoreira.starB2W.resource"))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(apiInfo())
            .useDefaultResponseMessages(true);
    }
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
			.title("Star Wars Planets API")
			.description("This API is used to control the planets of Star Wars.")
			.version("1.0.0")
			//.termsOfServiceUrl("Terms of service")
			//.contact(new Contact())
			//.license("License of API")
			//.licenseUrl("API license URL")
			.extensions(Collections.emptyList())
		.build();
    }

}
