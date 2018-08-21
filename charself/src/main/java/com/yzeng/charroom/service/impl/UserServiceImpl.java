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
import com.yzeng.charroom.entity.User;
import com.yzeng.charroom.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService{
	
	@Autowired
    private UserDao userDao;

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
    	List<User> findAll = userDao.findAll();
    	if(findAll != null && findAll.size() > 0) {
    		user.setId(findAll.get(findAll.size()-1).getId()+1);
    	}else {
    		user.setId(1);
    	}
    	user.setPassword(md5Pwd);
    	user.setLoginTime(formatter.format(new Date()));
        userDao.insert(user);
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
		User u = userDao.getUserByName(user.getUsername());
		if(u != null) {
			if(u.getPassword().equals(md5Pwd)) {
				map.put("flag", true);
				map.put("user", u);
			}else {
				map.put("flag", false);
			}
		}
		return map;
	}

}
