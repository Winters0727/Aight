package com.ssafy.ai.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ssafy.ai.model.dto.InterviewResult;

public interface InterviewResultDao extends JpaRepository<InterviewResult, Integer> {

	@Query(value="SELECT * FROM interview_result WHERE ir_id=:ir_id", nativeQuery = true)
	InterviewResult getInterviewId(int ir_id);
	
	List<InterviewResult> getInterviewResultByUsername(String username);
}
