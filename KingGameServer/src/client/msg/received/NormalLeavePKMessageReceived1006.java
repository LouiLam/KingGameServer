package client.msg.received;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;

import pk.PK;
import pk.PKManager;
import user.PKUser;
import user.UserManager;
import client.msg.send.HostLeavePKResultMessage2009;
import client.msg.send.LeavePKResultMessage2004;
import client.msg.send.NoCanStartGamePKMessage2010;
import client.msg.send.NormalLeavePKResultMessage2011;

/**
 * 玩家正常主动离开挑战房间
 */
public class NormalLeavePKMessageReceived1006 extends SocketMessageReceived {
	@Override
	public void parse(ChannelBuffer buffer) {
		 
	}

	@Override
	public void logicHandle(ChannelBuffer buffer, Channel channel) {
		PKUser usertemp = UserManager.getInstance().getUserByChannel(channel);
		long roomIndex=usertemp.roomSqlID;
		usertemp.roomSqlID=-1;
		PK pk=PKManager.getInstance().getPKBySqlID(roomIndex);
		PKUser user=pk.getPKUserByName(usertemp.name);
		pk.channelGroup
		.write(new NormalLeavePKResultMessage2011(user.name,
				user.Camp, user.seatID).pack());
		pk.removePKUser(user.name, user.Camp, user.seatID);
		if(channel!=pk.channelHost)
		{
			pk.channelHost.write(new NoCanStartGamePKMessage2010().pack());
		}
		else
		{
			PKManager.getInstance().getPKBySqlID(roomIndex).channelGroup
			.write(new HostLeavePKResultMessage2009(usertemp.name).pack());
			PKManager.getInstance().removePK(roomIndex);
		}
	
	}
	
//	public static void main(String[] args) {
//		try {
//			httpPostFightStart(null);
//		} catch (Exception e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
////		String url="http://www.woowgo.com/yxlm/member/fight_add.php?";
////		url=url+"action=stac&id="+20+"&status=1&gt="+"2|3|4"+"&yt="+"10|11|12";	
////				
////		try {
////			String testEncode = URLEncoder.encode(url, "utf-8" );
////		    System.out.println(testEncode);
////		} catch (UnsupportedEncodingException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		} 
//	}
}