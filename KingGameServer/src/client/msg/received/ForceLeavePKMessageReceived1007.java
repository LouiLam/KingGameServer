package client.msg.received;

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
import user.PKUser;
import user.UserManager;
import client.msg.send.ForceLeavePKResultMessage2012;
import client.msg.send.HostLeavePKResultMessage2009;

/**
 * 玩家在倒计时内强制离开挑战房间
 */
public class ForceLeavePKMessageReceived1007 extends SocketMessageReceived {
	@Override
	public void parse(ChannelBuffer buffer) {
		 
	}

	@Override
	public void logicHandle(ChannelBuffer buffer, Channel channel) {
		PKUser usertemp = UserManager.getInstance().getUserByChannel(channel);
		
	
		long roomIndex=usertemp.roomSqlID;
		usertemp.roomSqlID=-1;
		PK pk=PKManager.getInstance().getPKBySqlID(roomIndex);
		PKUser user=pk.getPKUserByRoleName(usertemp.id);
		pk.removePKUser(user.id, user.Camp, user.seatID);
		try {
			httpGetFightEnd(channel, user.uid, pk, user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(channel==pk.channelHost) //如果是房主强退  挑战解散
		{
			PKManager.getInstance().getPKBySqlID(roomIndex).channelGroup
			.write(new HostLeavePKResultMessage2009(usertemp.id).pack());
			PKManager.getInstance().removePK(roomIndex);
			
		}
		PKManager.getInstance().refreshPK();
//		else
//		{
//			pk.channelHost.write(new NoCanStartGamePKMessage2010().pack());
//		}
	
	}
	
	public  void  httpGetFightEnd(Channel channel,int uid,PK pk,PKUser user) throws Exception {
		String url="http://198.204.255.98/yxlm/member/fight_score.php?action=fight&reason=0";
		String paras="&uid="+uid+"&fightid="+pk.sql_id+"&money=-"+pk.point;
		
        CloseableHttpClient httpclient = HttpClients.createDefault();
        System.out.println(url+paras);
        try {
            HttpGet httpGet = new HttpGet(url+paras);
            CloseableHttpResponse response1 = httpclient.execute(httpGet);
            // The underlying HTTP connection is still held by the response object
            // to allow the response content to be streamed directly from the network socket.
            // In order to ensure correct deallocation of system resources
            // the user MUST either fully consume the response content  or abort request
            // execution by calling CloseableHttpResponse#close().

            try {
            	String entity=EntityUtils.toString(response1
 						.getEntity());
           
                if(response1.getStatusLine().getStatusCode()==HttpStatus.SC_OK)
                {
                	
                	//用户强退结束游戏成功
                 	JSONObject obj = new JSONObject(entity);
    				int value = Integer.parseInt(obj.get("stauts") + "");
    				if(value==1)//表示成功
    				{
    					pk.channelGroup.write(new ForceLeavePKResultMessage2012(user.roleName,user.Camp,user.seatID,pk.sql_id).pack());
    				}
    				else //表示失败
    				{
    					pk.channelGroup.write(new ForceLeavePKResultMessage2012(user.roleName,user.Camp,user.seatID,pk.sql_id).pack());
    				}
                }
                else
                {
                	pk.channelGroup.write(new ForceLeavePKResultMessage2012(user.roleName,user.Camp,user.seatID,pk.sql_id).pack());
                	//用户强退结束游戏失败
//                	channel.write(new StartGamePKResultMessage2006(1,pk.id).pack());
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
}