package com.yzeng.charroom.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yzeng.charroom.entity.Group;
import com.yzeng.charroom.mapper.GroupMapper;
import com.yzeng.charroom.service.GroupService;

@Service("groupService")
public class GroupServiceImpl implements GroupService{

	@Resource
	private GroupMapper groupMapper;
	
	@Override
	public int addGroup(Group group) {
		group.setCreateTime(new Date());
		return groupMapper.addGroup(group);
	}

	@Override
	public List<Group> getGroupByCreateUserId(Integer userId) {
		return groupMapper.getGroupByCreateUserId(userId);
	}
	
	@Override
	public List<Group> getGroupByUserId(Integer userId) {
		return groupMapper.getGroupByUserId(userId);
	}

	@Override
	public List<Map<String, Object>> getGroupUserByGroupId(Integer groupId) {
		return groupMapper.getGroupUserByGroupId(groupId);
	}

	@Override
	public int addUserToGroup(Integer groupId, Integer userId) {
		return groupMapper.addUserToGroup(groupId, userId);
	}

	

}
