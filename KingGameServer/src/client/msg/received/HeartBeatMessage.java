package client.msg.received;

import org.apache.log4j.Logger;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;

public class HeartBeatMessage extends SocketMessageReceived{

	private static Logger logger = Logger.getLogger(HeartBeatMessage.class);
	
	@Override
	public void logicHandle(ChannelBuffer buffer, Channel channel) {
		logger.info("玩家发送心跳包");
		//返回心跳包
		ChannelBuffer cb = ChannelBuffers.dynamicBuffer();
		cb.writeShort(1);
		channel.write(cb);
	}

	@Override
	public void parse(ChannelBuffer buffer) {
		
	}

}
