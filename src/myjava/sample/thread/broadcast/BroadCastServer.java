/**
 * @brief
 * @Detail
 */
package myjava.sample.thread.broadcast;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author juhyeon
 * @biref 
 * @details 
 * @date 
 * @version
 * 
 */
public class BroadCastServer {
	private List<BroadResponseThread> userthread =  Collections.synchronizedList(new ArrayList<BroadResponseThread>());	
	
	public BroadCastServer() {
		userthread.clear();
	}

	public BroadCastServer(BroadResponseThread thread) {
		userthread.add(thread);		
	}

	//리스트에 있는 모든 소켓에 메시지 보내기 
	public synchronized void broadCasting (String msg){
		System.out.println("접속한 유저 수"+userthread.size());
		for(int i =0 ; i< userthread.size();i++){
			BroadResponseThread thread = userthread.get(i);
			thread.sendMessage(msg);
		}
	}

	public static void main(String[] args) throws IOException {
		BroadCastServer server = new BroadCastServer();
		server.start();
		
	}

	ServerSocket serverSocket;
	int port = 9898;
	
	/**
	 * @brief 
	 * @details
	 */
	private void start() {
		try {
			serverSocket = new ServerSocket(port);
			serverSocket.setReuseAddress(true);
			System.out.println("ServerSocketReady");
			while(true){
				Socket socket = serverSocket.accept();
				BroadResponseThread broadResponseThread = new BroadResponseThread(socket,this);
				add(broadResponseThread);
				broadResponseThread.start();				
			}
		} catch (IOException e) {			
			e.printStackTrace();
		}finally{
			try {
				serverSocket.close();
			} catch (IOException e2) {
				
			}
		}
	}



	/**
	 * @brief
	 * @details
	 */
	synchronized void add(BroadResponseThread broadResponseThread) {
		userthread.add(broadResponseThread);	
	}
	
	/**
	 * @brief
	 * @details 
	 */
	synchronized void remove(BroadResponseThread broadResponseThread) {
		userthread.remove(broadResponseThread);		
	}
	

}
