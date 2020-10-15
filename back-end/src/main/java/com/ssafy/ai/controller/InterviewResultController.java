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

import com.ssafy.ai.model.dto.InterviewResult;
import com.ssafy.ai.model.service.InterviewResultService;

import io.swagger.annotations.ApiOperation;

//http://localhost:9999/vue/swagger-ui.html
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/api/interviewresult")
public class InterviewResultController {

	private static final Logger logger = LoggerFactory.getLogger(InterviewResultController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private InterviewResultService irService;
	
	@ApiOperation(value = "모든 InterviewResult의 정보를 반환한다.", response = List.class)
	@GetMapping
	public ResponseEntity<List<InterviewResult>> selectAll() throws Exception {
		logger.debug("InterviewResult / selectAll - 호출");
		List<InterviewResult> all = irService.selectAll();
		return new ResponseEntity<List<InterviewResult>>(all, HttpStatus.OK);
	}

	@ApiOperation(value = "특정 InterviewResult의 정보를 반환한다.", response = InterviewResult.class)
	@GetMapping("/select/{ir_id}")
	public ResponseEntity<InterviewResult> select(@PathVariable int ir_id) throws Exception {
		logger.debug("InterviewResult / select - 호출");

		return new ResponseEntity<InterviewResult>(irService.selectById(ir_id), HttpStatus.OK);
	}
	
	@ApiOperation(value = "유저 이름으로 저장된 인터뷰 결과 값 가져오기", response = InterviewResult.class)
	@GetMapping("select/username/{username}")
	public ResponseEntity<List<InterviewResult>> getVideoUsername(@PathVariable String username) {
		
		return new ResponseEntity<List<InterviewResult>>(irService.selectByUserName(username), HttpStatus.OK);
	}

	@ApiOperation(value = "InterviewResult의 정보를 삽입한다.", response = String.class)
	@PostMapping
	public ResponseEntity<String> insert(@RequestBody InterviewResult ir) {
		logger.debug("InterviewResult / insert - 호출");
		if (irService.insert(ir)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "InterviewResult 정보를 수정한다", response = String.class)
	@PutMapping()
	public ResponseEntity<String> update(@RequestBody InterviewResult ir) {
		logger.debug("InterviewResult / update - 호출");

		if (irService.upadate(ir)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "InterviewResult 삭제한다", response = String.class)
	@DeleteMapping("{ir_id}")
	public ResponseEntity<String> delete(@PathVariable int ir_id) {
		logger.debug("InterviewResult / delete - 호출");

		if (irService.delete(ir_id)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
}
