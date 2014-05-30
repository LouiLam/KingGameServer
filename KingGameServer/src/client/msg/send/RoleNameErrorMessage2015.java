package client.msg.send;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

/**
 * 角色名错误
 * @author Administrator
 *
 */
public class RoleNameErrorMessage2015 extends SocketMessageToSend{

	public RoleNameErrorMessage2015(){
	}
	
	@Override
	public ChannelBuffer pack() {
		ChannelBuffer cb = ChannelBuffers.dynamicBuffer();
		cb.writeShort(2015);
		return cb;
	}

}
