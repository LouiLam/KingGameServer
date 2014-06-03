package client.msg.send;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

public class HeartMessage2000 extends SocketMessageToSend {

	public HeartMessage2000() {
	}

	@Override
	public ChannelBuffer pack() {
		ChannelBuffer cb = ChannelBuffers.dynamicBuffer();
		cb.writeShort(2000);
		return cb;
	}

}
