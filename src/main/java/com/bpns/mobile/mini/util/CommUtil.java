package com.bpns.mobile.mini.util;

import java.io.File;

public class CommUtil {
	
	
	public static String appendSuffixName(String orgFileName, int seq) {
        String retFileName = "";
        // 파일이 존재하는지 확인한다.
        if (new File(orgFileName).exists()) {
            int plusSeq = 1;
 
            String seqStr = "_" + seq;
            String firstFileName = orgFileName.substring(0,
                    orgFileName.lastIndexOf("."));
            String extName = orgFileName
                    .substring(orgFileName.lastIndexOf("."));
 
            // 만약 파일명에 _숫자가 들어간경우라면..
            if (orgFileName.lastIndexOf("_") != -1
                    && !firstFileName.endsWith("_")) {
                String numStr = orgFileName.substring(
                        orgFileName.lastIndexOf("_") + 1,
                        orgFileName.lastIndexOf(extName));
                try {
                    plusSeq = Integer.parseInt(numStr);
                    plusSeq = plusSeq + 1;
                    
                    retFileName = firstFileName.substring(0,
                            firstFileName.lastIndexOf("_"))
                            + "_" + plusSeq + extName;
                } catch (NumberFormatException e) {
                    retFileName = firstFileName + seqStr + extName;
                    return appendSuffixName(retFileName, ++plusSeq);
                }
                
            } else {
                retFileName = firstFileName + seqStr + extName;
            }
            // 재귀
            return appendSuffixName(retFileName, ++plusSeq);
        } else {
            return orgFileName;
        }
    }

	



}