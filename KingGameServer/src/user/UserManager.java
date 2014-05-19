package user;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;

import server.ServerConfig;

public class UserManager {

	
	private static Logger logger = Logger.getLogger(UserManager.class);
	private int maxUserNum;
	private static UserManager myself;
	private Map<Channel, PKUser> channelUserMap;
	private Map<String, PKUser> idUserMap;
	private UserManager(){
		channelUserMap = new ConcurrentHashMap<Channel, PKUser>();
		idUserMap=new ConcurrentHashMap<String ,PKUser>();
	}
	
	public static UserManager getInstance(){
		if(myself == null){
			myself = new UserManager();
		}
		return myself;
	}
	
	public int getCurUserNum(){
		return channelUserMap.size();
	}
	public void init(){
		maxUserNum = new ServerConfig().getMaxUserNum();
		
	}

	public boolean isFull(){
		return channelUserMap.size() >= maxUserNum;
	}
	

	public void addUser(Channel channel, PKUser user,String id) {
		channelUserMap.put(channel, user);
		idUserMap.put(id, user);
	}
	
	public PKUser getUserByChannel(Channel channel) {
		return channelUserMap.get(channel);
	}

	public PKUser removeUser(Channel channel) {
		PKUser user = channelUserMap.remove(channel);
		return user;
//		if(user != null){
//			user.leaveRoom();
//		}
	}

	public PKUser getUserByID(String id) {
		return idUserMap.get(id);
		
	}
}
