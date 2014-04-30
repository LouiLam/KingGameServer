package main;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.dom4j.DocumentException;

import pk.PKManager;

import center_server.CenterServerClient;
import config.GlobalConfig;
import database.client.IbatisClient;
import server.GameServer;

public class GameServerMain {

	private static Logger logger = Logger.getLogger(GameServerMain.class);
	
	public static void main(String[] args) {
		try {
			
			PropertyConfigurator.configure(new GlobalConfig().getConfigResourceAddress("logConfig"));
			
			IbatisClient.getInstance().init();
			
			GameServer.getInstance().init();
			
			GameServer.getInstance().start();
			
//			CenterServerClient.getInstance().start();
			
		} catch (DocumentException e) {
			logger.error("服务器启动失败！" + e.getMessage());
		}
	}
}
