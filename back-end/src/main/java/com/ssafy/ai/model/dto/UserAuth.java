package com.ssafy.ai.model.dto;

import java.util.Date;

public class UserAuth {
	private int user_auth_pk;
	private String user_auti_id;
	private String token;
	private Date create_date;
	private Date expire_date;
	public int getUser_auth_pk() {
		return user_auth_pk;
	}
	public void setUser_auth_pk(int user_auth_pk) {
		this.user_auth_pk = user_auth_pk;
	}
	public String getUser_auti_id() {
		return user_auti_id;
	}
	public void setUser_auti_id(String user_auti_id) {
		this.user_auti_id = user_auti_id;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public Date getExpire_date() {
		return expire_date;
	}
	public void setExpire_date(Date expire_date) {
		this.expire_date = expire_date;
	}
	
	public UserAuth(int user_auth_pk, String user_auti_id, String token, Date create_date, Date expire_date) {
		super();
		this.user_auth_pk = user_auth_pk;
		this.user_auti_id = user_auti_id;
		this.token = token;
		this.create_date = create_date;
		this.expire_date = expire_date;
	}
	
}
