/**
 * @brief
 * @Detail
 */
package netty.sample.discardserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author juhyeon
 * @biref 클라이언트로 부터 테이터만 받는다 어떤 데이터도 보내지 않는다. 
 * @details 
 * @date 
 * @version
 * 
 */
public class DiscardServer {
	public static void main(String[] args) throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup,workerGroup)
			.channel(NioServerSocketChannel.class)
			.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ChannelPipeline p = ch.pipeline();
					p.addLast(new DiscardServerHandler());
					//접속된 클라이언트로 부터 수신된 데이터를 처리할 핸들러를 지정한다.
					//8888번 포트에 클라이언트 접속을 허용하고 클라이언트로 부터 받은 데이터를 
					//DiscardServerHandler 클래스가 처리하도혹 지정 
					//실제 처리하는 내용은 DiscardServerHandler의 구현부분을 참조 
					
				}				
			});
			
			ChannelFuture f = b.bind(9999).sync();//부트스트랩 클래스의 bind 메서드로 접속할 포트를 지정한다. 
			
			f.channel().closeFuture().sync();
		}finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}
}
