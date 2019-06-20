package cn.mlick.aopservicedemo.conf;

import com.google.common.base.Predicates;
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
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author fengzirong
 */
@Configuration
@EnableSwagger2
public class Swagger2Configuration {


  @Bean
  public Docket createRestApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("cn.mlick.aopservicedemo"))
        .paths(Predicates.not(PathSelectors.regex("/error.*")))
        .paths(PathSelectors.regex("/.*"))
        .build()
        .apiInfo(apiInfo());
  }


  @Bean
  public ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("")
        .description("")
        .contact(new Contact("", "", ""))
        .version("1.0")
        .build();
  }
}
