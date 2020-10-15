package com.ssafy.ai.model.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ssafy.ai.model.dto.User;

public interface UserDao extends JpaRepository<User, Integer> {

	@Query(value ="SELECT * FROM user WHERE user_pk=:user_pk", nativeQuery = true)
	public User select(int user_pk);
	
	@Query(value ="SELECT * FROM user WHERE uid=:uid", nativeQuery = true)
	public Optional<User> selectByUid(String uid);
	
	@Query(value ="SELECT * FROM user WHERE username=:username", nativeQuery = true)
	public User selectByUsername(String username);
	
}
