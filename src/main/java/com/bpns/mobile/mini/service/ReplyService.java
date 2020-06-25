package com.bpns.mobile.mini.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bpns.mobile.mini.exception.RecordNotFoundException;
import com.bpns.mobile.mini.model.BorderEntity;
import com.bpns.mobile.mini.model.ReplyEntity;
import com.bpns.mobile.mini.model.UserEntity;
import com.bpns.mobile.mini.repository.ReplyRepository;

@Service
public class ReplyService {
	@Autowired
	ReplyRepository rrp;
	
	public List<ReplyEntity> getReplyByborderIdx(Integer Idx) { // 게시물의 댓글 불러오기
		List<ReplyEntity> list = rrp.getReplyByborderIdx(Idx);
		return list;
	}
	
	public ReplyEntity createReplyEntity(Integer Idx, String Contents, String Id) throws RecordNotFoundException {
		ReplyEntity re = new ReplyEntity();
		BorderEntity be = new BorderEntity();
		UserEntity ue = new UserEntity();
		
		be.setIDX(Idx);
		ue.setKakao_userid(Id);
		re.setCONTENT(Contents);
		re.setBo(be);
		re.setUs(ue);
		
		re = rrp.save(re);
		
		return re;
	}
	
	public Boolean updateReplyEntity(String Content, Integer Idx) {
		Boolean b = rrp.updateReplyEntity(Content, Idx);
		return b;
	}
	
	public Boolean deleteReplyEntity (Long Idx) throws RecordNotFoundException {
		Boolean b = rrp.deleteReplyEntity(Idx);
		return b;
	}
	
	//
}
