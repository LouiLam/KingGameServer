package client.msg.received;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;

import pk.PK;
import pk.PKManager;
import user.PKUser;
import user.UserManager;
import client.msg.send.LoginReturnMessage;
import client.msg.send.RepeatLoginErrorMessage2014;
import client.msg.send.RoomPKBeginMessage2013;
import client.msg.send.RoomPKFinishMessage2008;
import client.msg.send.RoomPKMessage2001;

public class LoginMessage1001 extends SocketMessageReceived{

	private static Logger logger = Logger.getLogger(LoginMessage1001.class);
	private String id;
	private byte roomId;
	
	@Override
	public void logicHandle(ChannelBuffer buffer, Channel channel) {
		//检查该连接是否已登陆过
		if(UserManager.getInstance().getChannelByID(id) != null){
			logger.error(id+"此用户已登陆过：" + ", ip === " + channel.getRemoteAddress());
			Channel	close_channel=UserManager.getInstance().getChannelByID(id);
			close_channel.write(new RepeatLoginErrorMessage2014().pack());
//			close_channel.close();
		}
		//服务器人满 
		if(UserManager.getInstance().isFull()){
			sendResult((byte)2, (short)1, channel);
			return;
		}
//		//检查用户合法性,sessionKey是否有效
//		List<Map> userSessionKeyList = LoginDao.selectUserSessionKeyBySessionKey(sessionKey);
//		//sessionKey 不存在
//		if(userSessionKeyList.size() == 0){
//			sendResult((byte)2, (short)2, channel);
//			return;
//		}
//		//sessionkey时间 过期
//		Map userSessionKeyMap = userSessionKeyList.get(0);
//		long expiryTime = (Long) userSessionKeyMap.get("expiryTime");
//		if(expiryTime < System.currentTimeMillis()/1000){
//			sendResult((byte)2, (short)2, channel);
//			return;
//		}
//		long uid = (Long) userSessionKeyMap.get("uid");
//		Map userInfoMap = (Map) LoginDao.selectUserInfoByUid(uid).get(0);
		PKUser user = new PKUser(id);
		UserManager.getInstance().addUser(channel, user,id);
//		Room room = RoomManager.getInstance().getRoomById(roomId);
//		//房间不存在
//		if(room == null){
//			logger.error("房间不存在roomId=" + roomId + ", ip=" + channel.getRemoteAddress());
//			sendResult((byte)2, (short)3, channel);
//			return;
//		}
//		//加入房间成功，则反回桌子id和桌子座位上所有用户的基本信息，否则返回失败（钱不够 ）
//		if(room.canUserEnter(user)){
//			room.userEnter(channel, user);
//			channel.write(new LoginReturnMessage((byte)1, user.getTable().getId()
//					, user.getTable().getSeatUserInfo()).pack());
//		}else{
//			sendResult((byte)2, (short)4, channel);
//			return;
//		}
		logger.info(id+"玩家登陆成功"  + ", ip=" + channel.getRemoteAddress());
		//如果有PK房间列表，就发送给推送房间信息给登录用户
		
		channel.write(new RoomPKBeginMessage2013().pack());
		 HashMap<Long,PK> map=	PKManager.getInstance().getPKMap();
		Iterator<Long> it = map.keySet().iterator();
		while(it.hasNext()){
			long key = it.next();
			PK pk = map.get(key);
			channel.write(new RoomPKMessage2001(pk).pack());
		}
		channel.write(new RoomPKFinishMessage2008().pack());
	}

	@Override
	public void parse(ChannelBuffer buffer) {
		
		int Length = buffer.readShort();
		byte[] idBytes = new byte[Length];
		buffer.readBytes(idBytes);
		id = new String(idBytes);
		try {
			id=URLDecoder.decode(id,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
//		byte sessionKeyLength = buffer.readByte();
//		//sessionKey
//		byte[] sessionKeyBytes = new byte[sessionKeyLength];
//		buffer.readBytes(sessionKeyBytes);
//		sessionKey = new String(sessionKeyBytes);
//		//roomId
//		roomId = buffer.readByte();
	}

	private void sendResult(byte loginResult, short resultDescription
			, Channel channel){
		channel.write(new LoginReturnMessage(loginResult, resultDescription
				, null).pack());
	}
}
