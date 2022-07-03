package com.example.restfulwebservice.helloworld;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloWorldController {

    @Autowired
    private MessageSource messageSource;
    //GET
    // /hello-world(end point)
    // @RequestMapping(method=RequestMethod.GET, path="/hello-world") 최근에는 사용하지 않음
    @GetMapping(path = "/hello-world")
    public String helloWorld(){
        return "Hello World";
    }//helloWorld

    @GetMapping(path = "/hello-world-bean")
    public HelloWorldBean helloWorldBean(){
        //선언된 클래스가 없으면 선언부분에서 alt+enter
        return new HelloWorldBean("Hello World");
    }//helloWorld

    @GetMapping(path = "/hello-world-bean/path-variable/{name}")
    public HelloWorldBean helloWorldBean(@PathVariable(value="name") String name){
        return new HelloWorldBean(String.format("Hello World, %s",name));
    }//helloWorld

    @GetMapping(path="/hello-world-internationalized")
    public String helloWorldInternationalized(
            @RequestHeader(name="Accept-Language",required = false) Locale locale){
        return messageSource.getMessage("greeting.message",null,locale);
    }//helloWorldInternationalized
}//class
