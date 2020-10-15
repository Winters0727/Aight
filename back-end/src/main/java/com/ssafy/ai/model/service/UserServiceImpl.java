package com.ssafy.ai.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.ai.config.BCrpytImpl;
import com.ssafy.ai.model.dao.UserDao;
import com.ssafy.ai.model.dto.User;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserDao uDao;
	
	@Autowired
	BCrpytImpl bcrpytimpl;
	
	@Override
	public List<User> selectAll() {
		return uDao.findAll();
	}

	@Override
	public User select(int user_pk) {
		return uDao.select(user_pk);
	}

	@Override
	public boolean SignUp(User u) {
		boolean result = false;
		 
		Optional<User> temp = uDao.selectByUid(u.getUid());
		
		// 중복 아이디가 없는 경우에만 회원 가입
		if(!temp.isPresent()) {
			String encrypted = bcrpytimpl.encrypt(u.getPassword());
			u.setPassword(encrypted);
			
			uDao.save(u);
			result = true;
		}
		 
		 return result;
	}

	@Override
	public boolean delete(int user_pk) {
		
		boolean result = false;
		
		Optional<User> tUser = uDao.findById(user_pk);
		if(tUser.isPresent()) {
			uDao.delete(tUser.get());
			result = true;
		}
		return result;
	}

	@Override
	public boolean update(User u) {
		boolean result = false;
		
		Optional<User> tUser = uDao.findById(u.getUser_pk());
		if(tUser.isPresent()) {
			uDao.save(tUser.get());
			result = true;
		}
		return result;
	}

	@Override
	public User selectByUid(String uid) {
		
		Optional<User> temp = uDao.selectByUid(uid); 
		
		User result = temp.get();
		
		return result;
	}
	
	@Override
	public User selectByUsername(String username) {
		return uDao.selectByUsername(username);
	}
	
	@Override
	public Object login(User u) {
		Object res = null;
		Optional<User> check = uDao.selectByUid(u.getUid());
//		System.out.println(check.toString());
		if(check.isPresent()) {
			User temp = check.get();
			if(bcrpytimpl.isMatch(u.getPassword(), temp.getPassword())){
				res = temp;
			}else {
				res ="password";
			}
		}else {
			res ="email";
		}
		
		return res;
	}
	
	@Override
    public boolean checkEmail(String uid) {
		
		boolean result = false;
		Optional<User>oUser = uDao.selectByUid(uid);
		
		if(oUser.isPresent()) {
			result = true;
		}
        return result;
    }

}
