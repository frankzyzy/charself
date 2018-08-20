package com.yzeng.charroom.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.yzeng.charroom.entity.User;

public interface UserDao {
	
	List<User> findAll();

	User getUserById(Integer id);
	
	User getUserByName(String username);

    void update(User user);

    void insert(User user);

    void insertAll(List<User> users);

    void remove(Integer id);

    List<User> findByPage(User user, Pageable pageable);
}
