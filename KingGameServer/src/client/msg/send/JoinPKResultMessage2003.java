package client.msg.send;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

import pk.PK;
import user.PKUser;

public class JoinPKResultMessage2003 extends SocketMessageToSend {

	int camp;
	int seatID;
	String id, roleName;
	PK pk;
	/**
	 * 0表示成功，否则表示失败
	 */
	int status;

//	public JoinPKResultMessage2003(String id, String roleName, int camp,
//			int seatID, HashMap<String, PKUser> userMap, int type, int status,
//			String title, String area,int point,int faqiSeatCount,int yingzhanSeatCount,long sql_id) {
//		this.seatID = seatID;
//		this.camp = camp;
//		this.id = id;
//		this.roleName = roleName;
//		this.userMap = userMap;
//		this.type = type;
//		this.status = status;
//		this.title = title;
//		this.area = area;
//		this.point=point;
//		this.faqiSeatCount=faqiSeatCount;
//		this.yingzhanSeatCount=yingzhanSeatCount;
//		this.sql_id=sql_id;
//	}
	public JoinPKResultMessage2003(int status, String id, String roleName,
			int camp, int seatID, PK pk) {
		this.pk=pk;
		this.status = status;
		this.seatID = seatID;
		this.camp = camp;
		this.id = id;
		this.roleName = roleName;
	}
	@Override
	public ChannelBuffer pack() {
		ChannelBuffer cb = ChannelBuffers.dynamicBuffer();
		cb.writeShort(2003);
		cb.writeInt(status);
		cb.writeInt(pk.type);
		cb.writeInt(pk.point);
		cb.writeInt(camp);
		cb.writeInt(pk.faqiSeatCount);
		cb.writeInt(pk.yingzhanSeatCount);
		cb.writeLong(pk.sql_id);
		try {
			cb.writeShort(id.getBytes().length);
			cb.writeBytes(id.getBytes("utf-8"));
			cb.writeShort(roleName.getBytes().length);
			cb.writeBytes(roleName.getBytes("utf-8"));
			cb.writeShort(pk.title.getBytes().length);
			cb.writeBytes(pk.title.getBytes("utf-8"));
			cb.writeShort(pk.area.getBytes().length);
			cb.writeBytes(pk.area.getBytes("utf-8"));
			cb.writeShort(pk.map.getBytes().length);
			cb.writeBytes(pk.map.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		cb.writeShort(pk.userMap.size());
		Iterator<String> it = pk.userMap.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			PKUser user = pk.userMap.get(key);
			cb.writeShort(user.Camp);
			cb.writeShort(user.seatID);
			try {
			cb.writeShort(user.roleName.getBytes().length);
			cb.writeBytes(user.roleName.getBytes("utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		return cb;
	}

}
