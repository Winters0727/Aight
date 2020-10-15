package com.ssafy.ai.model.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="InterviewResult")
public class InterviewResult {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int ir_id;
	
	@Column
	private int user_pk;
	
	@Column
	private String username;
	@Column
	private int image_score;
	
	@Column
	private String image_score_list;
	
	@Column
	private int voice_score;
	
	@Column
	private String silent_interval;
	
	@Column
	private String graph_image_url;
	
	@Column
	private String feedback;
	
	@Column
	private int video_length;
	
	@Column
	private boolean is_live;
	
	@Column
	private String filename;
	
	@Column
	private Date test_date;
	public int getIr_id() {
		return ir_id;
	}
	public void setIr_id(int ir_id) {
		this.ir_id = ir_id;
	}
	public int getUser_pk() {
		return user_pk;
	}
	public void setUser_pk(int user_pk) {
		this.user_pk = user_pk;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getImage_score() {
		return image_score;
	}
	public void setImage_score(int image_score) {
		this.image_score = image_score;
	}
	public String getImage_score_list() {
		return image_score_list;
	}
	public void setImage_score_list(String image_score_list) {
		this.image_score_list = image_score_list;
	}
	public int getVoice_score() {
		return voice_score;
	}
	public void setVoice_score(int voice_score) {
		this.voice_score = voice_score;
	}
	public String getSilent_interval() {
		return silent_interval;
	}
	public void setSilent_interval(String silent_interval) {
		this.silent_interval = silent_interval;
	}
	public String getGraph_image_url() {
		return graph_image_url;
	}
	public void setGraph_image_url(String graph_image_url) {
		this.graph_image_url = graph_image_url;
	}
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	public int getVideo_length() {
		return video_length;
	}
	public void setVideo_length(int video_length) {
		this.video_length = video_length;
	}
	public boolean isIs_live() {
		return is_live;
	}
	public void setIs_live(boolean is_live) {
		this.is_live = is_live;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public Date getTest_date() {
		return test_date;
	}
	public void setTest_date(Date test_date) {
		this.test_date = test_date;
	}
	
	public InterviewResult(int ir_id, int user_pk, String username, int image_score, String image_score_list,
			int voice_score, String silent_interval, String graph_image_url, String feedback, int video_length,
			boolean is_live, String filename, Date test_date) {
		this.ir_id = ir_id;
		this.user_pk = user_pk;
		this.username = username;
		this.image_score = image_score;
		this.image_score_list = image_score_list;
		this.voice_score = voice_score;
		this.silent_interval = silent_interval;
		this.graph_image_url = graph_image_url;
		this.feedback = feedback;
		this.video_length = video_length;
		this.is_live = is_live;
		this.filename = filename;
		this.test_date = test_date;
	}

	
	public InterviewResult(){}
}
