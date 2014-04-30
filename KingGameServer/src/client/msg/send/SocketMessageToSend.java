package client.msg.send;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;

public abstract class SocketMessageToSend {
	
	protected short msgType;
	
	public abstract ChannelBuffer pack();
	
}
