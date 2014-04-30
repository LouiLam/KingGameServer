package client.msg.send;

import java.io.UnsupportedEncodingException;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

import pk.PK;

public class RoomPKMessage2001 extends SocketMessageToSend {

	PK pk;

	public RoomPKMessage2001(PK pk) {
		this.pk = pk;
	}

	@Override
	public ChannelBuffer pack() {
		ChannelBuffer cb = ChannelBuffers.dynamicBuffer();
		cb.writeShort(2001);
		try {
			cb.writeShort(pk.name.getBytes().length);
			cb.writeBytes(pk.name.getBytes("utf-8"));
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
			cb.writeLong(pk.sql_id);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cb;
	}

}
