package com.example.restfulwebservice.user;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
@Service
public class UserDaoService {
    private static List<User> users = new ArrayList<>();
    private static int usersCount = 3;


    static{
        users.add(new User(1,"Joony",new Date(),"pass1","701010-1111111"));
        users.add(new User(2,"Alice",new Date(),"pass2","801010-1111111"));
        users.add(new User(3,"Elena",new Date(),"pass3","901010-1111111"));
    }//static

    public List<User> findAll(){
        return users;
    }//findAll

    public User save(User user){
        if(user.getId() == null){
            user.setId(++usersCount);
        }//end if
        users.add(user);
        return user;
    }//save

    public User findOne(int id){
        for(User user : users){
            if(user.getId() == id){
                return user;
            }//end if
        }//end for
        return null;
    }//findOne

    public User deleteById(int id){
        Iterator<User> iterator = users.iterator();
        while(iterator.hasNext()){
            User user = iterator.next();
            if(user.getId() == id){
                iterator.remove();
                return user;
            }//end if
        }//end while
        return null;
    }//deleteById

    public User updateById(User user){
        User userTemp = findOne(user.getId());
        if(userTemp != null){
            userTemp.setName(user.getName());
            userTemp.setJoinDate(new Date());
        }//end if
        return userTemp;
    }//updateById

}//class

