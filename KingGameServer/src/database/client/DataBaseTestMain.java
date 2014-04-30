package database.client;

import org.apache.log4j.PropertyConfigurator;


import config.GlobalConfig;

public class DataBaseTestMain {

	
	public static void main(String[] args) {
		System.out.println(System.currentTimeMillis());
		PropertyConfigurator.configure(new GlobalConfig().getConfigResourceAddress("logConfig"));
//		IbatisClient.getInstance().test();
		LoginDao.selectUserSessionKeyBySessionKey("sessionKey1");
	}
}
