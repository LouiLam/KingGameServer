package client.msg.received;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;

import pk.PK;
import pk.PKManager;
import user.UserManager;
import client.msg.send.CanStartGamePKMessage2005;
import client.msg.send.JoinPKResultMessage2003;

public class JoinPKMessageReceived1003 extends SocketMessageReceived {

	long sql_id;
	int camp;
	String name;
	int  type;
	int uid;
	@Override
	public void parse(ChannelBuffer buffer) {
		sql_id = buffer.readLong();// 数据库ID
		 camp = buffer.readShort();// 阵营 1发起 2应战
		 
		 
		 
		int namelength = buffer.readShort();
		byte nameBytes[] = new byte[namelength];
		buffer.readBytes(nameBytes);
		try {
			name = new String(nameBytes, "utf-8");
			name=URLDecoder.decode(name,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		uid=buffer.readInt();
		
	}

	@Override
	public void logicHandle(ChannelBuffer buffer, Channel channel) {
		
		type=PKManager.getInstance().getPKBySqlID(sql_id).type;
		PK pk=PKManager.getInstance().getPKBySqlID(sql_id);
		if((camp==1&&pk.faqiSeatCount==pk.type)||(camp==2&&pk.yingzhanSeatCount==pk.type))//加入方 人数已满,返回
		{
			channel.write(new JoinPKResultMessage2003(name,camp,-1,pk.userMap,type,1,pk.title,pk.area).pack());
			return;
		}
		PKManager.getInstance().getPKBySqlID(sql_id).addPKUser(name, camp,uid);
		UserManager.getInstance().getUserByName(name).roomSqlID=pk.sql_id;
		
		pk.channelGroup.add(channel);
		int seatID=camp==1?pk.faqiSeatCount-1:pk.yingzhanSeatCount-1;
		pk.channelGroup.write(new JoinPKResultMessage2003(name,camp,seatID,pk.userMap,type,0,pk.title,pk.area).pack());
//		System.out.println("pk.count:"+pk.count+",pk.type*2:"+pk.type*2);
		if(pk.faqiSeatCount==pk.type&&pk.yingzhanSeatCount==pk.type)
		{
			pk.channelHost
			.write(new CanStartGamePKMessage2005().pack());
		}
	}
}