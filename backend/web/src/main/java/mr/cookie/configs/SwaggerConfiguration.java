package mr.cookie.configs;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfiguration {

    @Value("${spring.application.name:#{null}}")
    private @Nullable String appName;

    @Value("${spring.application.version:#{null}}")
    private @Nullable String version;

    @Value("${spring.application.description:#{null}}")
    private @Nullable String description;

    @Value("${developer.name:#{null}}")
    private @Nullable String developerName;

    @Value("${developer.email:#{null}}")
    private @Nullable String developerEmail;

    @Value("${developer.url:#{null}}")
    private @Nullable String developerUrl;

    @Bean
    public @NotNull Contact contact() {
        return new Contact(developerName, developerUrl, developerEmail);
    }

    @Bean
    public @NotNull ApiInfo apiInfo(@NotNull Contact contact) {
        return new ApiInfoBuilder()
                .title(appName)
                .description(description)
                .version(version)
                .contact(contact)
                .license("GNU General Public License")
                .licenseUrl("https://www.gnu.org/licenses/gpl-3.0.en.html")
                .build();
    }

    @Bean
    public @NotNull Docket api(@NotNull ApiInfo apiInfo) {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("mr.cookie"))
                .paths(PathSelectors.any())
                .build();
    }

}
