package client.msg.send;

import java.io.UnsupportedEncodingException;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

/**
 * 房主强制或正常或崩溃离开房间
 * @author Administrator
 *
 */
public class HostLeavePKResultMessage2009 extends SocketMessageToSend {

	String id;

	public HostLeavePKResultMessage2009(String id) {
		this.id = id;
	}

	@Override
	public ChannelBuffer pack() {
		ChannelBuffer cb = ChannelBuffers.dynamicBuffer();
		cb.writeShort(2009);
		cb.writeShort(id.getBytes().length);
		try {
			cb.writeBytes(id.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return cb;
	}

}
