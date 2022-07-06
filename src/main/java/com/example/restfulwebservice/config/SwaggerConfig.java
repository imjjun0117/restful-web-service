package com.example.restfulwebservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig{
    private static final Contact DEFAULT_CONTACT = new Contact("Joony",
            "http://www.joneconsulting.co.kr","aaaa0117@naver.com");

//Spring boot 2.6버전 이후에 spring.mvc.pathmatch.matching-strategy 값이 ant_apth_matcher에서
// path_pattern_parser로 변경되면서 몇몇 라이브러리(swagger포함)에 오류가 발생할 수 있다고 한다.
//spring:
//  mvc:
//    pathmatch:
//      matching-strategy: ant_path_matcher 를 application.yml에 추가
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }
}//class
