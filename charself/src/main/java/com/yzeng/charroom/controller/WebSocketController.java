package com.yzeng.charroom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzeng.charroom.server.SocketServer;

@Controller
public class WebSocketController {
	@Autowired
    private SocketServer socketServer;

    @RequestMapping("/{page}")
    public String showPage(@PathVariable("page")String page) {
        return page;
    }
    
    @RequestMapping(value="toLogin")
    public String login() {
    	
    	return "index";
    }
    
}
