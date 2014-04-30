package client.msg.send;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

public class CanStartGamePKMessage2005 extends SocketMessageToSend {

	public CanStartGamePKMessage2005() {
	}

	@Override
	public ChannelBuffer pack() {
		ChannelBuffer cb = ChannelBuffers.dynamicBuffer();
		cb.writeShort(2005);
		return cb;
	}

}
