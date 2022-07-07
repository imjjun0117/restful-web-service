package com.example.restfulwebservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class SwaggerConfig{
    private static final Contact DEFAULT_CONTACT = new Contact("Joony",
            "http://www.joneconsulting.co.kr","aaaa0117@naver.com");
    private static final ApiInfo DEFAULT_API_INFO = new ApiInfo("Awesome API Title",
            "My User management REST API service","1.0", "urn:tos",
            DEFAULT_CONTACT,"Apache 2.0",
            "http://www.apache.org/licenses/LICENSE-2.0",new ArrayDeque<>());
    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<>(
            Arrays.asList("application/json","application/xml"));


//Spring boot 2.6버전 이후에 spring.mvc.pathmatch.matching-strategy 값이 ant_apth_matcher에서
// path_pattern_parser로 변경되면서 몇몇 라이브러리(swagger포함)에 오류가 발생할 수 있다고 한다.
//spring:
//  mvc:
//    pathmatch:
//      matching-strategy: ant_path_matcher 를 application.yml에 추가
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(DEFAULT_API_INFO)
                .produces(DEFAULT_PRODUCES_AND_CONSUMES)
                .consumes(DEFAULT_PRODUCES_AND_CONSUMES);
    }
}//class
