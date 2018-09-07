package com.yzeng.charroom.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.yzeng.charroom.entity.UserRelation;

@Mapper
public interface FriendMapper {
	/**
	 * 建立好友关系
	 * @param userIdA
	 * @param userIdB
	 * @return
	 */
	int addFriend(UserRelation userRelation);
}
