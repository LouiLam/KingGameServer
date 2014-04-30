package netty;

import java.io.IOException;
import java.net.SocketAddress;

import org.apache.log4j.Logger;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;

import pk.PK;
import pk.PKManager;
import user.PKUser;
import user.UserManager;
import client.msg.received.SocketMessageReceived;
import client.msg.send.LeavePKResultMessage2004;

public class MessageHandler extends SimpleChannelHandler {

	private static Logger logger = Logger.getLogger(MessageHandler.class);
	public static ChannelGroup channelGroup = new DefaultChannelGroup();

	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
		Channel channel = e.getChannel();
		SocketAddress ip = channel.getRemoteAddress();
		logger.info("Client connected in.." + ip);
		channelGroup.add(channel);
	}

	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		Channel channel = (Channel) e.getChannel();
		ChannelBuffer channelBuffer = (ChannelBuffer) e.getMessage();
		short msgType = channelBuffer.readShort();
		SocketMessageReceived msg = SocketMessageFactory.getInstance()
				.getMessage(msgType);
		msg.handle(channelBuffer, channel);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		if (e.getCause() instanceof IOException) {
			PKUser user = UserManager.getInstance().removeUser(e.getChannel());
			if (user != null) {
				int roomIndex = user.roomIndex;
				if (roomIndex != -1) {
					PKManager.getInstance().getPKByIndex(roomIndex)
							.removePKUser(user.name, user.Camp, user.seatID);
					PKManager.getInstance().getPKByIndex(roomIndex).channelGroup
							.write(new LeavePKResultMessage2004(user.name,
									user.Camp, user.seatID).pack());
					if(PKManager.getInstance().getPKByIndex(roomIndex).channelHost==ctx.getChannel())
					{
						PK obj=PKManager.getInstance().removePK(roomIndex);
						for (int i = 0; i < obj.users.length; i++) {
							 PKUser user1= UserManager.getInstance().getUserByName(obj.users[i].name);
							 user1.roomIndex=-1;
						}
						
						logger.info("房主退出，解散房间,通知其他玩家退出房间，并且处理其他玩家退出逻辑");
						//解散挑战条目
					}
					if(PKManager.getInstance().getPKByIndex(roomIndex).count==0)
					{
						PKManager.getInstance().removePK(roomIndex);
						logger.info("房间无人，解散房间");
						//解散挑战条目
					}
				
					logger.info("玩家断线，移除所在房间roomIndex" + roomIndex+"玩家数量"+UserManager.getInstance().getCurUserNum());
				}
			}
			logger.info("玩家断线");
		} else {
			e.getCause().printStackTrace();
		}
	}

	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) {
		try {
			UserManager.getInstance().removeUser(e.getChannel());
			logger.info("玩家关闭连接");
			logger.info("连接数量" + channelGroup.size());

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
