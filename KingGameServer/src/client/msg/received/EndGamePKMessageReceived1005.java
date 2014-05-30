package client.msg.received;

import object.PKHttpStringMgr;

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
import client.msg.send.EndGamePKResultMessage2007;

public class EndGamePKMessageReceived1005 extends SocketMessageReceived {
	long sql_id;

	@Override
	public void parse(ChannelBuffer buffer) {
		sql_id = buffer.readLong();
	}

	@Override
	public void logicHandle(ChannelBuffer buffer, Channel channel) {
		PK pk = PKManager.getInstance().getPKBySqlID(sql_id);
		if (pk != null) {
			try {
				httpPostFightEnd(channel);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void httpPostFightEnd(Channel channel) throws Exception {
		String url = PKHttpStringMgr.EndMap.get(sql_id);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		System.out.println(url);
		try {
			HttpGet httpGet = new HttpGet(url);
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
				System.out.println(str);
//				返回数据：status_sf(1：正常  -1:用户名不存在   -2：结果异常[界面显示“游戏暂未结束或其他异常原因”])
//		          win_side（1：挑战方胜   2：应战方胜）
//			  	  status_exe（1：结果判断成功 2：结果判断异常）
//				{"status_sf":"1","win_side":"2","status_exe":"1"}
				int win_side = 0;
				if (response1.getStatusLine().getStatusCode() == HttpStatus.SC_OK
						) {
					JSONObject obj=new JSONObject(str);
					int status_sf=obj.getInt("status_sf");
					
					if(status_sf==-2)
					{
						channel.write(new EndGamePKResultMessage2007(2,win_side).pack());
						return;
					}
					win_side=obj.getInt("win_side");
					// 用户点击结束游戏按钮成功，解散
					PK pk = PKManager.getInstance().removePK(sql_id);
					pk.channelGroup.write(new EndGamePKResultMessage2007(0,win_side)
							.pack());
				} else {
					// 用户点击开始游戏按钮失败
					channel.write(new EndGamePKResultMessage2007(1,win_side).pack());
				}
				HttpEntity entity1 = response1.getEntity();
				// do something useful with the response body
				// and ensure it is fully consumed
				EntityUtils.consume(entity1);

			} finally {
				response1.close();
			}

		} finally {
			httpclient.close();
		}

	}

	// public static void main(String[] args) {
	// try {
	// httpPostFightStart(null);
	// } catch (Exception e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// }
	// // String url="http://www..com/yxlm/member/fight_add.php?";
	// //
	// url=url+"action=stac&id="+20+"&status=1&gt="+"2|3|4"+"&yt="+"10|11|12";
	// //
	// // try {
	// // String testEncode = URLEncoder.encode(url, "utf-8" );
	// // System.out.println(testEncode);
	// // } catch (UnsupportedEncodingException e) {
	// // // TODO Auto-generated catch block
	// // e.printStackTrace();
	// // }
	// }
}