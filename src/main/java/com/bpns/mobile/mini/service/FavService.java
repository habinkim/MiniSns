package com.bpns.mobile.mini.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bpns.mobile.mini.exception.RecordNotFoundException;
import com.bpns.mobile.mini.model.BorderEntity;
import com.bpns.mobile.mini.model.FavEntity;
import com.bpns.mobile.mini.model.UserEntity;
import com.bpns.mobile.mini.repository.FavRepository;

@Service
public class FavService {
	@Autowired
	FavRepository frp;
	
	public List<FavEntity> getFavByborderIdx(Integer Idx) throws RecordNotFoundException {
		List<FavEntity> list = frp.getFavByborderIdx(Idx);
		return list;
	}
	
	public FavEntity createFavEntity(Integer Idx, String Id) throws RecordNotFoundException {
		BorderEntity be = new BorderEntity();
		UserEntity ue = new UserEntity();
		FavEntity fe = new FavEntity();
		
		be.setIDX(Idx);
		ue.setKakao_userid(Id);
		
		fe.setBo(be);
		fe.setUs(ue);
		
		fe = frp.save(fe);
		return fe;
	}
	
	public Boolean deleteFavEntity(Integer bIdx, String Id) throws RecordNotFoundException {
		Boolean b = frp.deleteFavEntity(bIdx, Id);
		return b;
	}
	
	public int getBorderMyFav(BorderEntity bo, UserEntity ue) throws RecordNotFoundException {
		
		List<FavEntity> list = frp.getMyFav(bo.getIDX(), ue.getKakao_userid());
		return list.size();
	}

	//
}
