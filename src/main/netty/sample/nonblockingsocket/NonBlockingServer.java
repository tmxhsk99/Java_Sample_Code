/**
 * @brief
 * @Detail
 */
package netty.sample.nonblockingsocket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author juhyeon
 * @biref 논블로킹 서버
 * @details 
 * 논블로킹 소켓은 구조적을 소켓으로 부터 읽은 데이터를 바로 소켓에 쓸 수가 없다. 
 * 이를 위해서 각 이벤트가 공유한는 데이터 객체를 생성하고 그객체를 통해서 각 소켓 채널로 데이터를 전송한다.  
 * @date 
 * @version
 * 
 */
public class NonBlockingServer {
	private Map<SocketChannel,List<byte[]>> keepDataTrack = new HashMap<>();
	private ByteBuffer buffer = ByteBuffer.allocate(2*1024);
	
	private void startEchoServer(){
		try (//1.자바 1.7에서 등장한 기능 , 소괄호에서 선언된자원을 자동으로 해제해준다 . 1.6이하에서는 finnaly 구문에서 해제 필요 
			Selector selector = Selector.open();
				//2.자바NIO 컴포넌트 중의 하나인 Selector는 자신에게 등록된 채널에 변경 사항이 발생했는지 검사하고 변경 사항이 발생한 채널에 대한 접근을 가능하게 해준다.
				//여기서는 새로운 selector 객체를 생성
			ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()
					//3. 블로킹소켓의 ServerSocket에 대응되는 논블로킹 소켓의 서버소켓 채널을 생성한다. 
					//블로킹 소켓과 다르게 소켓 채널을 먼저 생성하고 사용할 포트를 바인드 한다.
			){
				if((serverSocketChannel.isOpen()) && (selector.isOpen())){//4. 생성한 Selector와 ServerSocketChannel 객체가 정상적으로 생성되었는지 확인 
					serverSocketChannel.configureBlocking(false);//5. 소켓 채널의 블로킹 모드의 기본값은 true, 즉 , 별도로 논블로킹 모드로 지정하지 않으면 블로킹 모드로 동작함 
					serverSocketChannel.bind(new InetSocketAddress(9898));
					//6. 클라이언트의 연결을 대기할 포트를 지정하고 생성된 ServerSocketChannel 객체에 할당한다.
					//이 작업이 완료되면  ServerSocketChannel 객체가 지정된 포트로 부터 클라이언트의 연결을 생성할 수 있다. 
					
					serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);
					//7. ServerSocketChannel 객체를 Selector 객체에 등록한다. Selector가 감지할 이벤트는 연결요청에 해당하는 SelectionKey.OP_ACCEPT다. 
					System.out.println("접속 대기중");
					
					while(true) {
						selector.select(); 
						//8.Selector에 등록된 채널에서 변경사항이 발생했는지 검사한다.
						//Selctor에 아무런 I/O 이벤트도 발생하지 않으면 스레드는 이부분에서 블로킹된다.  
						//I/O 이벤트가 발생하지 않았을 때 블로킹을 피하고 싶으면 
						//selectNow 메서드를 사용하면 된다. 
						Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
						//9. Selector에 등록된 채널중에서 I/O 이벤트가 발생한 채널들의 목록을 조회한다.  selectedKeys()가 준비된 채널을 반환 
						
						while (keys.hasNext()){
							SelectionKey key = (SelectionKey) keys.next();
							keys.remove();//10. I/O  이벤트가 발생한 채널에서 동일한 이벤트가 감지되는 것을 방지하기위해 조회된 목록에서 제거한다. 
							
							if(!key.isValid()){
								//isValid() 메소드는 SelectionKey가 유효한지 아닌지를 질의하는 메소드입니다. SelectionKey가 cancel() 메소드 호출로 삭제되든지,
								//채널이 닫히든지 또는 Selector가 닫히면 유효한 키가 아니라는 의미의 false가 리턴됩니다.
								continue;
							}
							
							if(key.isAcceptable()){//11. 조회된 I/O 이벤트 종류가 연결 요청인지 확인한다. 만약 연결 요청 이벤트라면 연결처리 메서드로 이동한다. 
								this.acceptOP(key,selector);
							}else if(key.isReadable()){//12. 조회된 I/O 이벤트 종류가 데이터 수신인지 확인한다. 만약 데이터 수신 이벤트라면 데이터 읽기 처리 메서드로 이동한다. 
								this.readOP(key);
							}else if (key.isWritable()){//13. 조회된 I/O 이벤트 종류가 데이터 쓰기가능인지 확인한다. 만약 데이터 쓰기가능 이벤트라면 데이터 쓰기 처리 메서드로 이동한다.
								this.writeOP(key);
							}
						}
					}
				}else{
					System.out.println("서버 소켓을 생성하지 못했습니다.");
				}
			} catch (Exception e) {
				System.err.println(e);
			}
	}

	/**
	 * @throws IOException 
	 * @brief
	 * @details
	 */
	private void acceptOP(SelectionKey key, Selector selector) throws IOException {
		ServerSocketChannel serverChannel = (ServerSocketChannel)key.channel();
		//14. 연결 요청 이벤트가 발생한 채널은 항상 ServerSocketChannel이므로 이벤트가 발생한 채널을 ServerSocketChannel로 캐스팅한다. 해당SelectionKey에서 channel()로 키에 대한 ServerSocketChanal을 가져옴
		SocketChannel socketChannel = serverChannel.accept();
		//15. ServerSocketChannel을 사용하여 클라이언트 연결을 수락하고 연결된 소켓 채널을 가져온다. 
		socketChannel.configureBlocking(false);
		//16. 연결된 클라이언트 소켓 채널을 논블로킹 모드로 설정한다. 
		
		System.out.println("클라이언트 연결됨 : "+ socketChannel.getRemoteAddress());
		
		keepDataTrack.put(socketChannel, new ArrayList<byte[]>());//연결시 해당 연결된 클라이언트 정보를 등록함 
		socketChannel.register(selector, SelectionKey.OP_READ);//17. 클라이언트 소켓 채널을 Selector에 OP_Read 타입으로 등록 등록하여 I/O 이벤트를 감시한다.
		
		
	}

	/**
	 * @brief
	 * @details
	 */
	private void readOP(SelectionKey key) {
		
		try {
			SocketChannel socketChannel = (SocketChannel)key.channel();
			buffer.clear();
			int numRead = -1;
			
			try {
				numRead = socketChannel.read(buffer);
			} catch (IOException e) {
				System.err.println("데이터 읽기 에러!");
			}
			
			if(numRead == -1){
				this.keepDataTrack.remove(socketChannel);
				System.out.println("클라이언트 연결 종료 " + socketChannel.getRemoteAddress());
				socketChannel.close();
				key.cancel();
				return;
			}
			byte[] data = new byte[numRead];
			System.arraycopy(buffer.array(), 0,data, 0, numRead);
			System.out.println(new String(data,"UTF-8") + "from" + socketChannel.getRemoteAddress());
			
			doEchoJob(key,data);
		} catch (IOException ex) {
			System.err.println(ex);
		}
		
		
	}
	
	/**
	 * @throws IOException 
	 * @brief
	 * @details
	 */
	private void writeOP(SelectionKey key) throws IOException {
		SocketChannel socketChannel = (SocketChannel) key.channel();
		
		List<byte[]> channelData = keepDataTrack.get(socketChannel);
		Iterator<byte[]> its = channelData.iterator();
		while(its.hasNext()){
			byte[] it = its.next();
			its.remove();
			socketChannel.write(ByteBuffer.wrap(it));
		}
		
		key.interestOps(SelectionKey.OP_READ);//해당 키의 작업 유형을 OP_Write로 변경
	}
	
	/**
	 * @brief
	 * @details
	 */
	private void doEchoJob(SelectionKey key, byte[] data) {
		SocketChannel socketChannel = (SocketChannel) key.channel();
		List<byte[]> channelData = keepDataTrack.get(socketChannel);
		channelData.add(data);
		key.interestOps(SelectionKey.OP_WRITE);//해당 키의 작업 유형을 OP_Write로 변경
	}


	public static void main(String[] args) {
		NonBlockingServer main = new NonBlockingServer();
		main.startEchoServer();
	}
	
}
