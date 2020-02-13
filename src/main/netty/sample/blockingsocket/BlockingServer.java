/**
 * @brief
 * @Detail
 */
package netty.sample.blockingsocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author juhyeon
 * @biref 블로킹 소켓은 ServerSocket, Socket 클래스를 사용한다.
 * @details 
 * 1. 여러 클라이언트 가 접속시 accept()에서 병목현상이 일어난다. 대기시간이 길어짐
 * 2. 클라이언트 하나당 1개의 스레드이므로  여러명이 접속시 out of memory 현상이 일어난다. 
 * 3. 스레드 풀링을 사용하는 경우도 있다. 
 * 4. 힙을 허용하는 만큼 스레드 수를 늘릴경우 힙메모리의 할당량이 커지게됨 
 * 5. 가비지 컬렉션이 작동할때 스레드가 정지됨 애플리케이션이 먹통이 된것 처럼 보이게됨 
 * 6. 힙에 할당된 메모리가 크면 클수록 컬렉션이 수행도니느 회수는 줄지만 상대적으로 시간을 길어지게 된다. 
 * 7. 컨텍스트 스위칭 고찰 - 수 많은 스레드가 작동하므로 우선순위 결정하는 데 CPU를 많이 사용하게 된다. 
 * 8. 블로킹 소켓을 사용한 서버는 충분히 많은 동시 접속자를 수용하지 못한다. 
 * @date 
 * @version
 * 
 */
public class BlockingServer {
	public static void main(String[] args) throws IOException {
		BlockingServer server = new BlockingServer();
		server.run();
	}
	private void run() throws IOException {
		ServerSocket server = new ServerSocket(9888);
		System.out.println("접속 대기중");
		while(true){
			Socket sock = server.accept();
			System.out.println("클라이언트 연결됨");			
			OutputStream out = sock.getOutputStream();
			InputStream in = sock.getInputStream();
			while (true){
				try {
					int request = in.read();
					
					out.write(request);	
				} catch (Exception e) {
					break;
				}				
			}
		}
	}
}
