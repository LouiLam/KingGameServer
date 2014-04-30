package netty;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.timeout.IdleState;
import org.jboss.netty.handler.timeout.IdleStateAwareChannelHandler;
import org.jboss.netty.handler.timeout.IdleStateEvent;

public class HeartBeatHandler  extends IdleStateAwareChannelHandler{
	 
	 public void channelIdle(ChannelHandlerContext ctx, IdleStateEvent e)
	   throws Exception {
		 super.channelIdle(ctx, e);
		 if(e.getState() == IdleState.READER_IDLE){
			 e.getChannel().close();
		 }
	 }
}
