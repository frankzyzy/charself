package com.yzeng.charroom.service;

import com.yzeng.charroom.entity.UserRelation;

public interface FriendService {
	
	/**
	 * 建立好友关系
	 * @param userIdA
	 * @param userIdB
	 * @return
	 */
	int addFriend(UserRelation userRelation);
}
