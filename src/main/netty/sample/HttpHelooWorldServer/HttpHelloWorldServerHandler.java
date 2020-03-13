/**
 * @brief
 * @Detail
 */
package netty.sample.HttpHelooWorldServer;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.AsciiString;

/**
 * @author juhyeon
 * @biref 
 * @details 
 * @date 
 * @version
 * 
 */
public class HttpHelloWorldServerHandler extends ChannelInboundHandlerAdapter {//1. HttpServerCodec으로 부터 수신된 이벤트를처리해야하므로 ChannelInboundHandler 상속받음
	
	private static final byte[] CONTENT = {'H','E','L','L','O',' ','W','O','R','L','D'};//2.웹브라우저 전송할 메시지를 상수로 선언한다.	
	private static final AsciiString CONTENT_TYPE = new AsciiString("Content-Type");
	private static final AsciiString CONTENT_LENGTH = new AsciiString("Content-Length");
	private static final AsciiString CONNECTION = new AsciiString("Connection");
	private static final AsciiString KEEP_ALIVE = new AsciiString("keep-alive");
	
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx){///3.웹브라우저로부터 데이터가 수신되었을뗴 채널 버퍼의 내용을 웹 브라우저로 전송한다.
		ctx.flush();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {//4.HttpServerCodec으로 부터 수신된 channelRead 이벤트를 처리하려면 ChannelRead 오버라이드한다. 
		if(msg instanceof HttpRequest){//5.수신된 객체 HttpRequest 일때 HttpResponse 객체를 생성하고 헤더와   메시지를 저장한다.
			HttpRequest req = (HttpRequest) msg;
			
			if(HttpHeaders.is100ContinueExpected(req)){
				ctx.write(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE));
			}
			boolean keepAlive = HttpHeaders.isKeepAlive(req);
			FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,Unpooled.wrappedBuffer(CONTENT));
			response.headers().set(CONTENT_TYPE,"text/plain");
			response.headers().set(CONTENT_LENGTH,response.content().readableBytes());
			
			if(!keepAlive){
				ctx.write(response).addListener(ChannelFutureListener.CLOSE);
			}else{
				response.headers().set(CONNECTION,KEEP_ALIVE);
				ctx.write(response);
			}
			
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		super.exceptionCaught(ctx, cause);
	}
	

}
