package client.msg.send;

import org.apache.log4j.Logger;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;


public class ErrorMessage extends SocketMessageToSend{

	private static Logger logger = Logger.getLogger(ErrorMessage.class);
	/**
	 * 1.下注金额有误
	 */
	private byte error;
	
	public ErrorMessage(byte error){
		this.msgType = 2;
		this.error = error;
	}
	
	@Override
	public ChannelBuffer pack() {
		ChannelBuffer cb = ChannelBuffers.dynamicBuffer();
		cb.writeShort(msgType);
		cb.writeByte(error);
		return cb;
	}

}
