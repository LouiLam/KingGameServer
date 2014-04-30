package client.msg.received;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;

public abstract class SocketMessageReceived {
	
	
	public void handle(ChannelBuffer buffer,Channel channel){
		parse(buffer);
		logicHandle(buffer,channel);
	}
	public abstract void parse(ChannelBuffer buffer);
	public abstract void logicHandle(ChannelBuffer buffer,Channel channel);

}
