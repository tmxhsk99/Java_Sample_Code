/**
 * @brief
 * @Detail
 */
package netty.sample.HttpHelooWorldServer;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;

/**
 * @author juhyeon
 * @biref 
 * @details 
 * @date 
 * @version
 * 
 */
public class HttpHelloWordServerInitializer extends ChannelInitializer<SocketChannel>{

	private final SslContext sslCtx;	
	/**
	 * @brief
	 * @Detail
	 */
	public HttpHelloWordServerInitializer(SslContext sslCtx) {
		this.sslCtx = sslCtx;
	}
	
	/* (non-Javadoc)
	 * @see io.netty.channel.ChannelInitializer#initChannel(io.netty.channel.Channel)
	 */
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline p = ch.pipeline();
		if(sslCtx != null){
			p.addLast(sslCtx.newHandler(ch.alloc()));
		}
		p.addLast(new HttpServerCodec());//네티가 제공하는 HTTP 서버코덱, 
		//인바운드 아웃바운드 모두 구현한다.ByteBuf 객체를 HttpRequest 와 HttpContent 객체로 변환하고  HttpResponse 객체를 ByteBuf 로 인코딩하여 송신
		p.addLast(new HttpHelloWorldServerHandler());
		//HttpServerCodec이 수신한 이벤트와 데이터를 처리하여 Http 객체로 변환한다음 
		//channelRead 이벤트를 HttpHelloWorldServerHandler 클래스로 전달한다.
		//수신된 Http  데이터에 대한 처리를 수행하는 데이터 핸들러를 살펴보자
	}

}
