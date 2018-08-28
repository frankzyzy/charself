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
import com.yzeng.charroom.mapper.UserMapper;
import com.yzeng.charroom.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService{
	
	@Autowired
    private UserDao userDao;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserInfoDao userInfoDao;

    @Override
    public List<User> findAll() {
        return userDao.findAll();
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
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String md5Pwd = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
    	
    	user.setPassword(md5Pwd);
    	user.setRegistertime(new Date());
    	user.setLastTime(new Date());
    	Integer num = userMapper.insertUser(user);
    	
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getId());
        userInfo.setUsername(user.getUsername());
        userInfoDao.insert(userInfo);
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
			map.put("userInfo", userInfoDao.getUserInfoByUserId(u.getId()));
		}else {
			map.put("flag", false);
		}
		return map;
	}

	@Override
	public User getNameById(User user) {
		return userMapper.getNameById(user);
	}

}
