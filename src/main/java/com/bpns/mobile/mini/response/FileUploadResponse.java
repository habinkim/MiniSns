package com.bpns.mobile.mini.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class FileUploadResponse {
	private String orgName;
	private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;
    
    public FileUploadResponse(String orgName,String fileName, String fileDownloadUri, String fileType, long size) {
        this.orgName = orgName;
    	this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
    }
 
}
