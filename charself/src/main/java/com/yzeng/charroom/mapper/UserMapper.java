package com.yzeng.charroom.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.yzeng.charroom.entity.User;

@Mapper
public interface UserMapper {
	
	User getNameById(User user);
	
	Integer insertUser(User user);
}
