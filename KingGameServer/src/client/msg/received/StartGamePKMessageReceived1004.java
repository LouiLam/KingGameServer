package client.msg.received;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;

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
import user.PKUser;
import client.msg.send.RoomPKMessage2001;
import client.msg.send.StartGamePKResultMessage2006;

public class StartGamePKMessageReceived1004 extends SocketMessageReceived {
	long sql_id;
	String gt="",yt="";
	@Override
	public void parse(ChannelBuffer buffer) {
		 sql_id=buffer.readLong();
	}

	@Override
	public void logicHandle(ChannelBuffer buffer, Channel channel) {
		
		 HashMap<Long,PK> map=	PKManager.getInstance().getPKMap();
			Iterator<Long> it = map.keySet().iterator();
			while(it.hasNext()){
				long key = it.next();
				PK pk = map.get(key);
				Iterator<String> it1=pk.userMap.keySet().iterator();
				while(it1.hasNext()){
					String key1 = it1.next();
					PKUser user = pk.userMap.get(key1);
					if(user.Camp==1)
					{
						gt+=user.uid+"|";
					}
					else
					{
						yt+=user.uid+"|";
					}
				}
				//去掉最后一个|号
				gt=gt.substring(0, gt.length()-1);
				yt=yt.substring(0, yt.length()-1);
			}
	
		try {
			httpPostFightStart(channel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public  void  httpPostFightStart(Channel channel) throws Exception {
		String url="http://www.hexcm.com/yxlm/member/fight_add.php?";
		String gtEncode = URLEncoder.encode(gt, "utf-8" ); 
		String ytEncode = URLEncoder.encode(yt, "utf-8" ); 
		String uu="action=stac&id="+sql_id+"&status=1&gt="+gtEncode+"&yt="+ytEncode;	
        CloseableHttpClient httpclient = HttpClients.createDefault();
        System.out.println(url+uu);
        try {
            HttpGet httpGet = new HttpGet(url+uu);
            PKHttpStringMgr.EndMap.put(sql_id, url+"action=stac&id="+sql_id+"&status=2&gt="+gtEncode+"&yt="+ytEncode);
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
                PK pk=PKManager.getInstance().getPKBySqlID(sql_id);
                if(response1.getStatusLine().getStatusCode()==HttpStatus.SC_OK&&str.equals("11"))
                {
                	//用户点击开始游戏按钮成功
                	//用户点击结束游戏按钮成功，解散
                	
                	pk.channelGroup.write(new StartGamePKResultMessage2006(0,pk.id).pack());
                }
                else
                {
                	//用户点击开始游戏按钮失败
                	channel.write(new StartGamePKResultMessage2006(1,pk.id).pack());
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
////		String url="http://www..com/yxlm/member/fight_add.php?";
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