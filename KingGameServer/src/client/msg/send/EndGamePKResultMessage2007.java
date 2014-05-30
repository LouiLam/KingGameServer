package client.msg.send;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
/**
 * 房主点击开结束游戏后的结果 返回给客户端
 * @author Administrator
 *
 */
public class EndGamePKResultMessage2007 extends SocketMessageToSend {
	/**
	 * 	0表示成功 否则失败
	 */
	int status,win_side;
	public EndGamePKResultMessage2007(int status,int win_side) {
		this.status=status;
		this.win_side=win_side;
	}

	@Override
	public ChannelBuffer pack() {
		ChannelBuffer cb = ChannelBuffers.dynamicBuffer();
		cb.writeShort(2007);
		cb.writeInt(status);
		cb.writeInt(win_side);
		return cb;
	}

}
