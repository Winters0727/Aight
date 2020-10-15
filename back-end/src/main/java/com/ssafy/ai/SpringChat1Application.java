package com.ssafy.ai;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ssafy.ai.interceptor.JwtInterceptorGetMethod;
import com.ssafy.ai.interceptor.JwtInterceptorPostMethod;
import com.ssafy.ai.interceptor.JwtInterceptorPutDeleteMethod;

@SpringBootApplication
public class SpringChat1Application implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(SpringChat1Application.class, args);
	}
	
	@Autowired
	private JwtInterceptorGetMethod jwtInterceptorGet;
	
	@Autowired
	private JwtInterceptorPostMethod jwtInterceptorPost;
	
	@Autowired
	private JwtInterceptorPutDeleteMethod jwtInterceptorPD;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		List<String> getMethodAdd = Arrays.asList("/api/user/extendJWT","/api/user","/api/interviewresult/*","/api/videos/*");
		List<String> getMethodExclude = Arrays.asList("");
		registry.addInterceptor(jwtInterceptorGet).addPathPatterns(getMethodAdd).excludePathPatterns(getMethodExclude);
		
		List<String> postMethodAdd = Arrays.asList("/api/interviewresult/*","/api/videos/*");
		List<String> postMethodExclude = Arrays.asList("");
		registry.addInterceptor(jwtInterceptorPost).addPathPatterns(postMethodAdd).excludePathPatterns(postMethodExclude);
		
		List<String> putDeleteMethodAdd = Arrays.asList("/api/user/*", "/api/interviewresult/*","/api/videos/*");
		List<String> putDeleteMethodExclude = Arrays.asList("");
		registry.addInterceptor(jwtInterceptorPD).addPathPatterns(putDeleteMethodAdd).excludePathPatterns(putDeleteMethodExclude);
	}
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*").allowedMethods("*")
		.allowedHeaders("*").exposedHeaders("jwt-auth-token", "username", "user_pk");
		
		
	}

}
