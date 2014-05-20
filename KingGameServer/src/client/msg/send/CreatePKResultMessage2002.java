package client.msg.send;

import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

import pk.PK;

public class CreatePKResultMessage2002 extends SocketMessageToSend {

	private static Logger logger = Logger.getLogger(CreatePKResultMessage2002.class);
	PK pk;
	/**
	 * 0表示成功，否则表示失败
	 */
	int status;
	public CreatePKResultMessage2002(PK pk,int status) {
		this.pk = pk;
		this.status=status;
	}

	@Override
	public ChannelBuffer pack() {
		ChannelBuffer cb = ChannelBuffers.dynamicBuffer();
		cb.writeShort(2002);
		try {
			cb.writeShort(status);
			cb.writeShort(pk.id.getBytes().length);
			cb.writeBytes(pk.id.getBytes("utf-8"));
			cb.writeShort(pk.roleName.getBytes().length);
			cb.writeBytes(pk.roleName.getBytes("utf-8"));
			cb.writeShort(pk.area.getBytes().length);
			cb.writeBytes(pk.area.getBytes("utf-8"));
			cb.writeShort(pk.map.getBytes().length);
			cb.writeBytes(pk.map.getBytes("utf-8"));
			cb.writeShort(pk.title.getBytes().length);
			cb.writeBytes(pk.title.getBytes("utf-8"));
			cb.writeShort(pk.des.getBytes().length);
			cb.writeBytes(pk.des.getBytes("utf-8"));
			cb.writeInt(pk.type);
			cb.writeInt(pk.point);
			cb.writeInt(pk.faqiSeatCount);
			cb.writeInt(pk.yingzhanSeatCount);
			cb.writeLong(pk.sql_id);
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cb;
	}

}
