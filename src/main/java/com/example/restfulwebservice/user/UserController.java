package com.example.restfulwebservice.user;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
public class UserController {
    private UserDaoService service;

    public UserController(UserDaoService service) {

        this.service = service;
    }//UserController

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return service.findAll();
    }//retrieveAllUsers

    //GET /users/1 or users/10
    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id) {
        User user = service.findOne(id);
        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }//end if

        //HATEOAS
        EntityModel<User> model = EntityModel.of(user);
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());

        model.add(linkTo.withRel("all-users"));


        return model;
    }//retrieveUser

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = service.save(user);
        // 위와 같이 사용자를 추가 시 사용자 id를 알기 위해서는 재조회를 해야한다. -> 트래픽 발생
        // ServletUriComponentBuilder를 사용하여 id값을 가지 url을 반환 -> 더 효율적인 코드
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }//createUser

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        User user = service.deleteById(id);

        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }//end if
    }//deleteUser

    @PutMapping("/users")
    public void modifyUser(@RequestBody User user) {
        User userTemp = service.updateById(user);
        if (userTemp == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", user.getId()));
        }//end if
    }//modifyUser
}//class
