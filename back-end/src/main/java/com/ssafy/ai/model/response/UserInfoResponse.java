package com.ssafy.ai.model.response;

import com.ssafy.ai.model.dto.User;
import io.swagger.annotations.ApiModelProperty;

public class UserInfoResponse {
	@ApiModelProperty(value = "status", position = 1)
    public boolean status;
    @ApiModelProperty(value = "data", position = 2)
    public String data;
    @ApiModelProperty(value = "object", position = 3)
    public Object object;
    @ApiModelProperty(value = "userinfo", position = 4)
    public User userinfo;
}
