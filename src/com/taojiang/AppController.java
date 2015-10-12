package com.taojiang;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.taojiang.utils.FolderUtils;

public class AppController {
	
	private List<FolderVO> analysis_list;
	
	public AppController() {
		super();
	}
	
	//分析文件夹
	public Boolean analysing(String root_path) {
		File root_file = new File(root_path);
		if(root_file.exists()) {
			analysis_list = new ArrayList<FolderVO>();
			File[] file_elements = root_file.listFiles();
			File element;
			for(int i = 0; i != file_elements.length; i++) {
				element = file_elements[i];
				FolderVO fv = new FolderVO();
				fv.video_root_folder = element.getPath();
				
				System.out.println("analysis in :");
				System.out.println(fv.video_root_folder);
				
				analysizeElement(element,fv);
			}
			return !analysis_list.isEmpty();
		}
		return false;
	}
	
	//分析主目录中的一级目录
	private Boolean analysizeElement(File elementFile,FolderVO fv) {
		if(elementFile == null) return false;
		if(!elementFile.isDirectory()) return false;
		
		File[] childrens = elementFile.listFiles();
		for(File fi:childrens) {
			if(fi.isDirectory() && fi.getName().indexOf("视频") != -1) {
				fv.video_folder = fi.getPath();
				File[] videoFiles = fi.listFiles();
				for(File video:videoFiles) {
					String filePath = video.getPath();
					if(FolderUtils.isVideoFile(filePath)) {
						if(!analysis_list.contains(fv)) analysis_list.add(fv);
						if(fv.avi_names == null) fv.avi_names = new ArrayList<String>();
						fv.avi_names.add(video.getName().substring(video.getName().lastIndexOf("_") + 1, video.getName().indexOf(".")));
						System.out.println(video.getName());
					}
				}
			} else if(fi.isDirectory()) {
				analysizeElement(fi,fv);
			}
		}
		return false;
	}
	
	//重命名目录文件
	public void reNames() {
		if(analysis_list != null && !analysis_list.isEmpty()) {
			for(FolderVO fv:analysis_list) {
				File root_file = new File(fv.video_root_folder);
				if(root_file.exists()) {
					if(root_file.isDirectory()) {
						Iterator<String> it = fv.avi_names.iterator();
						
						Pattern pt = Pattern.compile("day(\\d+)");
						Matcher m = pt.matcher(root_file.getName());
						String prefix = "";
						if(m.find()) {
							prefix = m.group();
						}
						
						String rename = prefix;
						while(it.hasNext()) {
							rename += "_" + it.next();
						}
						
						if(root_file.getName() != rename) {
							File new_folder = new File(root_file.getParent() +  "/" + rename);
							root_file.renameTo(new_folder);
						}
					}
				}
			}
		}
	}
}
