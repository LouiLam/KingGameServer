package client.msg.send;

import java.io.UnsupportedEncodingException;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

/**
 * 客户端崩溃，或者强行关闭程序离开房间
 * @author Administrator
 *
 */
public class CrashLeavePKResultMessage2004 extends SocketMessageToSend {

	int camp;
	int seatID;
	String name;
	long sql_id;
	public CrashLeavePKResultMessage2004(String name, int camp, int seatID,long sql_id) {
		this.seatID = seatID;
		this.camp = camp;
		this.name = name;
		this.sql_id=sql_id;
	}

	@Override
	public ChannelBuffer pack() {
		ChannelBuffer cb = ChannelBuffers.dynamicBuffer();
		cb.writeShort(2004);
		cb.writeShort(camp);
		cb.writeShort(seatID);
		cb.writeLong(sql_id);
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
