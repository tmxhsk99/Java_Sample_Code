/**
 * @brief
 * @Detail
 */
package myjava.sample.thread.multi.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.xml.crypto.Data;

import myjava.sample.thread.broadcast.BroadCastServer;
import myjava.sample.thread.broadcast.BroadResponseThread;

/**
 * @author juhyeon
 * @biref 
 * @details 
 * @date 
 * @version
 * 
 */
public class MultiServer {
	//접속하는 클라이언트를 담을 List 
	public static int sendCount = 0;
	ServerSocket serverSocket;
	int port = 9898;
	
	public MultiServer() {
	}

	private void start(){
		try {
			serverSocket = new ServerSocket(port);
			serverSocket.setReuseAddress(true);
			System.out.println("ServerSocketReady!");
			
			while(true){
				Socket socket = serverSocket.accept();
				MultiThread multiThread = new MultiThread(socket,this);//유저에게 소켓정보와, 서버소켓을 전달
				multiThread.start();
			}	
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * @brief
	 * @details
	 */

	//로그쌓기 
	public void loging (String msg,Socket socket,MultiThread mt){//sync		
		//메시지에 날짜 및 아이피 포트를 추가한다. 
		String logMessage = makeLogMessage(socket, msg);		
		//log에 쓰는 작업 		
		//queue에 넣는다. 				
		LogQueue l1 =LogQueue.getInstance();
		l1.add(logMessage);
		l1.run();	
		
	}

	/**
	 * @brief
	 * @details
	 */
	private String makeLogMessage(Socket socket, String msg) {
		StringBuffer sb = new StringBuffer();
		Date date = new Date();		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String logtime = sdf.format(date);
		sb.append(logtime+" ");
		sb.append("["+socket.getInetAddress()+":"+String.valueOf(socket.getPort())+"] ");
		sb.append(msg);		
		return sb.toString();
	}

	public static void main(String[] args) {
		MultiServer server = new MultiServer();
		server.start();
	}

	
	
	
}
