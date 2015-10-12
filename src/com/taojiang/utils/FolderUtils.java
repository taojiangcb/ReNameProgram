package com.taojiang.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FolderUtils {
	public static Boolean isVideoFile(String fileName) {
		String patteren = "(rm)|(rmvb)|(mkvi)|(mp4)|(avi)";
		Pattern p = Pattern.compile(patteren);
		Matcher m = p.matcher(fileName);
		return m.find();
	}
}
