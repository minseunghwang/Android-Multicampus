package web.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ImagePrediction {
	public String ms(String img) throws IOException, InterruptedException {
		Runtime rt = Runtime.getRuntime();
		Process pc = null;
		String s = null;
		String result= null;
		
		try {
			String python = "C:\\ProgramData\\Anaconda3\\envs\\cpu_env3\\python.exe";
			String path = " C:\\python_ML\\bb.py ";
			String val = img;
		
			
			
			String cmd = python + path + img;
			pc = rt.exec(cmd);
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(pc.getInputStream())) ;
			BufferedReader stdError = new BufferedReader(new InputStreamReader(pc.getErrorStream())) ;
			
			System.out.println("python should be run");
			while((s = stdInput.readLine()) != null) {
				result = s;
			}
		}catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}finally {
			pc.waitFor();
			pc.destroy();
		}
		return result;
	}
}
