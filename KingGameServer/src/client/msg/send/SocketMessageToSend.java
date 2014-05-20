package client.msg.send;

import org.jboss.netty.buffer.ChannelBuffer;

public abstract class SocketMessageToSend {
	
	protected short msgType;
	
	public abstract ChannelBuffer pack();
	
}
