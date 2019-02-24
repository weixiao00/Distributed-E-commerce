package com.lsl.manage.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.lsl.manage.service.PicService;

@Controller
public class PicController {

	@Autowired
	private PicService picService;
	/**
	 * 插入图片方法
	 * @param uploadFile
	 * @return
	 */
	@RequestMapping("/pic/upload")
	@ResponseBody
	public Map<String,Object> upLoad(MultipartFile uploadFile){
		Map<String,Object> map = null;
		try {
			map = picService.upload(uploadFile);
		} catch (IOException e) {
			map.put("error", 1);
			map.put("message", "上传图片时服务器出现异常");
		}
		return map;
	}
}
