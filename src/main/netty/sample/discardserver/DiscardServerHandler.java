/**
 * @brief
 * @Detail
 */
package netty.sample.discardserver;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author juhyeon
 * @biref 
 * @details 
 * @date 
 * @version
 * 
 */
public class DiscardServerHandler extends SimpleChannelInboundHandler<Object>{

	/* (non-Javadoc)
	 * @see io.netty.channel.SimpleChannelInboundHandler#channelRead0(io.netty.channel.ChannelHandlerContext, java.lang.Object)
	 */
	@Override
	protected void channelRead0(ChannelHandlerContext arg0, Object arg1) throws Exception {
		//아무것도 하지않음 
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
	

}
