package client.msg.send;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

public class NoCanStartGamePKMessage2010 extends SocketMessageToSend {

	public NoCanStartGamePKMessage2010() {
	}

	@Override
	public ChannelBuffer pack() {
		ChannelBuffer cb = ChannelBuffers.dynamicBuffer();
		cb.writeShort(2010);
		return cb;
	}

}
