package client.msg.received;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import netty.MessageHandler;

import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.json.JSONObject;

import pk.PK;
import pk.PKManager;
import user.UserManager;
import client.msg.send.CreatePKResultMessage2002;
import client.msg.send.PointNotEnoughMessage2017;
import client.msg.send.RoleNameErrorMessage2015;

public class CreatePKMessageReceived1002 extends SocketMessageReceived {

	private static Logger logger = Logger.getLogger(CreatePKMessageReceived1002.class);
	String area,map,title,des,id,roleName,password;
	int point,type,uid;
	PK pk;
	@Override
	public void parse(ChannelBuffer buffer) {
		try {
			int idlength = buffer.readShort();
			byte idBytes[] = new byte[idlength];
			buffer.readBytes(idBytes);
			id = new String(idBytes);
			id=URLDecoder.decode(id,"UTF-8");
			
			
			int roleNamelength = buffer.readShort();
			byte roleNameBytes[] = new byte[roleNamelength];
			buffer.readBytes(roleNameBytes);
			roleName = new String(roleNameBytes);
			roleName=URLDecoder.decode(roleName,"UTF-8");
			
			int arealength = buffer.readShort();
			byte areaBytes[] = new byte[arealength];
			buffer.readBytes(areaBytes);
			area = new String(areaBytes,"utf-8");
			area=URLDecoder.decode(area,"UTF-8");
			int maplength = buffer.readShort();
			byte mapBytes[] = new byte[maplength];
			buffer.readBytes(mapBytes);
			 map = new String(mapBytes, "utf-8");
			 map=URLDecoder.decode(map,"UTF-8");
			int titlelength = buffer.readShort();
			byte titleBytes[] = new byte[titlelength];
			buffer.readBytes(titleBytes);
			 title = new String(titleBytes, "utf-8");
			 title=URLDecoder.decode(title,"UTF-8");
				int deslength = buffer.readShort();
				byte desBytes[] = new byte[deslength];
				buffer.readBytes(desBytes);
				 des = new String(titleBytes, "utf-8");
				 des=URLDecoder.decode(des,"UTF-8");
				 
					int pwdlength = buffer.readShort();
					byte pwdBytes[] = new byte[pwdlength];
					buffer.readBytes(pwdBytes);
					 password = new String(pwdBytes, "utf-8");
					 password=URLDecoder.decode(password,"UTF-8");
					 
			// 几V几
			type = buffer.readInt();
			// 点数
			point = buffer.readInt();
			
			 uid= buffer.readInt();
			pk=new PK(id,roleName,title, area, map,des, type, point,uid,password);
			
		
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void logicHandle(ChannelBuffer buffer, Channel channel) {
		
		httpPostFightAdd(channel);
		
	}
	public void httpPostFightAdd(Channel channel) {
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(
				
				"http://www.hexcm.com/yxlm/member/fight_add.php");

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("action", "dopost"));
		nvps.add(new BasicNameValuePair("date_create", System.currentTimeMillis()/1000+""));
		nvps.add(new BasicNameValuePair("gamename","英雄联盟"));
		nvps.add(new BasicNameValuePair("area",area));
		nvps.add(new BasicNameValuePair("title",title));
		nvps.add(new BasicNameValuePair("price",point+""));
		nvps.add(new BasicNameValuePair("map",map));
		nvps.add(new BasicNameValuePair("description",des));
		nvps.add(new BasicNameValuePair("type",type+""));
		nvps.add(new BasicNameValuePair("creator",roleName));
		nvps.add(new BasicNameValuePair("uid_c",uid+""));
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nvps,"utf-8"));
			CloseableHttpResponse httppHttpResponse2 = httpClient
					.execute(httpPost);
			if (httppHttpResponse2.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String str=EntityUtils.toString(httppHttpResponse2
						.getEntity());
				System.out.println(str);
				JSONObject obj=new JSONObject(str);
//				status_creator(1：用户名正常  -1: 用户名未填写  -2：用户名不存在)
//		          status_exe(1:正常  -1：创建失败)
//			  	  roomid:房间号

				
			
				int status_creator=obj.getInt("status_creator");
				int  status_exe=obj.getInt("status_creator");
				if(status_creator==-1||status_creator==-2)
				{
					channel.write(new RoleNameErrorMessage2015().pack());
				}
				else if(status_creator==-3)
				{
					channel.write(new PointNotEnoughMessage2017().pack());
				}
				else
				{	
				pk.sql_id=obj.getLong("roomid");
				PKManager.getInstance().put(pk.sql_id, pk);
				UserManager.getInstance().getUserByID(id).roomSqlID=pk.sql_id;
				PKManager.getInstance().getPKBySqlID(pk.sql_id).channelGroup.add(channel);
				PKManager.getInstance().getPKBySqlID(pk.sql_id).channelHost=channel;
				MessageHandler.channelGroup.write(new CreatePKResultMessage2002(pk,0).pack());
				System.out.println("房间创建成功"+pk.sql_id);}
				
			}
			else //创建房间失败
			{
				channel.write(new CreatePKResultMessage2002(pk,1).pack());
				System.out.println("房间创建失败");
			}
			httppHttpResponse2.close();
			httpClient.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
