package com.ssafy.ai.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ai.model.dto.UserAuth;
import com.ssafy.ai.model.service.UserAuthService;

import io.swagger.annotations.ApiOperation;

//http://localhost:9999/vue/swagger-ui.html
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/api/userauth")
public class UserAuthController {

	private static final Logger logger = LoggerFactory.getLogger(UserAuthController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private UserAuthService uaService;

	@ApiOperation(value = "모든 UserAuth의 정보를 반환한다.", response = List.class)
	@GetMapping
	public ResponseEntity<List<UserAuth>> selectAll() throws Exception {
		logger.debug("UserAuth / selectAll - 호출");
		List<UserAuth> all = uaService.selectAll();
		return new ResponseEntity<List<UserAuth>>(all, HttpStatus.OK);
	}

	@ApiOperation(value = "특정 UserAuth의 정보를 반환한다.", response = UserAuth.class)
	@GetMapping("/select/{user_auth_pk}")
	public ResponseEntity<UserAuth> select(@PathVariable int user_auth_pk) throws Exception {
		logger.debug("UserAuth / select - 호출");

		return new ResponseEntity<UserAuth>(uaService.select(user_auth_pk), HttpStatus.OK);
	}

	@ApiOperation(value = "UserAuth의 정보를 삽입한다.", response = String.class)
	@PostMapping
	public ResponseEntity<String> insert(@RequestBody UserAuth ua) {
		logger.debug("UserAuth / insert - 호출");
		if (uaService.insert(ua) != 0) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "UserAuth 정보를 수정한다", response = String.class)
	@PutMapping()
	public ResponseEntity<String> update(@RequestBody UserAuth ua) {
		logger.debug("UserAuth / update - 호출");

		if (uaService.update(ua) != 0) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "UserAuth 삭제한다", response = String.class)
	@DeleteMapping("{user_auth_pk}")
	public ResponseEntity<String> delete(@PathVariable int user_auth_pk) {
		logger.debug("UserAuth / delete - 호출");

		if (uaService.delete(user_auth_pk) != 0) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
}
