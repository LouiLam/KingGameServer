package client.msg.received;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;

import client.msg.send.SizeofResultMessage2016;

public class SizeofMessageReceived1008 extends SocketMessageReceived {

	@Override
	public void parse(ChannelBuffer buffer) {
		
	}

	@Override
	public void logicHandle(ChannelBuffer buffer, Channel channel) {
		
		channel.write(new SizeofResultMessage2016().pack());
		
	}
}
