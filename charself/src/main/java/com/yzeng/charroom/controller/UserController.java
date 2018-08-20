package com.yzeng.charroom.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzeng.charroom.entity.Message;
import com.yzeng.charroom.entity.User;
import com.yzeng.charroom.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {

	@Autowired
    private UserService userService;

    @RequestMapping("/get/{id}")
    @ResponseBody
    public User getUser(@PathVariable int id) {
    	User user = userService.getUser(id);
        return user;
    }
    
    @RequestMapping("/delete/{id}")
    @ResponseBody
    public String delete(@PathVariable int id) {
        userService.remove(id);
        return "delete sucess";
    }

    @RequestMapping("/add")
    @ResponseBody
    public String insert(String name,String password) {
    	User user = new User();
    	user.setUsername(name);
    	user.setPassword(password);
        userService.insert(user);
        return "sucess";
    }

    @RequestMapping("/insert")
    @ResponseBody
    public String insertAll() {
        List<User> list = new ArrayList<>();
        for (int i = 10; i < 15; i++) {
            //list.add(new User(i, "" + i, i));
        }
        userService.insertAll(list);
        return "sucess";
    }

    @RequestMapping("/find/all")
    @ResponseBody
    public List<User> find(){
        return userService.findAll();
    }

    @RequestMapping("/find/{start}")
    @ResponseBody
    public List<User> findByPage(@PathVariable int start,User user){
        Pageable pageable=new PageRequest(start, 2);
        return userService.findByPage(user, pageable);
    } 

    @RequestMapping("/update/{id}")
    @ResponseBody
    public String update(@PathVariable int id){
        User user =new User();
        userService.update(user);
        return "sucess";
    }
}
