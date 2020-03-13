/**
 * @brief
 * @Detail
 */
package netty.sample.ServerBootstrap;



import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author juhyeon
 * @biref 논블로킹 입출력 모드를 지원하는 ServerBootstrap 초기화 
 * @details 
 * @date 
 * @version
 * 
 */
public class EchoServer {
	public static void main(String[] args) throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);// 1. 부모스레드는 단일스레드로 동작한다. 
		EventLoopGroup workerGroup = new NioEventLoopGroup();// 2. 인수가 없는 생성자느 사용할 스레드 수를 서버 애플리케이션이 동작하는 하드웨어 코어 수를 기준으로 결정한다. 
		try {												// 스레드 수는 하드웨어가 가지고 있는  CPU 코어 수의 2배를 사용한다. 
			ServerBootstrap b = new ServerBootstrap(); //3. 인수없는 생성자로 객체생성
			b.group(bossGroup,workerGroup) //4.설정을 위한 메서드 체이닝 ,서버 애플리케이션이 사용할 두 스레드 그룹 
			//설정 첫번째는 클라이언트를 연결을 수락하는 부모스레드그룹, 
			//연결된 클라이언트 소켓으로 부터 데이터 입출력및 이벤트처리를 담당하는 자식스레드 그룹
			.channel(NioServerSocketChannel.class) //5. 서버소켓(부모 스레드)이 사용할 네트워크 입출력 모드를 설정한다. NIO 모드로 동작.
			.option(ChannelOption.SO_BACKLOG, 1)//동시에 수용가능한 소켓 연결 요청수
			.handler(new LoggingHandler(LogLevel.INFO))
			.childHandler(new ChannelInitializer<SocketChannel>(){ //6. 자식 채널의 초기화 방법을 설정한다. 여기서는 익명 클래스로 채널 초기화 방법 지정 
				@Override
				public void initChannel(SocketChannel ch) { //7. ChannelInitalizer 클라이언트로 부터 연결된 채널이 초기화 될때 기본 동작이 지정된 추상클래스
					ChannelPipeline p = ch.pipeline(); // 8. 패널 파이프 라인 객체를 생성 
					p.addLast(new LoggingHandler(LogLevel.INFO));
					p.addLast(new EchoServerHandler()); // 9. 채널 파이프라인에 EchoServerHandler 클래스를 등록한다.
					//EchoServerHandlier 클래스는 이후에 클라이언트의 연결이 생성되었을때 데이터를 처리를 담당한다.
				}
			});
			ChannelFuture f = b.bind(9898).sync();
		} catch (Exception e) {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}
}	
