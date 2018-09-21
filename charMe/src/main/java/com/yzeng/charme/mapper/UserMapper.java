package com.yzeng.charme.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.yzeng.charme.entity.User;

@Mapper
public interface UserMapper {
	
	User getNameById(User user);
	
	Integer insertUser(User user);
}
