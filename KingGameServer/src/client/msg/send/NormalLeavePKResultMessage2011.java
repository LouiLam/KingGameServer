package client.msg.send;

import java.io.UnsupportedEncodingException;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

/**
 * 离开房间
 * @author Administrator
 *
 */
public class NormalLeavePKResultMessage2011 extends SocketMessageToSend {

	int camp;
	int seatID;
	String roleName;

	public NormalLeavePKResultMessage2011(String roleName, int camp, int seatID) {
		this.seatID = seatID;
		this.camp = camp;
		this.roleName = roleName;
	}

	@Override
	public ChannelBuffer pack() {
		ChannelBuffer cb = ChannelBuffers.dynamicBuffer();
		cb.writeShort(2011);
		cb.writeShort(camp);
		cb.writeShort(seatID);
		cb.writeShort(roleName.getBytes().length);
		try {
			cb.writeBytes(roleName.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		int pkNum = 0;
//		for (int i = 0; i < 10; i++) {
//			if (users[i] != null) {
//				pkNum++;
//			}
//		}
//		cb.writeShort(pkNum);
//
//		for (int i = 0; i < 10; i++) {
//			if (users[i] != null) {
//				cb.writeShort(users[i].Camp);
//				cb.writeShort(users[i].seatID);
//				cb.writeShort(users[i].name.getBytes().length);
//				try {
//					cb.writeBytes(users[i].name.getBytes("utf-8"));
//				} catch (UnsupportedEncodingException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}

		return cb;
	}

}
