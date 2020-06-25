package com.bpns.mobile.mini.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bpns.mobile.mini.model.BorderEntity;
import com.bpns.mobile.mini.model.FileMasterEntity;

@Repository
public interface BorderRepository extends JpaRepository<BorderEntity, Long> {
	
	@Query(value = "SELECT B.* FROM IB_BORDER B",
			nativeQuery=true)
	List<BorderEntity> getBorderList();
	
	
	//7. sns 게시글 작성 (유저 아이디, 게시글 사진, 게시글 내용, 게시글 작성 시간){
//	
//}
	
	@Query(value = "INSERT INTO IB_BORDER(CONTENTS, IB_USER_KAKAO_USERID) "
			+ "VALUES(:contents, :id)", 
			nativeQuery = true)
	BorderEntity createBorderEntity1(@Param("contents") String Contents, @Param("id") String Id);
	
	@Query(value = "INSERT INTO IB_BORDER(CONTENTS, IB_USER_KAKAO_USERID, FILE) "
			+ "VALUES(:contents, :id, :#{#file.id})", 
			nativeQuery = true)
	BorderEntity createBorderEntity2(@Param("contents") String Contents, @Param("id") String Id
			, @Param("file") FileMasterEntity File);
	
	@Query(value = "UPDATE IB_BORDER "
			+ "SET CONTENTS = :contents "
			+ "WHERE IDX = :idx", nativeQuery = true)
	Boolean updateBorderEntity1(@Param("contents") String Contents, @Param("idx") Integer Idx);
	
	@Query(value = "UPDATE IB_BORDER "
			+ "SET (CONTENTS = :contents, FILE = :#{#file.id}) "
			+ "WHERE IDX = :idx", nativeQuery = true)
	Boolean updateBorderEntity2(@Param("contents") String Contents, @Param("idx") Integer Idx
			, @Param("file") FileMasterEntity File);

	@Query(value = "DELETE FROM "
			+ "IB_BORDER "
			+ "WHERE IDX = :idx", nativeQuery = true)
	Boolean deleteBorderEntity(@Param("idx") Long Idx);
	
	@Query(value = "SELECT IB_BORDER.* " + 
			"FROM IB_BORDER LEFT JOIN IB_FILE_MASTER " + 
			"ON IB_BORDER.FILE = IB_FILE_MASTER.IB_BORDER_IDX WHERE IB_BORDER.IDX = :idx", 
			nativeQuery = true)
	FileMasterEntity getFileMByborderIdx(@Param("idx") Integer Idx);
}