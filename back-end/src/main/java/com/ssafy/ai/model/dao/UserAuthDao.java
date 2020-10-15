package com.ssafy.ai.model.dao;

import java.util.List;

import com.ssafy.ai.model.dto.UserAuth;

public interface UserAuthDao {

	public List<UserAuth> selectAll();
	
	public UserAuth select(int user_auth_pk);
	
	public int insert(UserAuth ua);
	
	public int delete(int user_auth_pk);
	
	public int update(UserAuth ua);
}
