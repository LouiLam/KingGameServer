package client.msg.send;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

public class RoomPKBeginMessage2013 extends SocketMessageToSend {


	public RoomPKBeginMessage2013() {
	}

	@Override
	public ChannelBuffer pack() {
		ChannelBuffer cb = ChannelBuffers.dynamicBuffer();
		cb.writeShort(2013);
		
		return cb;
	}

}
