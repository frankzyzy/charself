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

import com.yzeng.charroom.entity.Message;
import com.yzeng.charroom.entity.User;
import com.yzeng.charroom.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {

	@Autowired
    private UserService userService;

    @RequestMapping("/get/{id}")
    public User getUser(@PathVariable int id) {
        return userService.getUser(id);
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        userService.remove(id);
        return "delete sucess";
    }

    @RequestMapping("/add")
    public String insert() {
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    	Message msg = new Message(1, "x嘻嘻嘻", 1, 2, "tom", "admin", formatter.format(new Date()));
    	List<Message> msgList = new ArrayList<Message>();
    	msgList.add(msg);
        User user =new User(1, "tom", "123", formatter.format(new Date()), msgList);
        userService.insert(user);
        return "sucess";
    }

    @RequestMapping("/insert")
    public String insertAll() {
        List<User> list = new ArrayList<>();
        for (int i = 10; i < 15; i++) {
            //list.add(new User(i, "" + i, i));
        }
        userService.insertAll(list);
        return "sucess";
    }

    @RequestMapping("/find/all")
    public List<User> find(){
        return userService.findAll();
    }

    @RequestMapping("/find/{start}")
    public List<User> findByPage(@PathVariable int start,User user){
        Pageable pageable=new PageRequest(start, 2);
        return userService.findByPage(user, pageable);
    } 

    @RequestMapping("/update/{id}")
    public String update(@PathVariable int id){
        User user =new User();
        userService.update(user);
        return "sucess";
    }
}
