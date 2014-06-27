package client.msg.received;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.json.JSONObject;

import pk.PK;
import pk.PKManager;
import user.UserManager;
import client.msg.send.CanStartGamePKMessage2005;
import client.msg.send.JoinPKResultMessage2003;

public class JoinPKMessageReceived1003 extends SocketMessageReceived {

	long sql_id;
	int camp;
	String id, roleName, password;
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
			byte pwdBytes[] = new byte[pwdlength];
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

		// 先检测角色名
		try {
			if (!httpGetFightStart(channel))
				return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PK pk = PKManager.getInstance().getPKBySqlID(sql_id);
		if ((camp == 1 && pk.faqiSeatCount == pk.type)
				|| (camp == 2 && pk.yingzhanSeatCount == pk.type))// 加入方 人数已满,返回
		{
//			channel.write(new JoinPKResultMessage2003(id, roleName, camp, -1,
//					pk.userMap, pk.type, -1, pk.title, pk.area, pk.point,
//					pk.faqiSeatCount, pk.yingzhanSeatCount, pk.sql_id).pack());
			
			channel.write(new JoinPKResultMessage2003(-1,id, roleName, camp, -1,
					pk).pack());
			return;
		}
		int seatID = camp == 1 ? pk.faqiSeatCount - 1
				: pk.yingzhanSeatCount - 1;
		if (password.equals("") || pk.password.equals(password))// 没有密码
		{
			PKManager.getInstance().getPKBySqlID(sql_id)
					.addPKUser(id, roleName, camp, uid);
			UserManager.getInstance().getUserByID(id).roomSqlID = pk.sql_id;

			pk.channelGroup.add(channel);

			pk.channelGroup
					.write(new JoinPKResultMessage2003(0,id, roleName, camp,
							seatID, pk).pack());
			PKManager.getInstance().refreshPK();
			// System.out.println("pk.count:"+pk.count+",pk.type*2:"+pk.type*2);
			if (pk.faqiSeatCount == pk.type && pk.yingzhanSeatCount == pk.type) {
				pk.channelHost.write(new CanStartGamePKMessage2005().pack());
			}
		} else // 密码错误
		{
			channel.write(new JoinPKResultMessage2003(-2,id, roleName, camp,
					seatID, pk).pack());
		}

	}

	public boolean httpGetFightStart(Channel channel) throws Exception {
		PK pk = PKManager.getInstance().getPKBySqlID(sql_id);
		String url = "http://www.hexcm.com/yxlm/member/fight_add.php?action=join_check";
		String other = "&creator=" + roleName + "&area=" + pk.area;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		System.out.println(url + other);
		try {
			HttpGet httpGet = new HttpGet(url + other);
			CloseableHttpResponse response1 = httpclient.execute(httpGet);
			// The underlying HTTP connection is still held by the response
			// object
			// to allow the response content to be streamed directly from the
			// network socket.
			// In order to ensure correct deallocation of system resources
			// the user MUST either fully consume the response content or abort
			// request
			// execution by calling CloseableHttpResponse#close().

			try {
				String str = EntityUtils.toString(response1.getEntity());
				// status_join(1：用户名正常 -1: 用户名未填写 -2：用户名不存在)
				// {"status_join":"-1"}
				System.out.println(str);
				JSONObject obj = new JSONObject(str);
				int status_join = obj.getInt("status_join");

				if (response1.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					// 用户点击开始游戏按钮成功
					// 用户点击结束游戏按钮成功，解散
					if (status_join == 1) {
						return true;
					} else {
						channel.write(new JoinPKResultMessage2003(-3,id, roleName,
								camp, -1, pk).pack());
						return false;
					}
				}
				channel.write(new JoinPKResultMessage2003(-3,id, roleName, camp,
						-1, pk)
						.pack());
				HttpEntity entity1 = response1.getEntity();

				// do something useful with the response body
				// and ensure it is fully consumed
				EntityUtils.consume(entity1);
				return false;
			} finally {
				response1.close();
			}

		} finally {
			httpclient.close();
		}

	}

}