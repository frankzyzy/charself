package com.yzeng.charroom.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yzeng.charroom.entity.UserInfo;

@Mapper
public interface UserInfoMapper {
	/**
	 * 第一次保存用户个人信息
	 * @param userInfo userId username
	 * @return
	 */
	int firstSaveUserInfo(UserInfo userInfo);
	
	/**
	 * 根据用户ID查询个人信息
	 * @param userId
	 * @return
	 */
	UserInfo getUserInfoByUserId(Integer userId);
	
	/**
	 * 更新用户个人信息
	 * @param userInfo
	 * @return
	 */
	int updateUserInfo(UserInfo userInfo);
	
	/**
	 * 获得所有用户
	 * @return
	 */
	List<UserInfo> findAll();
}
