package client.msg.send;

import org.apache.log4j.Logger;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

public class LoginReturnMessage extends SocketMessageToSend{

	private static Logger logger = Logger.getLogger(LoginReturnMessage.class);
	
	private ChannelBuffer tableSeatUserInfo;
	
	/**
	 * 登陆房间结果（1登陆成功，2登陆失败）
	 */
	private byte loginResult;
	
	/**
	 * 登陆结果说明字段，如果登陆成功，则返回桌位ID与坐在桌位上的用户基本信息，如果登陆失败，
	 * 则返回登陆失败原因（1房间已满，2sessionkey已失效,3房间不存在，4钱不够）
	 */
	private short resultDescription;
	
	public LoginReturnMessage(byte loginResult, short resultDescription, ChannelBuffer tableSeatUserInfo){
		this.loginResult = loginResult;
		this.resultDescription = resultDescription;
		this.tableSeatUserInfo = tableSeatUserInfo;
		this.msgType = 1001;
	}
	
	@Override
	public ChannelBuffer pack() {
		ChannelBuffer cb = ChannelBuffers.dynamicBuffer();
		cb.writeShort(msgType);
		cb.writeByte(loginResult);
		cb.writeShort(resultDescription);
		if(loginResult == 1){
			cb.writeBytes(tableSeatUserInfo);
		}
		return cb;
	}
}
