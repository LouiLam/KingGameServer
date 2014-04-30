package client.msg.send;

import java.io.UnsupportedEncodingException;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
/**
 * 房主点击开始 游戏后的结果 返回给客户端
 * @author Administrator
 *
 */
public class StartGamePKResultMessage2006 extends SocketMessageToSend {
	/**
	 * 	0表示成功 否则失败
	 */
	int status;
	String name;
	public StartGamePKResultMessage2006(int status,String name) {
		this.status=status;
		this.name=name;
	}

	@Override
	public ChannelBuffer pack() {
		ChannelBuffer cb = ChannelBuffers.dynamicBuffer();
		cb.writeShort(2006);
		cb.writeInt(status);
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
