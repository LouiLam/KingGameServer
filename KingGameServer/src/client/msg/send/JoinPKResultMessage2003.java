package client.msg.send;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

import user.PKUser;

public class JoinPKResultMessage2003 extends SocketMessageToSend {

	int camp;
	int seatID;
	String name;
	HashMap<String,PKUser > userMap;
	int type;
	String title,area;
	/**
	 * 0表示成功，否则表示失败
	 */
	int status;
	public JoinPKResultMessage2003(String name, int camp, int seatID, HashMap<String,PKUser > userMap,int type,int status,String title,String area) {
		this.seatID = seatID;
		this.camp = camp;
		this.name = name;
		this.userMap = userMap;
		this.type=type;
		this.status=status;
		this.title=title;
		this.area=area;
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
		cb.writeShort(title.getBytes().length);
		try {
			cb.writeBytes(title.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cb.writeShort(area.getBytes().length);
		try {
			cb.writeBytes(area.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	
		cb.writeShort(userMap.size());
		Iterator<String> it=userMap.keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			PKUser user = userMap.get(key);
			cb.writeShort(user.Camp);
			cb.writeShort(user.seatID);
			cb.writeShort(user.name.getBytes().length);
			try {
				cb.writeBytes(user.name.getBytes("utf-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return cb;
	}

}
