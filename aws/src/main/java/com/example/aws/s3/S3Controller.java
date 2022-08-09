package com.example.aws.s3;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@RestController
@RequestMapping("/s3")
public class S3Controller {
	private final Logger logger = LoggerFactory.getLogger(S3Controller.class);
	
	@Autowired
	private S3Manager s3Manager;

	@GetMapping("/list")
	public List<String> list() throws IOException {
		return s3Manager.list();
	}
	
	@PostMapping("/upload")
	public void upload(@RequestBody String pathname) {
		s3Manager.uploadWithTransferManager(pathname);
	}
	
	@GetMapping("/download/{object}")
	public ResponseEntity<StreamingResponseBody> download(@PathVariable("object") String object) {

	    StreamingResponseBody responseBody = out -> {
	    	try (InputStream in = s3Manager.download(object)) {
		        int bytesRead;
		        byte[] buffer = new byte[65536];
		        while ((bytesRead = in.read(buffer)) != -1) {
		    	    out.write(buffer, 0, bytesRead);
		        }
		        in.close();
		        out.close();
            } catch (final IOException e) {
                logger.error("Exception while reading and streaming data {} ", e);
            }
	    };
	    
	    logger.info("Done!!!");
	    return ResponseEntity.ok()
	            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+object)
	            .contentType(MediaType.APPLICATION_OCTET_STREAM)
	            .body(responseBody);
	}
}
