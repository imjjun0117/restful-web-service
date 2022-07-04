package com.example.restfulwebservice.user;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
//공통 uri 정의
@RequestMapping("/admin")
public class AdminUserController {
    private UserDaoService service;

    public AdminUserController(UserDaoService service){
        this.service = service;
    }//UserController


    @GetMapping("/users")
    public MappingJacksonValue retrieveAllUsers(){
        List<User> users = service.findAll();

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id","name","joinDate","password");

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo",filter);

        MappingJacksonValue mapping = new MappingJacksonValue(users);
        mapping.setFilters(filters);

        return mapping;
    }//retrieveAllUsers

    //버전 관리 : 개발자나 사용자에게 올바른 사용 가이드를 알려주기 위해 사용
    //REST API 설계가 변경되거나 구조가 변경될 때 사용
    // URI versioning(Twitter) / Request Parameter versioning(Amazon) 일반 브라우저에서 실행 가능
    // Media type versioning(GitHub) / (custom)heanders versioning(Microsoft) 일반 브라우저에서 실행 불가
    // 주의사항 : URI에 너무 많은 정보는 x , 부적절한 header 값 x, Cache 관리가 필요 , 사용자에게 정확한 가이드 제공 필요



    //GET /admin/users/1 -> /admin/v1/users/1
    // uri를 이용한 버전관리
    // @GetMapping("/v1/users/{id}")

    //RequestParam을 이용한 버전관리 http://localhost/admin/users/1/?version=1
//   @GetMapping(value="/users/{id}/",params = "version=1")

    //Header를 이용한 버전관리
//   @GetMapping(value="/users/{id}",headers = "X-API-VERSION=1")

    //mime-type을 이용한 버전관리
    @GetMapping(value="/users/{id}",produces = "application/vnd.company.appv1+json")
    public MappingJacksonValue retrieveUserV1(@PathVariable int id){
        User user = service.findOne(id);
        if(user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found",id));
        }//end if
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id","name","ssn","password");

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo",filter);

        MappingJacksonValue mapping = new MappingJacksonValue(user);
        mapping.setFilters(filters);

        return mapping;
    }//retrieveUser

    // uri를 이용한 버전관리 http://localhost/admin/v2/users/1
    // @GetMapping("/v2/users/{id}")

    //RequestParam을 이용한 버전관리 http://localhost/admin/users/1?version=2
    //@GetMapping(value="/users/{id}",params = "version=2")

    //Header를 사용한 API 버전관리
//    @GetMapping(value="/users/{id}",headers = "X-API-VERSION=2")

    //mime-type을 이용한 버전관리
    @GetMapping(value="/users/{id}",produces = "application/vnd.company.appv2+json")
    public MappingJacksonValue retrieveUserV2(@PathVariable int id){

        User user = service.findOne(id);

        if(user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found",id));
        }//end if

        //User -> UserV2
        UserV2 userV2 = new UserV2();
        BeanUtils.copyProperties(user,userV2); // id, name, joinDate, password, ssn
        userV2.setGrade("VIP");

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id","name","joinDate","grade");

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2",filter);

        MappingJacksonValue mapping = new MappingJacksonValue(userV2);
        //500 Internal Server Error : Application의 호출 시 내부적 오류가 있을 때 발생하는 응답코드로, Application이나 서버 내부의 문제이다.
        // -> mapping 매개변수로 user말고 userV2를 넣어 해결
        mapping.setFilters(filters);

        return mapping;
    }//retrieveUser
}//class
