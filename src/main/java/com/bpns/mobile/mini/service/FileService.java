package com.bpns.mobile.mini.service;

import java.util.List;
import java.util.Map;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.bpns.mobile.mini.FileUploadProperties;
import com.bpns.mobile.mini.exception.FileUploadException;
import com.bpns.mobile.mini.model.FileEntity;
import com.bpns.mobile.mini.model.FileMasterEntity;
import com.bpns.mobile.mini.repository.FileMasterRepository;
import com.bpns.mobile.mini.repository.FileRepository;
import com.bpns.mobile.mini.util.CommUtil;

@Service
public class FileService {
	private final Path fileLocation;
	   private final String sep; 
	   
	    @Autowired
	    FileRepository fileRepository;
	    
	    @Autowired
	    FileMasterRepository fileMasterRepository;
	    
	    public FileService(FileUploadProperties prop) {
	        this.fileLocation = Paths.get(prop.getUploadDir())
	                .toAbsolutePath().normalize();
	        this.sep = prop.getSeplatorChar();
	        
	        try {
	            Files.createDirectories(this.fileLocation);
	        }catch(Exception e) {
	            throw new FileUploadException("파일을 업로드할 디렉토리를 생성하지 못했습니다.", e);
	        }
	    }
	    
	

	    public FileMasterEntity saveFileMaster(FileMasterEntity fileMasterEntity) {
	    	
	    	return fileMasterRepository.save(fileMasterEntity);
	    }
	    
	    public Map<String, Object> setFileMaster(FileMasterEntity fileMaster, List<FileEntity> f_list) {
            Map<String, Object> r = new HashMap<>();
	    	
	    	fileMaster.setFid(f_list);
            fileMasterRepository.save(fileMaster);
            
            for (int i = 0 ; i < f_list.size() ; i++) {
    			f_list.get(i).setMaster_id(fileMaster);
    		}
                        
            r.put("fm", fileMaster);
            r.put("flist", f_list);
            
            return r;
	    }
	    
	    public void saveFile(FileEntity fe) {
	    	fileRepository.save(fe);
	    }
	    
	    public List<FileEntity> storeFile(MultipartFile file, List<FileEntity> f_list) {
	        String fileName =  StringUtils.cleanPath(file.getOriginalFilename());
	       
	        try {
	            // 파일명에 부적합 문자가 있는지 확인한다.
	            if(fileName.contains(".."))
	                throw new FileUploadException("파일명에 부적합 문자가 포함되어 있습니다. " + fileName);
	            
	           
	            Path targetLocation = this.fileLocation.resolve(fileName);
	            
	            String newfile = CommUtil.appendSuffixName(targetLocation.toString(), 0);
	            Path newpath = this.fileLocation.resolve(newfile);
	            String newFileName = newpath.toString()
	                    .substring(newpath.toString().lastIndexOf(this.sep)).replace(this.sep,"");
	            
	            Files.copy(file.getInputStream(), newpath, StandardCopyOption.REPLACE_EXISTING);
	            
            	FileEntity upfile = new FileEntity();
	            
	            upfile.setFile_name(newFileName);
	            upfile.setOriginal_filename(file.getOriginalFilename());
	            upfile.setType(file.getContentType());
	            upfile.setSize(file.getSize());
//	            upfile.setMaster_id(fileMaster);
//	            
	            f_list.add(upfile);
	            
   	            fileRepository.save(upfile);
	            
	            return f_list;
	        }catch(Exception e) {
	            throw new FileUploadException("["+fileName+"] 파일 업로드에 실패하였습니다. 다시 시도하십시오.",e);
	        }
	    }
}
