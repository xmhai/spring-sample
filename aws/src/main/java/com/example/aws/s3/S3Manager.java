package com.example.aws.s3;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;

import io.awspring.cloud.core.io.s3.PathMatchingSimpleStorageResourcePatternResolver;

@Service
public class S3Manager {
	@Value("${s3.testBucket}")
	private String bucket;

    @Autowired
    private AmazonS3 amazonS3;

    public void uploadWithTransferManager(String pathname) {
    	System.out.println(pathname);
    	File file = new File(pathname);
        TransferManager transferManager = TransferManagerBuilder.standard()
                                                                .withS3Client(this.amazonS3)
                                                                .build();
        transferManager.upload(bucket, file.getName(), file);
    	System.out.println("upload success!!!");
    }

    @Autowired
    private ResourceLoader resourceLoader;

    public InputStream download(String object) throws IOException {
        Resource resource = this.resourceLoader.getResource("s3://"+bucket+"/"+object);

        return resource.getInputStream();
    }

    private ResourcePatternResolver resourcePatternResolver;

    @Autowired
    public void setupResolver(ApplicationContext applicationContext, AmazonS3 amazonS3){
        this.resourcePatternResolver = new PathMatchingSimpleStorageResourcePatternResolver(amazonS3, applicationContext);
    }

    public List<String> list() throws IOException {
        //Resource[] allTxtFilesInFolder =  this.resourcePatternResolver.getResources("s3://bucket/name/*.txt");
        //Resource[] allTxtFilesInBucket =  this.resourcePatternResolver.getResources("s3://bucket/**/*.txt");
        //Resource[] allTxtFilesGlobally =  this.resourcePatternResolver.getResources("s3://**/*.txt");
        
        Resource[] allFilesInBucket =  this.resourcePatternResolver.getResources("s3://" + bucket + "/**/*.*");
        List<String> objects = new ArrayList<String>();
        for (Resource resource : allFilesInBucket) {
        	objects.add(resource.getFilename());
        }
        return objects;
    }
}