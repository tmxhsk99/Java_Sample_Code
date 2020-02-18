/**
 * @brief
 * @Detail
 */
package myjava.sample.thread.broadcast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


/**
 * @author juhyeon
 * @biref 
 * @details 
 * @date 
 * @version
 * 
 */
public class BroadResponseThread extends Thread{
	private Socket socket;
	private InputStream in;
	private InputStreamReader isr;
	private OutputStreamWriter osw;
	private OutputStream out;
	private BufferedReader br;
	private PrintWriter pw;
	private BroadCastServer broadCastServer;
	/**
	 * @throws IOException 
	 * @brief
	 * @Detail
	 */
	public BroadResponseThread(Socket socket,BroadCastServer broadCastServer) throws IOException {
		this.socket = socket;
		this.broadCastServer = broadCastServer;
		System.out.println(socket.getInetAddress());
		System.out.println(socket.getLocalAddress());
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
	
	/**
	 * @brief
	 * @details
	 */
	public void sendMessage(String msg) {
		System.out.println("send : "+ msg);
		pw.println(msg);
		//pw.flush();
	}
	
	/**
	 * @brief
	 * @Detail
	 */	
	@Override
	public void run() {
		String msg = null;
		
		try {
			while(((msg=br.readLine())!=null)){
				System.out.println("BoroadCasting");
				broadCastServer.broadCasting(msg);
			}
		} catch (IOException e) {
			System.out.println(socket.getInetAddress()+":"+socket.getPort()+"연결 종료");
			broadCastServer.remove(this);
		}finally {
			pw.close();
		}
	}
	
	
	
}
