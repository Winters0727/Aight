package com.ssafy.ai.model.service;

import java.util.List;

import com.ssafy.ai.model.dto.User;

public interface UserService {

	public List<User> selectAll();
	
	public Object login(User u);

	public User select(int user_pk);

	public boolean SignUp(User u);

	public boolean delete(int user_pk);

	public boolean update(User u);
	
	public User selectByUid(String uid);
	
	public User selectByUsername(String username);
	
	 public boolean checkEmail(String uid);
}
