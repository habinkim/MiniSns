package com.bpns.mobile.mini.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bpns.mobile.mini.model.FavEntity;

@Repository
public interface FavRepository extends JpaRepository<FavEntity, Long> {
	
	@Query(value = "SELECT IB_FAV.* "
			+ "FROM IB_FAV LEFT JOIN IB_BORDER "
			+ "ON IB_BORDER.IDX = IB_FAV.IB_BORDER_IDX WHERE IB_BORDER.IDX = :idx",
			nativeQuery=true)
	// MySQL문: 게시물의 좋아요 불러오기
	List<FavEntity> getFavByborderIdx(@Param("idx") Integer Idx);
	
	
	@Query(value = "INSERT INTO "
			+ "IB_FAV(IB_BORDER_IDX, IB_USER_KAKAO_USERID) "
			+ "VALUES(:idx, :id)", 
			nativeQuery = true)
	FavEntity createFavEntity(@Param("idx") Integer Idx, @Param("id") String Id);
	
	@Query(value = "DELETE FROM "
			+ "IB_FAV "
			+ "WHERE IB_BORDER_IDX = :bidx AND IB_USER_KAKAO_USERID = :id", nativeQuery = true)
	Boolean deleteFavEntity(@Param("bidx") Integer bIdx, @Param("id") String Id);
	
	@Query(value = "SELECT A.* "
			+ "FROM IB_FAV A "
			+ "WHERE A.IB_BORDER_IDX = :idx AND A.IB_USER_KAKAO_USERID = :id",
			nativeQuery=true)
	// MySQL문: 게시물의 좋아요 불러오기
	List<FavEntity> getMyFav(@Param("idx") Integer Idx, @Param("id") String Id);
	
	//
}
