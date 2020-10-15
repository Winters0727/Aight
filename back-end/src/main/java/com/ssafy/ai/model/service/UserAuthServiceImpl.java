package com.ssafy.ai.model.service;

import java.util.List;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.ai.model.dao.UserAuthDao;
import com.ssafy.ai.model.dto.UserAuth;

@Service
public class UserAuthServiceImpl implements UserAuthService{

	@Autowired
	UserAuthDao uaDao;
	
	@Override
	public List<UserAuth> selectAll() {
		return uaDao.selectAll();
	}

	@Override
	public UserAuth select(int user_auth_pk) {
		return uaDao.select(user_auth_pk);
	}

	@Override
	public int insert(UserAuth ua) {
		return uaDao.insert(ua);
	}

	@Override
	public int delete(int user_auth_pk) {
		return uaDao.delete(user_auth_pk);
	}

	@Override
	public int update(UserAuth ua) {
		return uaDao.update(ua);
	}

}
