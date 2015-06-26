package com.mkanchwala.s3.controllers;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mkanchwala.s3.service.MediaService;

@RestController
public class MediaController {
	private static Logger logger = LogManager.getLogger(MediaController.class);
	
	@Autowired
	private MediaService mediaService;

	@RequestMapping(value = "/public/upload", method = RequestMethod.POST)
	public String handleFileUpload(@RequestParam("name") String name,	@RequestParam("file") MultipartFile file) {
		logger.debug("MK-REST : Call to Upload to S3 " + file.getOriginalFilename()); 
		if (!file.isEmpty()) {
			try {
				File convFile = new File(file.getOriginalFilename());
				file.transferTo(convFile);
				mediaService.uploadtoS3(convFile);
				return "You successfully uploaded " + file.getOriginalFilename() + " to S3";
			} catch (Exception e) {
				return "You failed to upload " + name + " => " + e.getMessage();
			}
		} else {
			return "You failed to upload " + name + " because the file was empty.";
		}
	}
}