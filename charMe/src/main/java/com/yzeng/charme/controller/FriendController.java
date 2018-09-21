package com.yzeng.charme.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzeng.charme.entity.UserInfo;
import com.yzeng.charme.entity.UserRelation;
import com.yzeng.charme.service.FriendService;
import com.yzeng.charme.service.UserService;

@Controller
@RequestMapping("friend")
public class FriendController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FriendService friendService;
	
	@RequestMapping("find")
	@ResponseBody
	public List<UserInfo> find(String findStr){
		UserInfo userInfo = new UserInfo();
		userInfo.setUsername(findStr);
		userInfo.setNickname(findStr);
		
		return userService.findByName(userInfo);
	}
	
	/**
	 * 添加好友
	 * @param userIdA
	 * @param userIdB
	 * @return
	 */
	@RequestMapping("add")
	@ResponseBody
	public String addFriend(Integer userIdA,Integer userIdB) {
		try {
			UserRelation relation = new UserRelation();
			relation.setUserIdA(userIdA);
			relation.setUserIdB(userIdB);
			int i = friendService.addFriend(relation);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
}
