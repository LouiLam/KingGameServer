package client.msg.send;

import netty.MessageHandler;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

/**
 * 房主强制或正常或崩溃离开房间
 * @author Administrator
 *
 */
public class SizeofResultMessage2016 extends SocketMessageToSend {


	public SizeofResultMessage2016() {
	}

	@Override
	public ChannelBuffer pack() {
		ChannelBuffer cb = ChannelBuffers.dynamicBuffer();
		cb.writeShort(2016);
		cb.writeInt(MessageHandler.channelGroup.size());
		return cb;
	}

}
