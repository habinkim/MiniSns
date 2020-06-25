package com.bpns.mobile.mini.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bpns.mobile.mini.exception.RecordNotFoundException;
import com.bpns.mobile.mini.model.BorderEntity;
import com.bpns.mobile.mini.model.FavEntity;
import com.bpns.mobile.mini.model.FileEntity;
import com.bpns.mobile.mini.model.FileMasterEntity;
import com.bpns.mobile.mini.model.ReplyEntity;
import com.bpns.mobile.mini.model.UserEntity;
import com.bpns.mobile.mini.service.BorderService;
import com.bpns.mobile.mini.service.FavService;
import com.bpns.mobile.mini.service.FileService;
import com.bpns.mobile.mini.service.ReplyService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@RestController
@Configuration
@RequestMapping("/border") // 게시판
//@CrossOrigin(origins = {"http://localhost:3000","http://192.168.0.26:3000"})
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8000" }) 
public class BorderController implements WebMvcConfigurer {
	
	@Autowired
	private BorderService b_service;
	
	@Autowired
	private FavService f_service;
	
	@Autowired
	private ReplyService r_service;
	
	@Autowired
	private FileService file_service;
	
	//6. sns 게시글 받기 (유저 아이디, 요청 개수){
	//	리턴 {
	//		유저 이미지, 
	//		유저 닉네임, 
	//		콘텐츠 아이디, 
	//		컨텐츠 좋아요 눌렀는지,
	//		댓글 개수,
	//		내용, 
	//		작성 날짜,
	//	}		
	//}

	@PostMapping(value = "/view", consumes = MediaType.APPLICATION_JSON_VALUE) // 게시물 리스트 조회
	public List<BorderEntity> getBorderList(@RequestBody Map<String, Object> params) throws RecordNotFoundException {
		ObjectMapper om = new ObjectMapper();
		
		Integer page = Integer.parseInt(params.get("page").toString()); // Request 파라미터: 페이지 번호
		UserEntity ue = om.convertValue(params.get("ue"), UserEntity.class); // Request 파라미터: 유저
		PageRequest pr = PageRequest.of(page, 10, Direction.DESC, "IDX"); // 페이징 리퀘스트 정보, 게시물 번호 기준 내림차순
		
		List<BorderEntity> list = b_service.getBorderList(pr); // 페이징 리퀘스트에 맞춰 불러온 게시물 리스트

		for(int i = 0; i < list.size(); i++) {
			list.get(i).setMyFav(f_service.getBorderMyFav(list.get(i), ue)); // 각 게시물마다 사용자의 좋아요여부 설정
		}
		
		return list;
	}
	
	//7. sns 게시글 작성 (유저 아이디, 게시글 사진, 게시글 내용, 게시글 작성 시간){
	//	
	//}
	
	@PostMapping(value = "/insert", consumes = MediaType.APPLICATION_JSON_VALUE) // 게시물 작성
	public BorderEntity createBorderEntity1(@RequestBody Map<String, Object> params) throws RecordNotFoundException {
		String Id = params.get("Id").toString(); // Request 파라미터 : 작성자 id
		String Contents = params.get("Contents").toString(); // Request 파라미터 : 게시물 내용
		BorderEntity be = b_service.createBorderEntity1(Contents, Id); // 게시물 작성
		return be;
	}
	
	@PostMapping(value = "/setfile") // 파일 저장
	public List<FileEntity> getFileMasterEntity(@RequestPart List<MultipartFile> files) throws RecordNotFoundException {
		FileMasterEntity fm = new FileMasterEntity(); // filemaster
		List<FileEntity> f_list = new ArrayList<>(); // file들
		
		fm.setCtype("img"); // file들의 type
		file_service.saveFileMaster(fm); 
		
		for (int i = 0 ; i < files.size() ; i++) {
			f_list = file_service.storeFile(files.get(i), f_list);
		} // file들 리스트 형태로 저장
		
		return f_list;
	}
	
	@PostMapping(value = "/insert/withfile", consumes = MediaType.APPLICATION_JSON_VALUE) // 정상작동
	public BorderEntity createBorderEntity2(@RequestBody Map<String, Object> params) throws RecordNotFoundException, JsonParseException, JsonMappingException, IOException {
		Map<String, Object> rt = new HashMap<>();
		ObjectMapper om = new ObjectMapper();
		om.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		om.registerModule(new JavaTimeModule());
		om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		
		String Id = params.get("Id").toString();
		String Contents = params.get("Contents").toString();
		List<FileEntity> filelist = Arrays.asList(om.readValue(om.writeValueAsString(params.get("Files")), FileEntity[].class));
		
		FileMasterEntity fme = new FileMasterEntity();
		
		fme.setFid(filelist);
		rt = file_service.setFileMaster(fme, filelist);
		
		fme = (FileMasterEntity) rt.get("fm");
		filelist = (List<FileEntity>) rt.get("flist");
			
		BorderEntity be = b_service.createBorderEntity2(Contents, Id, fme);
		fme.setCname(be);
		
		for(int i=0; i< filelist.size(); i++) {
			
			filelist.get(i).setMaster_id(fme);
			file_service.saveFile(filelist.get(i));
			
		}
		
		return be;
	}
	
	@PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE) // 정상작동
	public Boolean updateBorderEntity1(@RequestBody Map<String, Object> params) {
		Integer Idx = Integer.parseInt(params.get("Idx").toString());
		String Contents = params.get("Contents").toString();
		
		Boolean b = b_service.updateBorderEntity1(Contents, Idx);
		return b;
	}
	
	@PostMapping(value = "/delete") // 정상작동
	public Boolean deleteBorderEntity(@RequestParam Long Idx) throws RecordNotFoundException {
		Boolean b = b_service.deleteBorderEntity(Idx);
		return b;
	}
	
	
	@GetMapping(value = "/fav") // 정상작동
	public List<FavEntity> getFavByborderIdx(@RequestParam Integer Idx) throws RecordNotFoundException {
		List<FavEntity> list = f_service.getFavByborderIdx(Idx);
		return list;
	}
	
	@PostMapping(value = "/fav/insert", consumes = MediaType.APPLICATION_JSON_VALUE) // 정상작동
	public FavEntity createFavEntity(@RequestBody Map<String, Object> params) throws RecordNotFoundException {
		
		Integer Idx = Integer.parseInt(params.get("Idx").toString());
		String Id = params.get("Id").toString();
		
		FavEntity fe = f_service.createFavEntity(Idx, Id);
		return fe;
	}
	
	@PostMapping(value = "/fav/delete", consumes = MediaType.APPLICATION_JSON_VALUE) // 경철씨 수정
	public Boolean deleteFavEntity(@RequestBody Map<String, Object> params) throws RecordNotFoundException {
		Integer bIdx = Integer.parseInt(params.get("Idx").toString());
		String Id = params.get("Id").toString();
		
		Boolean b = f_service.deleteFavEntity(bIdx, Id);
		if(b == null) b = true;
		return b;
	}
			
	//8. 댓글 리스트 받기 (콘텐츠 아이디) {
	//	리턴 {
	//		닉네임,	
	//		유저 이미지,
	//		댓글 내용,
	//		댓글 작성 날짜
	//	}	
	//}
	
	@GetMapping("/reply") // 정상작동
	public List<ReplyEntity> getReplyByborderIdx(@RequestParam Integer Idx) throws RecordNotFoundException {
		List<ReplyEntity> list = r_service.getReplyByborderIdx(Idx);
		return list;
	}
	
	//
	//9. 댓글 작성 (콘텐츠 아이디, 댓글 내용, 내 아이디, 댓글 작성 시간) {
	//	
	//}
	
	@PostMapping(value = "/reply/insert", consumes = MediaType.APPLICATION_JSON_VALUE) // 정상작동 
	public ReplyEntity createReplyEntity(@RequestBody Map<String, Object> params) throws RecordNotFoundException {
		
		Integer Idx = Integer.parseInt(params.get("Idx").toString());
		String Contents = params.get("Contents").toString();
		String Id = params.get("Id").toString();
		
		ReplyEntity re = r_service.createReplyEntity(Idx, Contents, Id);
		return re;
	}
	
	@PostMapping(value = "reply/update", consumes = MediaType.APPLICATION_JSON_VALUE) // 정상작동
	public Boolean updateReplyEntity(@RequestBody Map<String, Object> params) throws RecordNotFoundException {
		Integer Idx = Integer.parseInt(params.get("Idx").toString());
		String Content = params.get("Content").toString();
		
		Boolean b = r_service.updateReplyEntity(Content, Idx);
		return b;
	}
	
	@PostMapping(value = "reply/delete") // 정상작동
	public Boolean deleteReplyEntity(@RequestParam Long Idx) throws RecordNotFoundException {
		Boolean b = r_service.deleteReplyEntity(Idx);
		return b;
	}
}