package com.example.restfulwebservice.helloworld;
//lombok

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HelloWorldBean {
    private String message;

//    public HelloWorldBean(String message) {
//        this.message=message;
//    }//HelloWorldBean
}//class
