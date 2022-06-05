package com.example.download;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@SpringBootApplication
@RestController
public class DownloadApplication {
	private final Logger logger = LoggerFactory.getLogger(DownloadApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DownloadApplication.class, args);
	}

	@GetMapping("/")
	public void sayHello(HttpServletResponse response) throws Exception {
	    int MB = 1024*1024;

	    //Getting the runtime reference from system
	    Runtime runtime = Runtime.getRuntime();

	    //Print used memory
	    response.getWriter().write("Used Memory:" + (runtime.totalMemory() - runtime.freeMemory()) / MB + "\n");

	    //Print free memory
	    response.getWriter().write("Free Memory:" + runtime.freeMemory() / MB + "\n");

	    //Print total available memory
	    response.getWriter().write("Total Memory:" + runtime.totalMemory() / MB + "\n");

	    //Print Maximum available memory
	    response.getWriter().write("Max Memory:" + runtime.maxMemory() / MB + "\n");
	}
	
	@GetMapping("/download")
	public ResponseEntity<StreamingResponseBody> downloadFile() throws Exception { //HttpServletResponse response
	    logger.info("Request for download...");
	    
	    StreamingResponseBody responseBody = out -> {
	    	try (FileInputStream fis = new FileInputStream(new File("C:\\Users\\hai\\Downloads\\CentOS-7-x86_64-DVD-2009.iso"))) {
		        int bytesRead;
		        byte[] buffer = new byte[65536];
		        while ((bytesRead = fis.read(buffer)) != -1) {
		    	    out.write(buffer, 0, bytesRead);
		        }
		        fis.close();
		        out.close();
            } catch (final IOException e) {
                logger.error("Exception while reading and streaming data {} ", e);
            }
	    };
	    
	    logger.info("Done!!!");
	    return ResponseEntity.ok()
	            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=download.file")
	            .contentType(MediaType.APPLICATION_OCTET_STREAM)
	            .body(responseBody);
	    
	}
}
