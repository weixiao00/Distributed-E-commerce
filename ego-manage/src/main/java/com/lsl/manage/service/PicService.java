package com.lsl.manage.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface PicService {

	/**
	 * 图片上传功能
	 * @param file
	 */
	Map<String,Object> upload(MultipartFile file) throws IOException;
}
