package com.bpns.mobile.mini.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bpns.mobile.mini.model.ReplyEntity;

@Repository
public interface ReplyRepository extends JpaRepository<ReplyEntity, Long> {
	
	@Query(value = "SELECT IB_REPLY.* "
			+ "FROM IB_REPLY LEFT JOIN IB_BORDER "
			+ "ON IB_BORDER.IDX = IB_REPLY.IB_BORDER_IDX WHERE IB_BORDER.IDX = :idx",
			nativeQuery=true) 
	// MySQL문: 게시물의 댓글 불러오기
	List<ReplyEntity> getReplyByborderIdx(@Param("idx") Integer Idx);
	
	@Query(value = "INSERT INTO "
			+ "IB_BORDER(CONTENT, IB_BORDER_IDX, IB_USER_KAKAO_USERID) "
			+ "VALUES(:contents, :idx, :ue.id)", 
			nativeQuery = true)
	ReplyEntity createReplyEntity(@Param("idx") Integer Idx, @Param("contents") String Contents, @Param("id") String Id);
	
	@Query(value = "UPDATE IB_REPLY "
			+ "SET CONTENT = :content "
			+ "WHERE IDX = :idx", nativeQuery = true)
	Boolean updateReplyEntity(@Param("content") String Content, @Param("idx") Integer Idx);
	
	
	@Query(value = "DELETE FROM "
			+ "IB_REPLY "
			+ "WHERE IDX = :idx", nativeQuery = true)
	Boolean deleteReplyEntity(@Param("idx") Long Idx);
	
	//
}
