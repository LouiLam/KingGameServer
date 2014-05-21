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
	String id;
	public StartGamePKResultMessage2006(int status,String id) {
		this.status=status;
		this.id=id;
	}

	@Override
	public ChannelBuffer pack() {
		ChannelBuffer cb = ChannelBuffers.dynamicBuffer();
		cb.writeShort(2006);
		cb.writeInt(status);
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
