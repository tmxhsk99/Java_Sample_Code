/**
 * @brief
 * @Detail
 */
package netty.sample.echoserver;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author juhyeon
 * @biref 문자열 전송후 응답수신 수신한 데이터를 화면에 출력하고 연결된 소켓을 종료한다. 
 * @details 
 * @date 
 * @version
 * 
 */
public class EchoClient {
	public static void main(String[] args) throws InterruptedException {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group)//1.서버와 다르게 이벤트 루픅그룹이 하나만 설정 클라이언트 애플리케이션은 서버와달리 체널이 하나만 존재하기 때문에 이벤트 루프그룹이 하나다.
			.channel(NioSocketChannel.class)//2.클라이언트 애플리케이션이 생성하는 채널 종류는 생성한다. 서버와 연결된 소켓체널은 NIO로 동작하게 된다.
			.handler(new ChannelInitializer<SocketChannel>() {//3.클라이언트 애플리케이션이므로 채널 파이프라인 설정에 일반 소켓 채널 클래스인  SocketChannel 을 설정한다. 
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ChannelPipeline p = ch.pipeline();
					p.addLast(new EchoClientHandler());
				}
			});
			ChannelFuture f = b.connect("localhost",9999).sync();//4.비동키 입출력 메서드인  connect 를 호출한다. 
			//connect 메서드 호출 결과로 ChannelFuture 객체를 돌려주는데 이 객체를 통해서 비동기 메서드 처리결과를 확인 할 수 있다. 
			//ChannelFuture 객체의 sync  메서드는  ChannelFuture 객체의 요청이 완료 될 때까지 대기하낟.
			// 단 요청이 실패하면 예외를 던진다. connect메서드 처리가 완료될때까지 다음 라인으로 진행하지 않는다. 
			f.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully();
		}
		
	}
}
