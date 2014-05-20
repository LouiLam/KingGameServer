package pk;

import java.util.HashMap;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;

import user.PKUser;

public class PK {

	public String id;// 发起挑战的房主id
	public String roleName;// 发起挑战的游戏角色名
	public String title;// 挑战标题
	public String area;// 挑战区
	public String map;// 挑战地图
	public String des;// 挑战描述
	public long sql_id;//挑战条目的数据库ID
	
	public int type;// 游戏对战人数类型
	public int point;// 挑战点数
//	public PKUser[] users = new PKUser[10];
	public int faqiSeatCount = 0;
	public int yingzhanSeatCount = 0;
	public int count = 0;// 挑战人数计数
	public ChannelGroup channelGroup = new DefaultChannelGroup();// 挑战中玩家通道
	public Channel channelHost;// 房主玩家通道
	public HashMap<String,PKUser > userMap=new HashMap<String,PKUser >();
	public PK(String id,String roleName, String title, String area, String map, String des,int type,
			int point,int hostUid) {
		super();
		this.id = id;
		this.roleName = roleName;
		this.title = title;
		this.area = area;
		this.map = map;
		this.des = des;
		this.type = type;
		this.point = point;
		userMap.put(id,new PKUser(roleName, 1, faqiSeatCount,hostUid));
		faqiSeatCount++;
		count++;
	}
	public PKUser getPKUserByRoleName(String id)
	{
		return userMap.get(id);
	}
	public void addPKUser(String id,String roleName, int camp,int uid) {
		if (camp == 1) {
			userMap.put(id, new PKUser(roleName, camp, faqiSeatCount,uid));
			faqiSeatCount++;
			
		} else {
			userMap.put(id, new PKUser(roleName, camp, yingzhanSeatCount,uid));
			yingzhanSeatCount++;
		}
		count++;
	}

	public void removePKUser(String id, int camp, int seatID) {
		count--;
		userMap.remove(id);
		if (camp == 1) {
			faqiSeatCount--;

		} else {
			yingzhanSeatCount--;
		}
	}

	@Override
	public String toString() {
		return "房主id:" + id+"---游戏角色名"+roleName + "----标题:" + title + "---挑战区:" + area + "--挑战地图:"
				+ map + "--游戏对战人数类型" + type + "---挑战点数" + point;
	}

}
