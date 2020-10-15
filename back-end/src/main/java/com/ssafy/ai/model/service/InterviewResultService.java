package com.ssafy.ai.model.service;

import java.util.List;

import com.ssafy.ai.model.dto.InterviewResult;

public interface InterviewResultService {

	public List<InterviewResult>selectAll();
	
	public InterviewResult selectById(int ir_id);
	
	public boolean delete(int ir_id);
	
	public Boolean insert(InterviewResult ir);
	
	public Boolean upadate(InterviewResult ir);
	
	public List<InterviewResult> selectByUserName(String username);
}
