package com.yzeng.charroom.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzeng.charroom.entity.Group;
import com.yzeng.charroom.service.GroupService;
import com.yzeng.charroom.utils.WebResult;

@Controller
@RequestMapping("group")
public class GroupController {
	
	@Resource
	private GroupService groupService;
	
	@RequestMapping("add")
	@ResponseBody
	public WebResult addGroup(Integer userId, String groupName, String groupDescribes) {
		WebResult wr = new WebResult();
		Group group = new Group();
		try {
			group.setCreateUserId(userId);
			group.setGroupName(groupName);
			group.setGroupDescribes(groupDescribes);
			int i = groupService.addGroup(group);
			wr.invokeSuccess(i+"");
		} catch (Exception e) {
			wr.invokeFail("创建群失败");
			e.printStackTrace();
		}
		return wr;
	}
	
	@RequestMapping("getAll")
	@ResponseBody
	public WebResult getAllByUserId(Integer userId) {
		WebResult wr = new WebResult();
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			List<Group> slefGroup = groupService.getGroupByCreateUserId(userId);
			List<Group> joinGroup = groupService.getGroupByUserId(userId);
			map.put("slefGroup", slefGroup);
			map.put("joinGroup", joinGroup);
			wr.setData(map);
			wr.setCode("1");
		} catch (Exception e) {
			e.printStackTrace();
			wr.invokeFail("加载群列表失败！");
		}
		return wr;
	}
	
	@RequestMapping("getGroupUser")
	@ResponseBody
	public WebResult getGroupUserByGroupId(Integer groupId) {
		WebResult wr = new WebResult();
		try {
			List<Map<String,Object>> list = groupService.getGroupUserByGroupId(groupId);
			wr.setData(list);
			wr.setCode("1");
		} catch (Exception e) {
			e.printStackTrace();
			wr.invokeFail("加载群列表失败！");
		}
		return wr;
	}
	
	@RequestMapping("addUserToGroup")
	@ResponseBody
	public WebResult addUserToGroup(Integer groupId,Integer userId) {
		WebResult wr = new WebResult();
		try {
			int i = groupService.addUserToGroup(groupId, userId);
			wr.setData(i);
			wr.setCode("1");
		} catch (Exception e) {
			e.printStackTrace();
			wr.invokeFail("加群失败！");
		}
		return wr;
	}
}
