package com.haoyu.wsts.utils;

public class FileSizeUtils {

	public static String getFileSize(Long byteSize) {
		String[] units = { "B", "K", "M", "G", "T" };
		int index = 0;
		while (byteSize >= 1024) {
			if(index>=units.length-1){
				break;
			}
			byteSize = byteSize/1024;
			index++;
		}
		return byteSize+units[index];
	}

}
