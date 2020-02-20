/**
 * @brief
 * @Detail
 */
package myjava.sample.thread.multi.server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author juhyeon
 * @biref 
 * @details 
 * @date 
 * @version
 * 
 */
public class LogQueue implements Runnable{
	static Queue<String> que = new LinkedList<String>();
	public static final String rootDrectory ="./log/";
	private static LogQueue logQueue = new LogQueue();
	BufferedWriter bw = null;
	PrintWriter pw = null;
	public synchronized static LogQueue getInstance()  {		
		return logQueue;
	}
	
	private LogQueue() {
		System.out.println("Logqueue Instance Create...");
	}
	
	public synchronized void add(String str){
		que.add(str);
	}
	
	public synchronized String pop(){
		return que.poll();
	}
	
	private synchronized String makeFileName(){
		Date date = new Date(); 		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");	
		String dayformat = sdf.format(date);
		StringBuffer sb = new StringBuffer(dayformat);
		sb.append("] UserLogFile.txt");
		return sb.toString();
	}
	@Override
	public synchronized void run() {
		String fileName = makeFileName();
		File file = new File(rootDrectory + fileName);
		try {
			file.createNewFile();
			bw = new BufferedWriter(new FileWriter(file,true));
			pw = new PrintWriter(bw,true);
			
			
			String log = this.pop();								
			//true를 해주지않으면 덮어쓰기 된다.
			//생성된 파일 뒤에 이어서 쓴다.
			pw.write(log+"\n");	
			pw.flush();
			
			} catch (IOException e) {
				e.printStackTrace();
				
			}
		
	}

}
