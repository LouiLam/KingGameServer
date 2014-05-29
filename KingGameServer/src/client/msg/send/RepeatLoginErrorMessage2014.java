package client.msg.send;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

/**
 * 重复登录，发送给先登录的玩家
 * @author Administrator
 *
 */
public class RepeatLoginErrorMessage2014 extends SocketMessageToSend{

	public RepeatLoginErrorMessage2014(){
	}
	
	@Override
	public ChannelBuffer pack() {
		ChannelBuffer cb = ChannelBuffers.dynamicBuffer();
		cb.writeShort(2014);
		return cb;
	}

}
