package com.yzeng.charroom.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.yzeng.charroom.dao.UserDao;
import com.yzeng.charroom.dao.UserInfoDao;
import com.yzeng.charroom.entity.User;
import com.yzeng.charroom.entity.UserInfo;
import com.yzeng.charroom.mapper.UserInfoMapper;
import com.yzeng.charroom.mapper.UserMapper;
import com.yzeng.charroom.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService{
	
	@Autowired
    private UserDao userDao;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserInfoMapper userInfoMapper;
	
	@Autowired
	private UserInfoDao userInfoDao;

    @Override
    public List<UserInfo> findAll() {
        //return userDao.findAll();
        return userInfoMapper.findAll();
    }

    @Override
    public User getUser(Integer id) {
        return userDao.getUserById(id);
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    public void insert(User user) {
    	String md5Pwd = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
    	//插入用户账户信息
    	user.setPassword(md5Pwd);
    	user.setRegistertime(new Date());
    	user.setLastTime(new Date());
    	Integer num = userMapper.insertUser(user);
    	
    	//插入用户个人信息
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getId());
        userInfo.setUsername(user.getUsername());
        userInfoMapper.firstSaveUserInfo(userInfo);
    }

    @Override
    public void insertAll(List<User> users) {
        userDao.insertAll(users);
    }

    @Override
    public void remove(Integer id) {
        userDao.remove(id);
    }

    @Override
    public List<User> findByPage(User user, Pageable pageable) {
        return userDao.findByPage(user, pageable);
    }

	@Override
	public Map<String,Object> login(User user) {
		Map<String,Object> map = new HashMap<String,Object>();
		String md5Pwd = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		User u = userDao.login(user.getUsername(), md5Pwd);
		if(u != null) {
			map.put("flag", true);
			map.put("userInfo", userInfoMapper.getUserInfoByUserId(u.getId()));
		}else {
			map.put("flag", false);
		}
		return map;
	}

	@Override
	public User getNameById(User user) {
		return userMapper.getNameById(user);
	}
	
	
	/**************以下为个人信息相关操作*****************/ 	
	@Override
	public int firstSaveUserInfo(UserInfo userInfo) {
		
		return userInfoMapper.firstSaveUserInfo(userInfo);
	}

	@Override
	public UserInfo getUserInfoByUserId(Integer userId) {
		return userInfoMapper.getUserInfoByUserId(userId);
	}

	@Override
	public int updateUserInfo(UserInfo userInfo) {
		return userInfoMapper.updateUserInfo(userInfo);
	}

	@Override
	public List<UserInfo> findByName(UserInfo userinfo) {
		return userInfoMapper.findByName(userinfo);
	}

}
