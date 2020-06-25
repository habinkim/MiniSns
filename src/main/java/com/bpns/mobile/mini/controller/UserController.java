package com.bpns.mobile.mini.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bpns.mobile.mini.model.UserEntity;
import com.bpns.mobile.mini.service.UserService;

@RestController
@Configuration
@RequestMapping("/user")
//@CrossOrigin(origins = {"http://localhost:3000","http://192.168.0.26:3000"})
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8000" }) 
public class UserController implements WebMvcConfigurer {
	@Autowired
	private UserService u_service;
	
	
	@GetMapping("/check") // 정상작동
	public UserEntity findById(@RequestParam String KAKAO_USERID) { // 로그인할 때 아이디 일치하는지 확인
		UserEntity ue = new UserEntity();
		
		try { 
			ue = u_service.findById(KAKAO_USERID);
			return ue;
		} catch(NullPointerException e) {
			return null;
		}
	}
	
	//1. 회원 여부 확인 (토큰, 카카오 아이디){
//	// 등록되지 않은 회원 
//	리턴 null
//
//	// 등록된 회원
//	리턴 {
//		유저 아이디, 닉네임, 푸쉬 알림 여부
//	}
//}
	
	@PostMapping(value = "/reg", consumes=MediaType.APPLICATION_JSON_VALUE) // 정상작동
	public boolean createUserEntity(@RequestBody Map<String, Object> params) {
		String KAKAO_USERID = params.get("KAKAO_USERID").toString();
		String KAKAO_EMAIL = params.get("KAKAO_EMAIL").toString();
		String KAKAO_PRF_URL = params.get("KAKAO_PRF_URL").toString();
		String KAKAO_ACCESS_TOKEN = params.get("KAKAO_ACCESS_TOKEN").toString();
		String DEVICE_ID = params.get("DEVICE_ID").toString();
		String NICK_NAME = params.get("NICK_NAME").toString();
		String FCM_TOKEN = params.get("FCM_TOKEN").toString();
		String FCM_USE_YN = params.get("FCM_USE_YN").toString();
		
		boolean b = u_service.createUserEntity(KAKAO_USERID, KAKAO_EMAIL, KAKAO_PRF_URL, 
				KAKAO_ACCESS_TOKEN, DEVICE_ID, NICK_NAME, 
				FCM_TOKEN, FCM_USE_YN);
		
//		boolean b = u_service.createUserEntity(KAKAO_ACCESS_TOKEN, KAKAO_USERID, NICK_NAME);
		
		return b;
	}
	
	//2. 회원 가입 (토큰, 카카오 아이디, 닉네임, 이미지){
//	// 저장됨
//	리턴true
//
//	// 저장 실패
//	리턴 false
//}
	
	
	@GetMapping("/mypage") // 정상작동
	public UserEntity getMysetById(@RequestParam String KAKAO_USERID) { // 닉네임과 푸시알림
//		KAKAO_USERID = "Who";
		UserEntity e = (UserEntity) u_service.findById(KAKAO_USERID);
		return e;
	}
	
	//3. 내 회원 정보 받기() {
//	리턴 닉네임, 유저 아이디, 푸쉬 알림 여부
//}
//
	
	@PostMapping(value = "/mypage/edit/nick", consumes=MediaType.APPLICATION_JSON_VALUE) // 정상작동
	public Boolean updateUserNick(@RequestBody Map<String, Object> params) {
		String Nickname = params.get("Nickname").toString();  
		String Id = params.get("Id").toString();
		
		Boolean b = u_service.updateUserNick(Nickname, Id);
		return b;
	}
	
	//4. 내 닉네임 변경 (닉네임) {
//	// 저장됨
//	리턴true
//
//	// 저장 실패
//	리턴 false
//}
	
	@PostMapping(value = "/mypage/edit/fcm", consumes=MediaType.APPLICATION_JSON_VALUE) // 정상작동
	public Boolean updateUserFcmUse(@RequestBody Map<String, Object> params) {
		String YN = params.get("YN").toString(); 
		String Id = params.get("Id").toString();
		
		Boolean b = u_service.updateUserFcmUse(YN, Id);
		return b;
	}
	
	//5. 내 푸쉬 알림 여부 변경 (푸쉬알림여부) {
//	// 저장됨
//	리턴true
//
//	// 저장 실패
//	리턴 false
//}
}
