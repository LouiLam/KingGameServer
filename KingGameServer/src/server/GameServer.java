package server;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import netty.MessageHandler;
import netty.ServerPipelineFactory;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.json.JSONObject;

import pk.PKManager;
import user.UserManager;


public class GameServer {

	private static Logger logger = Logger.getLogger(MessageHandler.class);
	
	private int port;
	private String ip;
	private JSONObject info = new JSONObject();
	private PKManager roomManager;
	private UserManager userManager;

	private static GameServer myself;
	
	public static GameServer getInstance(){
		if(myself == null){
			myself = new GameServer();
		}
		return myself;
	}
	
	private GameServer(){
		
		info = new JSONObject();
		roomManager = PKManager.getInstance();
		userManager = UserManager.getInstance();
	}
	
	public void init(){
		roomManager.init();
		userManager.init();
		ServerConfig serverConfig = new ServerConfig();
		ip = serverConfig.getGameServerIp();
		port = serverConfig.getGameServerPort();
//		try {
//			info.put("ip", ip);
//			info.put("port", port);
//			info.put("roomNum", roomManager.getRoomNum());
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
	}
	
	public String getInfo(){
//		try {
//			info.put("curUserNum", userManager.getCurUserNum());
//			info.put("room", roomManager.getAllRoomInfo());
//			logger.info(info.toString());
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//		return info.toString();
		return null;
	}
	
	
	public void start() throws DocumentException{
		int port = new ServerConfig().getGameServerPort();
		ServerBootstrap bootstrap = new ServerBootstrap(
				new NioServerSocketChannelFactory(
						Executors.newCachedThreadPool(),
						Executors.newCachedThreadPool()));
		bootstrap.setPipelineFactory(new ServerPipelineFactory());
		bootstrap.bind(new InetSocketAddress(port));
		logger.info("================================================");
		logger.info("================服务器绑定端口：" + port + "成功===============");
		logger.info("================================================");
	}
	
	
}
