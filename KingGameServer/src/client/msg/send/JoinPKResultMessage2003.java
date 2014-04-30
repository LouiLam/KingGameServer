package client.msg.send;

import java.io.UnsupportedEncodingException;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

import user.PKUser;

public class JoinPKResultMessage2003 extends SocketMessageToSend {

	int camp;
	int seatID;
	String name;
	PKUser[] users;
	int type;
	/**
	 * 0表示成功，否则表示失败
	 */
	int status;
	public JoinPKResultMessage2003(String name, int camp, int seatID, PKUser[] users,int type,int status) {
		this.seatID = seatID;
		this.camp = camp;
		this.name = name;
		this.users = users;
		this.type=type;
		this.status=status;
	}

	@Override
	public ChannelBuffer pack() {
		ChannelBuffer cb = ChannelBuffers.dynamicBuffer();
		cb.writeShort(2003);
//		cb.writeShort(camp);
//		cb.writeShort(seatID);
		cb.writeInt(status);
		cb.writeInt(type);
		cb.writeShort(name.getBytes().length);
		try {
			cb.writeBytes(name.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int pkNum = 0;
		for (int i = 0; i < 10; i++) {
			if (users[i] != null) {
				pkNum++;
			}
		}
		cb.writeShort(pkNum);

		for (int i = 0; i < 10; i++) {
			if (users[i] != null) {
				cb.writeShort(users[i].Camp);
				cb.writeShort(users[i].seatID);
				cb.writeShort(users[i].name.getBytes().length);
				try {
					cb.writeBytes(users[i].name.getBytes("utf-8"));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return cb;
	}

}
