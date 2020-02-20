/**
 * @brief
 * @Detail
 */
package myjava.sample.thread.multi.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import myjava.sample.thread.broadcast.BroadCastServer;

/**
 * @author juhyeon
 * @biref 
 * @details 
 * @date 
 * @version
 * 
 */
public class MultiThread extends Thread{
	private Socket socket; 
	private InputStream in;
	private InputStreamReader isr;
	private OutputStreamWriter osw;
	private OutputStream out;
	private BufferedReader br;
	private PrintWriter pw;
	private MultiServer mutiServer;		
	
	/**
	 * @brief
	 * @Detail
	 */
	public MultiThread(Socket socket, MultiServer multiServer) {
		this.socket = socket; 
		this.mutiServer = multiServer;
		System.out.println(socket.getInetAddress());
		System.out.println(socket.getPort());		
		
		try {
			//소켓에서 메시지를 가져옴
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			//소켓에 메시지를 보냄 
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public synchronized void run(){
		String msg = null;
		try {	
			while(((msg=br.readLine())!=null)){
				//int roof =Integer.parseInt(msg);
				
				mutiServer.sendCount++;
				System.out.println( socket.getPort()+ ":"+ mutiServer.sendCount + ":"+msg);			
				mutiServer.loging(msg,socket,this);
			   
			}						
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(socket.getInetAddress()+":"+socket.getPort()+"연결 종료");
		}
	}



	/**
	 * @brief
	 * @details
	 */
	public void sendMessage(String msg) {//서버의 처리결과를 입력받는다.
		pw.println(msg);
	}
	
}
