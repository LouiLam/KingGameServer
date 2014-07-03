package client.msg.send;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

/**
 * 房主强制或正常或崩溃离开房间
 * @author Administrator
 *
 */
public class PointNotEnoughMessage2017 extends SocketMessageToSend {


	public PointNotEnoughMessage2017() {
	}

	@Override
	public ChannelBuffer pack() {
		ChannelBuffer cb = ChannelBuffers.dynamicBuffer();
		cb.writeShort(2017);
		return cb;
	}

}
