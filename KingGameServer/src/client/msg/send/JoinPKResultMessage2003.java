package client.msg.send;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

import user.PKUser;

public class JoinPKResultMessage2003 extends SocketMessageToSend {

	int camp;
	int seatID;
	String id, roleName;
	HashMap<String, PKUser> userMap;
	int type,point;
	int faqiSeatCount;
	int yingzhanSeatCount;
	String title, area;
	long sql_id;
	/**
	 * 0表示成功，否则表示失败
	 */
	int status;

	public JoinPKResultMessage2003(String id, String roleName, int camp,
			int seatID, HashMap<String, PKUser> userMap, int type, int status,
			String title, String area,int point,int faqiSeatCount,int yingzhanSeatCount,long sql_id) {
		this.seatID = seatID;
		this.camp = camp;
		this.id = id;
		this.roleName = roleName;
		this.userMap = userMap;
		this.type = type;
		this.status = status;
		this.title = title;
		this.area = area;
		this.point=point;
		this.faqiSeatCount=faqiSeatCount;
		this.yingzhanSeatCount=yingzhanSeatCount;
		this.sql_id=sql_id;
	}

	@Override
	public ChannelBuffer pack() {
		ChannelBuffer cb = ChannelBuffers.dynamicBuffer();
		cb.writeShort(2003);
		cb.writeInt(status);
		cb.writeInt(type);
		cb.writeInt(point);
		cb.writeInt(camp);
		cb.writeInt(faqiSeatCount);
		cb.writeInt(yingzhanSeatCount);
		cb.writeLong(sql_id);
		try {
			cb.writeShort(id.getBytes().length);
			cb.writeBytes(id.getBytes("utf-8"));
			cb.writeShort(roleName.getBytes().length);
			cb.writeBytes(roleName.getBytes("utf-8"));
			cb.writeShort(title.getBytes().length);
			cb.writeBytes(title.getBytes("utf-8"));
			cb.writeShort(area.getBytes().length);
			cb.writeBytes(area.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		cb.writeShort(userMap.size());
		Iterator<String> it = userMap.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			PKUser user = userMap.get(key);
			cb.writeShort(user.Camp);
			cb.writeShort(user.seatID);
			try {
			cb.writeShort(user.roleName.getBytes().length);
			cb.writeBytes(user.roleName.getBytes("utf-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return cb;
	}

}
