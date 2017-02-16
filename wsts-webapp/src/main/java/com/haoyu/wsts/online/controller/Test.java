package com.haoyu.wsts.online.controller;

import java.io.File;

public class Test {

	public static void main(String[] args) throws Exception {
		// 递归显示C盘下所有文件夹及其中文件
		File root = new File("f:/中学");
		showAllFiles(root);
	}

	final static void showAllFiles(File dir) throws Exception {
		File[] fs = dir.listFiles();
		for (int i = 0; i < fs.length; i++) {
			if (fs[i].isDirectory()) {
				try {
					showAllFiles(fs[i]);
				} catch (Exception e) {
					
				}
			}else{
				System.out.println(fs[i].getAbsolutePath());
			}
		}
	}

}
