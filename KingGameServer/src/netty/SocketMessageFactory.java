package netty;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.dom4j.DocumentException;

import client.msg.received.SocketMessageReceived;
import config.GlobalConfig;

public class SocketMessageFactory {


	private Properties properties;
	private static SocketMessageFactory myself;
	
	public static SocketMessageFactory getInstance() throws IOException, DocumentException{
		if(myself == null){
			myself = new SocketMessageFactory();
		}
		return myself;
	}
	
	private SocketMessageFactory() {
		try{
			properties = new Properties();
			FileInputStream fis = new FileInputStream(
					new GlobalConfig().getConfigResourceAddress("socketHandlerConfig"));
			properties.load(fis);
			fis.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public SocketMessageReceived getMessage(short msgType) 
			throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		return (SocketMessageReceived)(Class.forName(properties.getProperty("" + msgType)).newInstance());
	}
	
	
	
}
