/**
 * @brief ChannelInboundHandler 인터페이스의 구현 실제 서버의 이벤트를 구현한다. 
 * @Detail
 */
package netty.sample.echoserver;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;

/**
 * @author juhyeon
 * @biref 
 * @details 
 * @date 
 * @version
 * 
 */
public class EchoServerHandler implements ChannelInboundHandler {

	/* (non-Javadoc)
	 * @see io.netty.channel.ChannelHandler#handlerAdded(io.netty.channel.ChannelHandlerContext)
	 */
	@Override
	public void handlerAdded(ChannelHandlerContext arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see io.netty.channel.ChannelHandler#handlerRemoved(io.netty.channel.ChannelHandlerContext)
	 */
	@Override
	public void handlerRemoved(ChannelHandlerContext arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see io.netty.channel.ChannelInboundHandler#channelActive(io.netty.channel.ChannelHandlerContext)
	 */
	@Override
	public void channelActive(ChannelHandlerContext arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see io.netty.channel.ChannelInboundHandler#channelInactive(io.netty.channel.ChannelHandlerContext)
	 */
	@Override
	public void channelInactive(ChannelHandlerContext arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see io.netty.channel.ChannelInboundHandler#channelRead(io.netty.channel.ChannelHandlerContext, java.lang.Object)
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		String readMessage = ((ByteBuf)msg).toString(Charset.defaultCharset());
		System.out.println("수신한 문자열 ["+readMessage+"]");
		ctx.write(msg);
		
	}

	/* (non-Javadoc)
	 * @see io.netty.channel.ChannelInboundHandler#channelReadComplete(io.netty.channel.ChannelHandlerContext)
	 */
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();		
	}

	/* (non-Javadoc)
	 * @see io.netty.channel.ChannelInboundHandler#channelRegistered(io.netty.channel.ChannelHandlerContext)
	 */
	@Override
	public void channelRegistered(ChannelHandlerContext arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see io.netty.channel.ChannelInboundHandler#channelUnregistered(io.netty.channel.ChannelHandlerContext)
	 */
	@Override
	public void channelUnregistered(ChannelHandlerContext arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see io.netty.channel.ChannelInboundHandler#channelWritabilityChanged(io.netty.channel.ChannelHandlerContext)
	 */
	@Override
	public void channelWritabilityChanged(ChannelHandlerContext arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see io.netty.channel.ChannelInboundHandler#exceptionCaught(io.netty.channel.ChannelHandlerContext, java.lang.Throwable)
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext arg0, Throwable arg1) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see io.netty.channel.ChannelInboundHandler#userEventTriggered(io.netty.channel.ChannelHandlerContext, java.lang.Object)
	 */
	@Override
	public void userEventTriggered(ChannelHandlerContext arg0, Object arg1) throws Exception {
		// TODO Auto-generated method stub
		
	}

	

}
