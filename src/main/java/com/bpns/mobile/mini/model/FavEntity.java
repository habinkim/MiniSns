package com.bpns.mobile.mini.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="IB_FAV")
public class FavEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(length = 11)
	private Integer IDX;
	
	@ManyToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name="IB_BORDER_IDX", referencedColumnName="IDX")
	private BorderEntity bo;
	
	@Column
	private LocalDateTime REG_DATE;
	
	@ManyToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name="IB_USER_KAKAO_USERID", referencedColumnName="KAKAO_USERID")
	private UserEntity us;
	
	@PrePersist
	public void REG_DATE() {
		this.REG_DATE = LocalDateTime.now();
	}
}
