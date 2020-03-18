package com.github._4hp007.inventory.configuration;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

    @Bean
    Docket api() {
        Contact contact = new Contact("Harshal Pawar",
                                      "https://github.com/4hp007",
                                      "4hp007@gmail.com");

        ApiInfo apiInfo = new ApiInfo("Inventory Management REST Api Documentation",
                                      "This page documents all the endpoints for inventory management",
                                      "1.0",
                                      "Terms of service",
                                      contact,
                                      "Apache License Version 2.0", "https://www.apache.org/licenses/LICENSE-2.0",
                                      Lists.newArrayList());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.github._4hp007.inventory.controller"))
                .paths(PathSelectors.ant("/api/**"))
                .build();
    }

}
