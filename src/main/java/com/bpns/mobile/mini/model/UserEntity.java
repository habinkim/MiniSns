package com.bpns.mobile.mini.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

//@Data
@Entity
@Getter
@Setter
@Table(name="IB_USER")
public class UserEntity {
	
	@Id
	@Column(name = "KAKAO_USERID", length=50)
	private String kakao_userid;
	
	@Column(length=50)
	private String KAKAO_EMAIL;
	
	@Column(length=50)
	private String KAKAO_PRF_URL;
	
	@Column(length=50)
	private String KAKAO_ACCESS_TOKEN;
	
	@Column(length=50)
	private String DEVICE_ID;
	
	@Column(length=50)
	private String NICK_NAME;
	
	@Column(length=50)
	private String FCM_TOKEN;
	
	@Column(length=50)
	private String FCM_USE_YN;
	
	@Column(length=50)
	private LocalDateTime REG_DATE;
	
	@PrePersist
	public void REG_DATE() {
		this.REG_DATE = LocalDateTime.now();
	}
}
