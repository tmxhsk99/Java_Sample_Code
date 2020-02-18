/**
 * @brief
 * @Detail
 */
package myjava.sample.thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


/**
 * @author juhyeon
 * @biref 간단한 블로킹서버 클라이언트가 요청할때마다 새로운 스레드가 생성된다. 
 * @details 
 * @date 
 * @version
 * 
 */
public class ThreadServer {
	static Map<String, Socket> socketMap = new HashMap<String, Socket>();
	
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
