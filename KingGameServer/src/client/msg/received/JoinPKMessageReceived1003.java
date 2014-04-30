package client.msg.received;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;


import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;

import client.msg.send.CanStartGamePKMessage2005;
import client.msg.send.JoinPKResultMessage2003;
import client.msg.send.LeavePKResultMessage2004;

import pk.PK;
import pk.PKManager;
import user.UserManager;

public class JoinPKMessageReceived1003 extends SocketMessageReceived {

	int index;
	int camp;
	String name;
	int  type;
	int uid;
	@Override
	public void parse(ChannelBuffer buffer) {
		index = buffer.readShort();// 条目索引
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
		
		type=PKManager.getInstance().getPKByIndex(index).type;
		PK pk=PKManager.getInstance().getPKByIndex(index);
		if((camp==1&&pk.faqiSeatCount==pk.type)||(camp==2&&pk.yingzhanSeatCount==pk.type))//加入方 人数已满,返回
		{
			channel.write(new JoinPKResultMessage2003(name,camp,-1,pk.users,type,1).pack());
			return;
		}
		PKManager.getInstance().getPKByIndex(index).addPKUser(name, camp,uid);
		UserManager.getInstance().getUserByName(name).roomIndex=PKManager.getInstance().getPKNum()-1;
		
		pk.channelGroup.add(channel);
		int seatID=camp==1?pk.faqiSeatCount-1:pk.yingzhanSeatCount-1;
		pk.channelGroup.write(new JoinPKResultMessage2003(name,camp,seatID,pk.users,type,0).pack());
//		System.out.println("pk.count:"+pk.count+",pk.type*2:"+pk.type*2);
		if(pk.faqiSeatCount==pk.type&&pk.yingzhanSeatCount==pk.type)
		{
			pk.channelHost
			.write(new CanStartGamePKMessage2005().pack());
		}
	}
}