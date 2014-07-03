package main;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.dom4j.DocumentException;

import pk.PK;
import pk.PKManager;

import server.GameServer;
import timer_task.TaskScheduled;
import utils.RandomCreatePK;
import config.GlobalConfig;
import database.client.IbatisClient;

public class GameServerMain {

	private static Logger logger = Logger.getLogger(GameServerMain.class);
	static int num=0;
	public static void main(String[] args) {
		try {
			
			PropertyConfigurator.configure(new GlobalConfig().getConfigResourceAddress("logConfig"));
			
			IbatisClient.getInstance().init();
			
			GameServer.getInstance().init();
			
			GameServer.getInstance().start();
			
//			CenterServerClient.getInstance().start();
			RandomCreatePK.create();
			//每10分钟更新5个
			TaskScheduled.scheduleAtFixedRate(new Runnable() {
				
				@Override
				public void run() {
					System.out.println("刷新5个数据");
					for (int i = 1; i <=5; i++) {
						if(num>=50) {num=0;}
						PK pk=	PKManager.getInstance().removePKNoRefres(-(i+num));
						switch (pk.type) {
						case 1:
							RandomCreatePK.update1(i+num);
							break;
						case 2:
							RandomCreatePK.update2(i+num);
							break;
						case 3:
							RandomCreatePK.update3(i+num);
							break;
						default:
							break;
						}
					}
					PKManager.getInstance().refreshPK();
					num+=5;
				}
			}, 10, 10, TimeUnit.MINUTES);
		} catch (DocumentException e) {
			logger.error("服务器启动失败！" + e.getMessage());
		}
	}
}
