package com.ssafy.ai.model.dao;

import java.util.List;


import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.ai.model.dto.UserAuth;

@Repository
public class UserAuthDaoImpl implements UserAuthDao {

	private static final String ns = "com.ssafy.api.UserAuth.";

	@Autowired
	SqlSessionTemplate template;

	@Override
	public List<UserAuth> selectAll() {
		return template.selectList(ns + "selectAll");
	}

	@Override
	public UserAuth select(int user_auth_pk) {
		return template.selectOne(ns + "select", user_auth_pk);
	}

	@Override
	public int insert(UserAuth ua) {
		return template.insert(ns + "insert", ua);
	}

	@Override
	public int delete(int user_auth_pk) {
		return template.delete(ns + "delete", user_auth_pk);
	}

	@Override
	public int update(UserAuth ua) {
		return template.update(ns + "update", ua);
	}

}
