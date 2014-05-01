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

import pk.PK;
import pk.PKManager;
import user.UserManager;
import client.msg.send.EndGamePKResultMessage2007;

public class EndGamePKMessageReceived1005 extends SocketMessageReceived {
	long sql_id;
	@Override
	public void parse(ChannelBuffer buffer) {
		 sql_id=buffer.readLong();
	}

	@Override
	public void logicHandle(ChannelBuffer buffer, Channel channel) {
		for (int i = 0; i < PKManager.getInstance().getPKNum(); i++) {
			PK pk=PKManager.getInstance().getPKByIndex(i);
			if(pk.sql_id==sql_id)
			{
				try {
					httpPostFightEnd(channel);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				break;
			}
		}
		
//		UserManager.getInstance().getUserByName(name).roomIndex=PKManager.getInstance().getPKNum()-1;
//		PKManager.getInstance().getPKByIndex(index).channelGroup.add(channel);
//		int seatID=camp==1?PKManager.getInstance().getPKByIndex(index).faqiSeatCount-1:PKManager.getInstance().getPKByIndex(index).yingzhanSeatCount-1;
//		PKManager.getInstance().getPKByIndex(index).channelGroup
//				.write(new JoinPKResultMessage2003(name,camp,seatID,PKManager.getInstance().getPKByIndex(index).users).pack());
	}
	public  void  httpPostFightEnd(Channel channel) throws Exception {
		String url=PKHttpStringMgr.EndMap.get(sql_id);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        System.out.println(url);
        try {
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response1 = httpclient.execute(httpGet);
            // The underlying HTTP connection is still held by the response object
            // to allow the response content to be streamed directly from the network socket.
            // In order to ensure correct deallocation of system resources
            // the user MUST either fully consume the response content  or abort request
            // execution by calling CloseableHttpResponse#close().

            try {
            	String str=EntityUtils.toString(response1
 						.getEntity());
                System.out.println(response1.getStatusLine());
                if(response1.getStatusLine().getStatusCode()==HttpStatus.SC_OK&&str.equals("11"))
                {
                	//用户点击结束游戏按钮成功，解散
                	PK pk=PKManager.getInstance().pkMap.get(sql_id);
                	PKManager.getInstance().removePK(pk);
                	pk.channelGroup.write(new EndGamePKResultMessage2007(0).pack());
                }
                else
                {
                	//用户点击开始游戏按钮失败
                	channel.write(new EndGamePKResultMessage2007(1).pack());
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