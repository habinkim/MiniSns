package com.bpns.mobile.mini.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="IB_FILE_MASTER")
public class FileMasterEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private String ctype;
	
	@OneToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name="IB_BORDER_IDX", referencedColumnName="IDX")
	@JsonBackReference("border")
	private BorderEntity cname;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "master_id")
	@JsonManagedReference("files")
	private List<FileEntity> fid = new ArrayList<>();
	
	@Column(length = 6)
	private LocalDateTime REG_DATE;
	
	@PrePersist
	public void REG_DATE() {
		this.REG_DATE = LocalDateTime.now();
	}
}