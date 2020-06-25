package com.bpns.mobile.mini.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="IB_FILE")
public class FileEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@ManyToOne
//	@JsonManagedReference("B")
	@JsonBackReference("files")
	@JoinColumn(name = "IB_FILE_MASTER_id")
	private FileMasterEntity master_id;
	
	@Column(length=100)
	private String file_name;
	
	@Column(length=100)
	private String original_filename;
	
	@Column(length=20)
	private String type;
	private Long size;
	
	@Column(length = 6)
	private LocalDateTime REG_DATE;
	
	@PrePersist
	public void REG_DATE() {
		this.REG_DATE = LocalDateTime.now();
	}
}
