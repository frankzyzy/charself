package com.yzeng.charroom.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.yzeng.charroom.entity.User;
import com.yzeng.charroom.entity.UserInfo;

public interface UserService {
	public User getNameById(User user);
	
	Map<String,Object> login(User user);

	List<UserInfo> findAll();

    User getUser(Integer id);

    void update(User user);

    void insert(User user);

    void insertAll(List<User> users);

    void remove(Integer id);

    List<User> findByPage(User user,Pageable pageable);
    
   /**************以下为个人信息相关操作*****************/ 
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
}
