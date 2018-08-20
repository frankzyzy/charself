package com.yzeng.charroom.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.yzeng.charroom.entity.User;

public interface UserService {
	Map<String,Object> login(User user);

	List<User> findAll();

    User getUser(Integer id);

    void update(User user);

    void insert(User user);

    void insertAll(List<User> users);

    void remove(Integer id);

    List<User> findByPage(User user,Pageable pageable);
}
