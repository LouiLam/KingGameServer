package client.msg.send;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

public class RoomPKFinishMessage2008 extends SocketMessageToSend {


	public RoomPKFinishMessage2008() {
	}

	@Override
	public ChannelBuffer pack() {
		ChannelBuffer cb = ChannelBuffers.dynamicBuffer();
		cb.writeShort(2008);
		
		return cb;
	}

}
