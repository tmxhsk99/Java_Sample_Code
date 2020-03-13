/**
 * @brief
 * @Detail
 */
package netty.sample.HttpHelooWorldServer;


import java.security.cert.CertificateException;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

/**
 * @author juhyeon
 * @biref 
 * @details 
 * @date 
 * @version
 * 
 */
public final class HttpHelloWorldServer {
	static final boolean SSL = System.getProperty("ssl") != null;//1. HttpHelloWorldServer의 SSL 지원 여부를 설정한다. 웹서버를 실행할때 -D 옵션을 설정가능하다.
	// java io.netty.example.http.helloworld.HttpHelloWorldServer -Dssl=true
	static final int PORT = Integer.parseInt(System.getProperty("port",SSL ? "8443" : "8080"));
		
	public static void main(String[] args) throws Exception {
		//Confiugre SSL.		
		final SslContext sslCtx;
		if(SSL){
			SelfSignedCertificate ssc = new SelfSignedCertificate();
			//sslCtx = SslContext.newServerContext(ssc.certificate(),ssc.privateKey());
			sslCtx = SslContextBuilder.forServer(ssc.certificate(),ssc.privateKey()).build();
		}else{
			sslCtx = null;
		}
		
		//configure the server 
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.option(ChannelOption.SO_BACKLOG, 1024);
			b.group(bossGroup,workerGroup)
				.channel(NioServerSocketChannel.class)
				.handler(new LoggingHandler(LogLevel.INFO))
				.childHandler(new HttpHelloWordServerInitializer(sslCtx));//2. 채널파이프라인을 설정한다. 
			
			Channel ch = b.bind(PORT).sync().channel();
			
			System.err.println("Open your web browser and navigate to " + (SSL ? "https" : "http") + "://127.0.0.1:" + PORT + "/");
			ch.closeFuture().sync();
		} catch (Exception e) {

		} finally{
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}
