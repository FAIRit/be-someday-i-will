package pl.fairit.somedayiwill.config;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.fairit.somedayiwill.avatar.AvatarController;
import pl.fairit.somedayiwill.book.usersbooks.BookController;
import pl.fairit.somedayiwill.movie.usersmovies.MovieController;
import pl.fairit.somedayiwill.security.jwt.AuthResponse;
import pl.fairit.somedayiwill.user.AppUserController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
@ComponentScan(basePackageClasses = {AppUserController.class, AvatarController.class, BookController.class, MovieController.class})
public class SwaggerConfig {

    public static final String DEFAULT_INCLUDE_PATTERN = "/users/.*";


    @Bean
    public Docket SwaggerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(getSwaggerPaths())
                .build()
                .securitySchemes(Lists.newArrayList(apiKey()))
                .securityContexts(Lists.newArrayList(securityContext()))
                .globalResponseMessage(RequestMethod.GET,
                        List.of(new ResponseMessageBuilder()
                                .code(500)
                                .message("500 message")
                                .responseModel(new ModelRef("Error"))
                                .build()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Someday I Will")
                .contact(new Contact("Ewelina Fiedorowicz", "someday I will provide an url",
                        "ewelinaformella@gmail.com"))
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT",  "Authorization", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN))
                .build();
    }

    List<SecurityReference> defaultAuth() {
        var authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        var authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(
                new SecurityReference("JWT", authorizationScopes));
    }

    private Predicate<String> getSwaggerPaths() {
        return or(
                regex("/auth.*"),
                regex("/books/search.*"),
                regex("/movies/search.*"),
                regex("/users.*"));
    }
}
