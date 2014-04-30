package netty;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

public class MsgEncoder extends OneToOneEncoder {

	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel channel,
			Object msg) throws Exception {
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		ChannelBuffer dataBuffer = (ChannelBuffer) msg;
		int datalength = dataBuffer.readableBytes();
		buffer.writeInt(datalength);
		buffer.writeBytes(dataBuffer);
		return buffer;
	}
}
