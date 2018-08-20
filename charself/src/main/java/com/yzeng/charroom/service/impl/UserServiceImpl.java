package com.yzeng.charroom.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
        return userDao.getUser(id);
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    public void insert(User user) {
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    	String md5Pwd = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
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

}
