package com.bpns.mobile.mini.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bpns.mobile.mini.model.UserEntity;
import com.bpns.mobile.mini.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository urp;
	
	public UserEntity findById(String KAKAO_USERID) { // 로그인할 때 아이디 일치하는지 확인
//		KAKAO_USERID = KAKAO_USERID.replaceAll("'", "");
//		KAKAO_USERID = "'" + KAKAO_USERID + "'";
		UserEntity ue = urp.findById(KAKAO_USERID);
		return ue;
	}
	
	public Boolean createUserEntity(String KAKAO_USERID, String KAKAO_EMAIL, String KAKAO_PRF_URL, 
			String KAKAO_ACCESS_TOKEN, String DEVICE_ID, String NICK_NAME, 
			String FCM_TOKEN, String FCM_USE_YN) {
		UserEntity e = new UserEntity();
		
		e.setKakao_userid(KAKAO_USERID);
		e.setKAKAO_EMAIL(KAKAO_EMAIL);
		e.setKAKAO_PRF_URL(KAKAO_PRF_URL);
		e.setKAKAO_ACCESS_TOKEN(KAKAO_ACCESS_TOKEN);
		e.setDEVICE_ID(DEVICE_ID);
		e.setNICK_NAME(NICK_NAME);
		e.setFCM_TOKEN(FCM_TOKEN);
		e.setFCM_USE_YN(FCM_USE_YN);
		
		e = urp.save(e);
		
		if(e == null) {
			return false;
		} else {
			return true;
		}
		
	}
	
	public UserEntity getMysetById(String KAKAO_USERID) { // 닉네임과 푸시알림
		UserEntity e = (UserEntity) urp.findById(KAKAO_USERID);
		return e;
	}
	
	public Boolean updateUserNick(String Nickname, String Id) {
		Boolean b = urp.updateUserNick(Nickname, Id);
		return b;
	}
	
	public Boolean updateUserFcmUse(String YN, String Id) {
		Boolean b = urp.updateFcmUse(YN, Id);
		return b;
	}
}
