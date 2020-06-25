package com.bpns.mobile.mini.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bpns.mobile.mini.model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long>{
	
	@Query(value = "SELECT * "
			+ "FROM IB_USER "
			+ "WHERE KAKAO_USERID = :id", nativeQuery = true) 
	// JPQL문: 카카오 ID 일치 확인
	UserEntity findById(@Param("id") String Id);
	
	@Query(value = "INSERT INTO IB_USER "
			+ "the VALUES(:id, :email, :url, :token, :device, :nick, :ftoken, :yn)", nativeQuery = true)
	boolean createUserEntity(
			@Param("id") String Id,
			@Param("email") String Email,
			@Param("url") String P_Url,
			@Param("token") String Token,
			@Param("device") String D_Id,
			@Param("nick") String Nickname,
			@Param("ftoken") String F_Token,
			@Param("yn") String YN
			);
	
	@Query(value = "SELECT NICK_NAME, FCM_USE_YN"
			+ "FROM IB_USER"
			+ "WHERE KAKAO_USERID = :id", nativeQuery = true)
	// JPQL문: 마이페이지- 닉네임과 푸시알람 설정 불러오기
	UserEntity getMysetById(@Param("id") String Id);
	
	
	@Query(value = "UPDATE IB_USER "
			+ "SET NICK_NAME = :nick "
			+ "WHERE KAKAO_USERID = :id", nativeQuery = true)
	Boolean updateUserNick(@Param("nick") String Nickname, 
			@Param("id") String Id);
	
	@Query(value = "UPDATE IB_USER "
			+ "SET FCM_USE_YN = :yn "
			+ "WHERE KAKAO_USERID = :id", nativeQuery = true)
	Boolean updateFcmUse(@Param("yn") String YN, 
			@Param("id") String Id);
	
}
