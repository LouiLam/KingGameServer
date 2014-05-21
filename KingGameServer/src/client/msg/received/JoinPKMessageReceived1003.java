package client.msg.received;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;

import netty.MessageHandler;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;

import pk.PK;
import pk.PKManager;
import user.UserManager;
import client.msg.send.CanStartGamePKMessage2005;
import client.msg.send.JoinPKResultMessage2003;
import client.msg.send.RoomPKBeginMessage2013;
import client.msg.send.RoomPKFinishMessage2008;
import client.msg.send.RoomPKMessage2001;

public class JoinPKMessageReceived1003 extends SocketMessageReceived {

	long sql_id;
	int camp;
	String id, roleName,password;
	int type;
	int uid;

	@Override
	public void parse(ChannelBuffer buffer) {
		sql_id = buffer.readLong();// 数据库ID
		camp = buffer.readShort();// 阵营 1发起 2应战
		try {
			int idlength = buffer.readShort();
			byte idBytes[] = new byte[idlength];
			buffer.readBytes(idBytes);
			id = new String(idBytes, "utf-8");
			id = URLDecoder.decode(id, "UTF-8");
			
			int roleNamelength = buffer.readShort();
			byte roleNameBytes[] = new byte[roleNamelength];
			buffer.readBytes(roleNameBytes);
			roleName = new String(roleNameBytes, "utf-8");
			roleName = URLDecoder.decode(roleName, "UTF-8");
			
			int pwdlength = buffer.readShort();
			byte  pwdBytes[] = new byte[pwdlength];
			buffer.readBytes(pwdBytes);
			password = new String(pwdBytes, "utf-8");
			password = URLDecoder.decode(password, "UTF-8");
			
		}

		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		uid = buffer.readInt();

	}

	@Override
	public void logicHandle(ChannelBuffer buffer, Channel channel) {

		type = PKManager.getInstance().getPKBySqlID(sql_id).type;
		PK pk = PKManager.getInstance().getPKBySqlID(sql_id);
		if ((camp == 1 && pk.faqiSeatCount == pk.type)
				|| (camp == 2 && pk.yingzhanSeatCount == pk.type))// 加入方 人数已满,返回
		{
			channel.write(new JoinPKResultMessage2003(id,roleName, camp, -1, pk.userMap,
					type, -1, pk.title, pk.area,pk.point,pk.faqiSeatCount,pk.yingzhanSeatCount,pk.sql_id).pack());
			return;
		}
		int seatID = camp == 1 ? pk.faqiSeatCount - 1
				: pk.yingzhanSeatCount - 1;
		if(password.equals("")||pk.password.equals(password))//没有密码
		{
			PKManager.getInstance().getPKBySqlID(sql_id).addPKUser(id,roleName, camp, uid);
			UserManager.getInstance().getUserByID(id).roomSqlID = pk.sql_id;

			pk.channelGroup.add(channel);
			
			pk.channelGroup.write(new JoinPKResultMessage2003(id,roleName, camp, seatID,
					pk.userMap, type, 0, pk.title, pk.area,pk.point,pk.faqiSeatCount,pk.yingzhanSeatCount,pk.sql_id).pack());
			PKManager.getInstance().refreshPK();
			// System.out.println("pk.count:"+pk.count+",pk.type*2:"+pk.type*2);
			if (pk.faqiSeatCount == pk.type && pk.yingzhanSeatCount == pk.type) {
				pk.channelHost.write(new CanStartGamePKMessage2005().pack());
			}
		}
		else  //密码错误
		{
			channel.write(new JoinPKResultMessage2003(id,roleName, camp, seatID,
					pk.userMap, type, -2, pk.title, pk.area,pk.point,pk.faqiSeatCount,pk.yingzhanSeatCount,pk.sql_id).pack());
		}
		
	}
}