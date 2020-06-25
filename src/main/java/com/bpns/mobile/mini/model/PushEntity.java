package com.bpns.mobile.mini.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name="IB_PUSH")
public class PushEntity {
	@Id
	@Column(name="DEVICE_ID", length=50)
	private String DEVICE_ID;
	
	@Column(name="TOKEN", length=50)
	private String TOKEN;
	
	@Column(name="TITLE", length=50)
	private String TITLE;
	
	@Column(name="CONTENT", length=50)
	private String CONTENT;
	
	@Column(name="RESERVE_YN", length=1)
	private String RESERVE_YN;
	
//	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="RESERVE_DATE")
	private LocalDateTime RESERVE_DATE;
	
	@Column(name="REG_ID", length=50)
	private String REG_ID;
	
//	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="REG_DATE")
	private LocalDateTime REG_DATE;
	
	@Column(name="SEND_YN", length=1)
	private String SEND_YN;
	
//	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="SEND_DATE")
	private LocalDateTime SEND_DATE;
	
	@PrePersist
	public void REG_DATE() {
		this.REG_DATE = LocalDateTime.now();
	}
}
