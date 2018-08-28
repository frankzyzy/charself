package com.yzeng.charroom.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.yzeng.charroom.entity.Message;

@Mapper
public interface MessageMapper {
	Integer insertMsg (Message message);
	
	Message getMsgById(Integer id);
}
