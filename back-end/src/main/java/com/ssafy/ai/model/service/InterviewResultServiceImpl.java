package com.ssafy.ai.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.ai.model.dao.InterviewResultDao;
import com.ssafy.ai.model.dto.InterviewResult;

@Service
public class InterviewResultServiceImpl implements InterviewResultService{

	@Autowired
	private InterviewResultDao dao;
	
	@Override
	public List<InterviewResult> selectAll() {
		
		return dao.findAll();
	}

	@Override
	public InterviewResult selectById(int ir_id) {
		
		return dao.getInterviewId(ir_id);
	}

	@Override
	public boolean delete(int ir_id) {
		dao.deleteById(ir_id);
		
		return true;
	}

	@Override
	public Boolean insert(InterviewResult ir) {
		dao.save(ir);
		return true;
	}

	@Override
	public Boolean upadate(InterviewResult ir) {
			Boolean result = false;
		Optional<InterviewResult> temp = Optional.ofNullable(dao.getInterviewId(ir.getIr_id()));
		if(temp.isPresent()) {
			InterviewResult tmp = temp.get();
			tmp.setFeedback(ir.getFeedback());
			tmp.setIs_live(ir.isIs_live());
			dao.save(tmp);
			result = true;
		}
		return result;
	}

	@Override
	public List<InterviewResult> selectByUserName(String username) {
		return dao.getInterviewResultByUsername(username);
	}
}
