package client.msg.send;

import java.io.UnsupportedEncodingException;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

/**
 * 房主离开房间，通知其他玩家异常情况
 * @author Administrator
 *
 */
public class HostLeavePKResultMessage2009 extends SocketMessageToSend {

	String name;

	public HostLeavePKResultMessage2009(String name) {
		this.name = name;
	}

	@Override
	public ChannelBuffer pack() {
		ChannelBuffer cb = ChannelBuffers.dynamicBuffer();
		cb.writeShort(2009);
		cb.writeShort(name.getBytes().length);
		try {
			cb.writeBytes(name.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return cb;
	}

}
