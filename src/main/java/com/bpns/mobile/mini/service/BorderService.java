package com.bpns.mobile.mini.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.bpns.mobile.mini.exception.RecordNotFoundException;
import com.bpns.mobile.mini.model.BorderEntity;
import com.bpns.mobile.mini.model.FileMasterEntity;
import com.bpns.mobile.mini.model.UserEntity;
import com.bpns.mobile.mini.repository.BorderRepository;
import com.bpns.mobile.mini.repository.FileMasterRepository;

@Service
public class BorderService {
	
	@Autowired
	BorderRepository brp;
	
	@Autowired
	FileMasterRepository fmrp;
	
	public List<BorderEntity> getBorderList(PageRequest pr) { // 전체 게시물 불러오기
		Page<BorderEntity> list = brp.findAll(pr);
		return list.getContent();
	}
	
	public BorderEntity createBorderEntity1(String Contents, String Id) throws RecordNotFoundException {
		
		BorderEntity be = new BorderEntity();
		UserEntity ue = new UserEntity();
		
		ue.setKakao_userid(Id);
		
		be.setUs(ue);
		be.setCONTENTS(Contents);
		
		be = brp.save(be);
		
		return be;
	}
	
	public BorderEntity createBorderEntity2(String Contents, String Id, FileMasterEntity File) throws RecordNotFoundException {
		
		BorderEntity be = new BorderEntity();
		UserEntity ue = new UserEntity();
		
//		brp.createBorderEntity2(Contents, Id, File);
	
		ue.setKakao_userid(Id);		
		be.setUs(ue);
		be.setCONTENTS(Contents);
		be.setFile_m(File);
		
		be = brp.save(be);
		
//		File.setCname(be);
//		fmrp.save(File);
		
		return be;
	}
	
	public Boolean updateBorderEntity1(String Contents, Integer Idx) {
		Boolean b = brp.updateBorderEntity1(Contents, Idx);
		return b;
	}
	
	public Boolean updateBorderEntity2(String Contents, Integer Idx, FileMasterEntity File) {
		Boolean b = brp.updateBorderEntity2(Contents, Idx, File);
		return b;
	}
	
	public Boolean deleteBorderEntity(Long Idx) throws RecordNotFoundException {
		Boolean b = brp.deleteBorderEntity(Idx);
		return b;
	}
	
	public FileMasterEntity getFileMByborderIdx(Integer Idx) {
		FileMasterEntity fme = brp.getFileMByborderIdx(Idx);
		return fme;
	}
}
