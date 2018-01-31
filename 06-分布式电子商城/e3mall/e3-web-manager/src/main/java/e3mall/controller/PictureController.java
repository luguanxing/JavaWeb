package e3mall.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import e3mall.common.utils.FastDFSClient;
import e3mall.common.utils.JsonUtils;

@Controller
@RequestMapping("/pic")
public class PictureController {

	@Value("${IMAGE_SERVER_URL}")
	private String IMAGE_SERVER_URL;
	
	@RequestMapping(value="/upload", produces=MediaType.TEXT_PLAIN_VALUE+";charset=utf-8")
	@ResponseBody
	public String uploadFile(MultipartFile uploadFile) {
		Map result = new HashMap();
		try {
			//将图片上传
			FastDFSClient fastDFSClient = new FastDFSClient("classpath:config/client.conf");
			String filename = uploadFile.getOriginalFilename();
			String extName = filename.substring(filename.lastIndexOf(".")+1);
			String url = fastDFSClient.uploadFile(uploadFile.getBytes(), extName);
			//返回图片路径和文件啊名,应该将其补全成url
			url = IMAGE_SERVER_URL + url;
			//封装回map中
			result.put("error", 0);
			result.put("url", url);
		} catch (Exception e) {
			result.put("error", 1);
			result.put("message", "上传失败:"+e.getMessage());
		}
		return JsonUtils.objectToJson(result);
	}
	
}
