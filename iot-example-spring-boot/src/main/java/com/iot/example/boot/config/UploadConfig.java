package com.iot.example.boot.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "upload")
@Configuration(value="uploadConfig")
public class UploadConfig {
	private String path;
	private Map<String,String> picMap = new HashMap<>();
	private List<String> fileParentPath=new ArrayList<>();
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Map<String, String> getPicMap() {
		return picMap;
	}

	public void setPicMap(Map<String, String> picMap) {
		this.picMap = picMap;
	}

	public List<String> getFileParentPath() {
		return fileParentPath;
	}

	public void setFileParentPath(List<String> fileParentPath) {
		this.fileParentPath = fileParentPath;
	}

}
