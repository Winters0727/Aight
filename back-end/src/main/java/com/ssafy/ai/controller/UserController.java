package com.ssafy.ai.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ai.model.dto.User;
import com.ssafy.ai.model.response.BasicResponse;
import com.ssafy.ai.model.response.UserInfoResponse;
import com.ssafy.ai.model.service.JwtService;
import com.ssafy.ai.model.service.UserService;

import io.swagger.annotations.ApiOperation;

//http://localhost:9999/vue/swagger-ui.html
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/api/user")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private UserService uService;
	
	@Autowired
	private JwtService jwtService;

	@ApiOperation(value = "모든 User의 정보를 반환한다.", response = List.class)
	@GetMapping
	public ResponseEntity<List<User>> selectAll() throws Exception {
		logger.debug("User / selectAll - 호출");
		List<User> all = uService.selectAll();
		return new ResponseEntity<List<User>>(all, HttpStatus.OK);
	}

	@ApiOperation(value = "특정 User의 정보를 반환한다.", response = User.class)
	@GetMapping("/select/{user_pk}")
	public ResponseEntity<User> select(@PathVariable int user_pk) throws Exception {
		logger.debug("User / select - 호출");

		return new ResponseEntity<User>(uService.select(user_pk), HttpStatus.OK);
	}
	
	@ApiOperation(value = "로그인", response = String.class)
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody User u, HttpServletResponse res) throws Exception {
		logger.debug("User / login - 호출");
		String userid = u.getUid();
		String password = u.getPassword();
		
		ResponseEntity response = null;
		Object login_res = uService.login(u);
		if(!login_res.equals("password") && !login_res.equals("email")) {
			final UserInfoResponse result = new UserInfoResponse();
			String token = jwtService.create((User)login_res);
			String encoded = URLEncoder.encode(((User)login_res).getUsername(), "UTF-8");
			System.out.println(encoded);
			res.setHeader("jwt-auth-token", token);
			res.setHeader("username", encoded);
			res.setHeader("user_pk", Integer.toString(((User)login_res).getUser_pk()));
			
			res.setHeader("userpk", Integer.toString(((User)login_res).getUser_pk()));
            result.status = true;
            result.data = "success";
			result.userinfo = (User)login_res;
			response = new ResponseEntity<>(result, HttpStatus.OK);
		}else {
			final BasicResponse result = new BasicResponse();
            result.status = false;
            result.data = "fail";
            
            response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}
		return response;
	}


	@ApiOperation(value = "회원 가입", response = String.class)
	@PostMapping("/signup")
	public ResponseEntity<String> insert(@RequestBody User u) {
		logger.debug("User / insert - 호출");
		ResponseEntity response = null;
		final BasicResponse result = new BasicResponse();
		System.out.println(u.getNickname());
		if (uService.SignUp(u)) {
			result.status = true;
			result.data = "회원 가입 성공";
			response = new ResponseEntity<>(result, HttpStatus.OK);
		}else {
			result.status = false;
			result.data = "회원 가입 실패";
			response = new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
		}
		return response;
	}

	@ApiOperation(value = "회원 정보 수정", response = String.class)
	@PutMapping()
	public ResponseEntity<String> update(@RequestBody User u) {
		logger.debug("User / update - 호출");

		if (uService.update(u)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	

	@ApiOperation(value = "User 삭제한다", response = String.class)
	@DeleteMapping("{user_pk}")
	public ResponseEntity<String> delete(@PathVariable int user_pk) {
		logger.debug("User / delete - 호출");

		if (uService.delete(user_pk)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "jwt 재발 급")
	@GetMapping("/user/extendJWT")
	public Object extendJWT(HttpServletRequest req, HttpServletResponse res)throws UnsupportedEncodingException{
		ResponseEntity<BasicResponse> response = null;
		final BasicResponse result = new BasicResponse();
		User user = uService.selectByUsername(req.getHeader("username"));
		String token = jwtService.create(user);
		String encoded = URLEncoder.encode(user.getNickname(), "UTF-8");
		res.setHeader("jwt-auth-token", token);
		res.setHeader("nickname", encoded);
		res.setHeader("userpk", Integer.toString(user.getUser_pk()));
		result.status = true;
		result.data = "토큰이 재발급 되었습니다.";
		
		response = new ResponseEntity<>(result, HttpStatus.CREATED);
		return response;
	}
	
	@ApiOperation(value = "User의 이메일을 반환한다.", response = List.class)
    @GetMapping("/emailCheck")
    public ResponseEntity<Boolean> checkEmail(@RequestParam String email) throws Exception {
       logger.debug("User / checkEmail - 호출");
       boolean is_existed = false;
       // 이메일이 있으면 true 리턴
       boolean result = uService.checkEmail(email);
       if (result) {
          is_existed = true;
       }
       return new ResponseEntity<Boolean>(is_existed, HttpStatus.OK);
    }
}
