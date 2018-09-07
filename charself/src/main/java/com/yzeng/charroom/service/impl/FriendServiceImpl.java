package com.yzeng.charroom.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yzeng.charroom.entity.UserRelation;
import com.yzeng.charroom.mapper.FriendMapper;
import com.yzeng.charroom.service.FriendService;

@Service("friendService")
public class FriendServiceImpl implements FriendService{

	@Resource
	private FriendMapper friendMapper; 
	
	@Override
	public int addFriend(UserRelation userRelation) {
		userRelation.setRelationTime(new Date());
		return friendMapper.addFriend(userRelation);
	}
	
}
