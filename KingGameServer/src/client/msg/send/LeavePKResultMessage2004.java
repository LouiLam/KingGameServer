package client.msg.send;

import java.io.UnsupportedEncodingException;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

import user.PKUser;

/**
 * 离开房间
 * @author Administrator
 *
 */
public class LeavePKResultMessage2004 extends SocketMessageToSend {

	int camp;
	int seatID;
	String name;

	public LeavePKResultMessage2004(String name, int camp, int seatID) {
		this.seatID = seatID;
		this.camp = camp;
		this.name = name;
	}

	@Override
	public ChannelBuffer pack() {
		ChannelBuffer cb = ChannelBuffers.dynamicBuffer();
		cb.writeShort(2004);
		cb.writeShort(camp);
		cb.writeShort(seatID);
		cb.writeShort(name.getBytes().length);
		try {
			cb.writeBytes(name.getBytes("utf-8"));
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
