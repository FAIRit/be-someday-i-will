package pl.fairit.somedayiwill.config;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.fairit.somedayiwill.avatar.AvatarController;
import pl.fairit.somedayiwill.book.BookController;
import pl.fairit.somedayiwill.movie.MovieController;
import pl.fairit.somedayiwill.user.AppUserController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

import static com.google.common.base.Predicates.or;
import static java.util.Objects.isNull;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
@ComponentScan(basePackageClasses = {AppUserController.class, AvatarController.class, BookController.class, MovieController.class})
public class SwaggerConfig {

    @Bean
    public Docket SwaggerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(getSwaggerPaths())
                .build()
                .securitySchemes(Lists.newArrayList(apiKey()))
                .globalResponseMessage(RequestMethod.GET,
                        List.of(new ResponseMessageBuilder()
                                .code(500)
                                .message("500 message")
                                .responseModel(new ModelRef("Error"))
                                .build()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Someday I will")
                .contact(new Contact("Ewelina Fiedorowicz", "someday I will provide an url",
                        "ewelinaformella@gmail.com"))
                .build();
    }

    @Bean
    SecurityScheme apiKey() {
        return new ApiKey("AUTHORIZATION", "token", "header");
    }

    private Predicate<String> getSwaggerPaths() {
        return or(
                regex("/auth.*"),
                regex("/users.*"));
    }
}
