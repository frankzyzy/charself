package com.yzeng.charroom.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.support.RequestContext;

import com.yzeng.charroom.entity.Message;
import com.yzeng.charroom.entity.User;
import com.yzeng.charroom.entity.UserInfo;
import com.yzeng.charroom.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {

	@Autowired
    private UserService userService;

	@RequestMapping("login")
	public String login(String username,String password,HttpSession session) {
		User user = new User();
    	user.setUsername(username);
    	user.setPassword(password);
    	Map<String, Object> map = userService.login(user);
    	if(map != null && map.size() > 0) {
    		boolean flag = (boolean) map.get("flag");
    		if(flag) {
    			session.setAttribute("user", map.get("userInfo"));
    			return "index";
    		}else {
    			return "login";
    		}
    	}else {
    		return "login";
    	}
	}
	
    @RequestMapping("/get/{id}")
    @ResponseBody
    public User getUser(@PathVariable int id) {
    	//User user = userService.getUser(id);
    	User user = new User();
    	user.setId(id);
    	User user1 = userService.getNameById(user);
        return user1;
    }
    
    @RequestMapping("/delete/{id}")
    @ResponseBody
    public String delete(@PathVariable int id) {
        userService.remove(id);
        return "delete sucess";
    }

    @RequestMapping("/add")
    @ResponseBody
    public String insert(String username,String password) {
    	User user = new User();
    	user.setUsername(username);
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
    public List<UserInfo> find(){
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
