import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import com.taojiang.AppController;
import com.taojiang.utils.FolderUtils;

public class AppMain {
	public static AppMain global_application;
	public static void main(String[] args) throws IOException {
		global_application = new AppMain();
	}
	
	private AppController appController;
	AppMain() throws IOException {
		appController = new AppController();
		//获取cmd中的命令输入
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		while(true) {
			System.out.println("please enter video url:");
			//读取一行命令
			String cmd = reader.readLine();
			if(appController.analysing(cmd)) {
				appController.reNames();
				System.out.println("rename succeed!");
			} else if("exit".equals(cmd)) {
				System.out.println("exit succeed!");
				return; //推出程序
			}  else {
				System.out.println("enter path error!");
			}
		}
	}
}
