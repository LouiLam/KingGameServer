package client.msg.received;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;

import utils.DateUtil;
import client.msg.send.HeartMessage2000;

public class HeartMessageReceived1000 extends SocketMessageReceived {

	@Override
	public void parse(ChannelBuffer buffer) {}

	@Override
	public void logicHandle(ChannelBuffer buffer, Channel channel) {
		
	System.out.println(DateUtil.getCurDate()+"收到客户端心跳包");
	if(channel.isConnected())
	{
		System.out.println(DateUtil.getCurDate()+"回发客户端心跳包");
		channel.write(new HeartMessage2000().pack());
	}
	}
	
}
