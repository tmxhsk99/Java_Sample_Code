/**
 * @brief
 * @Detail
 */
package myjava.sample.thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * @author juhyeon
 * @biref 
 * @details 
 * @date 
 * @version
 * 
 */
public class ThreadServer {
	
	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(9898);
		Socket socket;
		System.out.println("접속대기 중 ...");
		while (true) {
			socket = server.accept();
			System.out.println("클라이언트 연결됨");
			System.out.println("=============접속정보==============");
			System.out.println(socket+"\n"+socket.getPort()+"\n");
			System.out.println("===================================");
			
			Response res = new Response(socket);
			Thread t1 = new Thread(res);
			t1.start();
		}
	}	
}
