package com.springhotel.common;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public String getSaveFilename(String uploadPath, String filename) {
		String saveFilename = filename;
		File uploadFile = new File(uploadPath);
		File saveFile = new File(uploadPath, filename);
		String name = filename;
		String ext = "";
		
		uploadFile.mkdirs();
		
		int pos = filename.lastIndexOf(".");
		if (pos > 0) {
			name = filename.substring(0, pos);
			ext = filename.substring(pos + 1);
		}
		
		
		if (saveFile.exists()) {
			for (int i = 1; true; i++) {
				saveFilename = name + " (" + i + ")." + ext;
				saveFile = new File(uploadPath, saveFilename); 
				if (! saveFile.exists())
					break; 
			}
		}
		System.out.println("savedFilename: " + saveFilename);
		return saveFilename;
	}
	
	/*
	Request Header
		Content-Length:221824
		Content-Type:multipart/form-data; boundary=----WebKitFormBoundaryJ72zJ1H0PnOntxjr
	
	Request Payload
		------WebKitFormBoundaryJ72zJ1H0PnOntxjr
		Content-Disposition: form-data; name="boardType"
		guest
		Content-Disposition: form-data; name="title"
		파일1
		------WebKitFormBoundaryJ72zJ1H0PnOntxjr
		Content-Disposition: form-data; name="content"
		파일1
		------WebKitFormBoundaryJ72zJ1H0PnOntxjr
		Content-Disposition: form-data; name="file"; filename="영진이.png"
		Content-Type: image/png
		------WebKitFormBoundaryJ72zJ1H0PnOntxjr--
	
	*/
    public Map<String,Object> parse(HttpServletRequest req, String uploadPath, String subPath) throws Exception {
    	Map<String,Object> parm = new HashMap<String,Object>();
        List<Map<String, Object>> files = new ArrayList<Map<String,Object>>();
        Map<String,Object> file = null;
        DiskFileItemFactory diskFactory = null;
        parm.put("files", files);
        boolean isPathSet = false;
        
    	if (! ServletFileUpload.isMultipartContent(req))
    		return parm;

		diskFactory = new DiskFileItemFactory();
		
		//diskFactory.setRepository(new File(uploadPath));
		
		List<FileItem> multiparts = new ServletFileUpload(diskFactory).parseRequest(req);

		for (FileItem item : multiparts) {
			if (item.isFormField()) {
				parm.put(item.getFieldName(), item.getString("UTF-8"));

				logger.debug(String.format("%s:%s", item.getFieldName(), item.getString("UTF-8")));
				if (subPath != null && item.getFieldName().equals(subPath)) {
					subPath = item.getString("UTF-8");
				}
			}
			else {
				if (item.getName().length() == 0 || item.getSize() == 0)
					continue;
				
				if (subPath != null && ! isPathSet) {
					uploadPath += (File.separator + subPath);
					isPathSet = true;
				}
				
				String savedFilename = getSaveFilename(uploadPath, item.getName());
				item.write(new File(uploadPath, savedFilename));
				
				file = new HashMap<String,Object>();
				file.put("filename", item.getName());
				file.put("savedFilename", savedFilename);
				file.put("filesize", item.getSize());
				
				System.out.printf("uploadPath %s, filename %s, filesize %d\n",
										uploadPath,
										item.getName(), item.getSize());
				
				files.add(file);
			}
		}
        return parm;
    }
    
    public byte[] getByteArray(String uploadPath, String savedFilename) throws IOException {
    	File file = new File(uploadPath, savedFilename);
    	byte[] fileByte = FileUtils.readFileToByteArray(file);;
    	return fileByte;
    }

}
