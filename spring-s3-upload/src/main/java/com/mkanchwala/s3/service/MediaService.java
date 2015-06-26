package com.mkanchwala.s3.service;

import java.io.File;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.model.ProgressEvent;
import com.amazonaws.services.s3.model.ProgressListener;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;

@Component
public class MediaService {
	private static Logger logger = LogManager.getLogger(MediaService.class);
	
	@Resource
    private Environment env;
	
	/**
	 * Service : To Upload any kind of file to S3 bucket
	 * 
	 * @param file
	 * @throws AmazonServiceException
	 * @throws AmazonClientException
	 * @throws InterruptedException
	 */
	@SuppressWarnings("deprecation")
	public void uploadtoS3(File file) throws AmazonServiceException, AmazonClientException, InterruptedException{
		logger.debug("Uploading the File => S3");
		ProfileCredentialsProvider credentialProviderChain = new ProfileCredentialsProvider();
        TransferManager tx = new TransferManager(credentialProviderChain.getCredentials());
        final Upload upload = tx.upload(env.getProperty("amazon.s3.media.bucket"), file.getName(), file);
         
        // You can poll your transfer's status to check its progress
        if (upload.isDone() == false) {
        	logger.debug("Transfer: " + upload.getDescription());
        	logger.debug("State: " + upload.getState());
        	logger.debug("Progress: " + upload.getProgress().getBytesTransferred());
        }
         
        // Transfers also allow you to set a <code>ProgressListener</code> to receive
        // asynchronous notifications about your transfer's progress.
        
        upload.addProgressListener(new ProgressListener() {
            // This method is called periodically as your transfer progresses
            public void progressChanged(ProgressEvent progressEvent) {
            	logger.debug(upload.getProgress().getPercentTransferred() + "%");
         
                if (progressEvent.getEventCode() == ProgressEvent.COMPLETED_EVENT_CODE) {
                	logger.debug("Upload complete!!!");
                }
            }
        });
         
        // Or you can block the current thread and wait for your transfer to
        // to complete. If the transfer fails, this method will throw an
        // AmazonClientException or AmazonServiceException detailing the reason.
        upload.waitForCompletion();
         
        // After the upload is complete, call shutdownNow to release the resources.
        tx.shutdownNow();
	}
}
