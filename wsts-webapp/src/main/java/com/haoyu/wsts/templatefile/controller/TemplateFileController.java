package com.haoyu.wsts.templatefile.controller;

import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.haoyu.sip.core.utils.PropertiesLoader;
import com.haoyu.sip.core.web.AbstractBaseController;

@Controller
@RequestMapping("**/template_file")
public class TemplateFileController extends AbstractBaseController{
	
	@Resource
	private PropertiesLoader propertiesLoader;
	
	@RequestMapping("download")
	public void downloadExcel(String fileName, HttpServletResponse response){
		String dir = propertiesLoader.getProperty("template.file.path");
		File file = new File(request.getServletContext().getRealPath("/")+dir+"/"+fileName);
		if (file != null) {
			try {
				response.setCharacterEncoding("GBK");
				response.setContentType("application/xls;charset=GBK");
				String outName = new String(fileName.getBytes("GBK"),"ISO-8859-1");
				response.setHeader("Content-disposition", "attachment; filename="+ outName);
				response.getOutputStream().write(FileUtils.readFileToByteArray(file));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
