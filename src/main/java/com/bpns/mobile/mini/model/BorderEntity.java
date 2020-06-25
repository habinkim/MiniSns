package com.bpns.mobile.mini.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="IB_BORDER")
@Getter
@Setter
public class BorderEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IDX", length=11)
	private Integer IDX;
	
	@Column(name="CONTENTS", length=200)
	private String CONTENTS;
	
	@Column(name="IMG_URL", length=1)
	private String IMG_URL;
	
	@Column(name="IMG_THUMNAIL_URL", length=1)
	private String IMG_THUMNAIL_URL;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="FILE", referencedColumnName="id")
	@JsonManagedReference("border")
	private FileMasterEntity file_m;
	
	@Column(name="REG_DATE")
	private LocalDateTime REG_DATE;
	
	@ManyToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name="IB_USER_KAKAO_USERID", referencedColumnName="KAKAO_USERID")
	private UserEntity us;
	
	@Formula("(select count(*) from IB_REPLY r where r.IB_BORDER_IDX = IDX)")
	private int countOfReply;
	
	@Formula("(select count(*) from IB_FAV f where f.IB_BORDER_IDX = IDX)")
	private int countOfFav;

	@Column(name="MY_FAV")
	private Integer myFav;
	
	@PrePersist
	public void REG_DATE() {
		this.REG_DATE = LocalDateTime.now();
	}
}
