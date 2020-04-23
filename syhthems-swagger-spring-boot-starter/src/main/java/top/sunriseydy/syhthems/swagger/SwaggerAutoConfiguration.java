package top.sunriseydy.syhthems.swagger;

import com.fasterxml.classmate.TypeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import top.sunriseydy.syhthems.common.vo.ResultVO;

import java.time.LocalDate;

import static springfox.documentation.schema.AlternateTypeRules.newRule;

/**
 * 自定义的 swagger 配置
 *
 * <p>http://springfox.github.io/springfox/docs/current/#configuration-explained</p>
 *
 * @author SunriseYDY
 * @date 2020-04-15 15:17
 */
@Configuration
@Import({
        Swagger2Configuration.class
})
public class SwaggerAutoConfiguration {
    @Autowired
    private TypeResolver typeResolver;

    @Bean
    public Docket petApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("top.sunriseydy.syhthems"))
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/")
                .directModelSubstitute(LocalDate.class, String.class)
                .genericModelSubstitutes(ResponseEntity.class, ResultVO.class)
                .alternateTypeRules(
                        newRule(typeResolver.resolve(DeferredResult.class,
                                typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
                                typeResolver.resolve(WildcardType.class)))
                .useDefaultResponseMessages(true)
                .enableUrlTemplating(true)
                ;
    }

    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Syhthems Platform Api Documentation")
                .build();
    }
}
