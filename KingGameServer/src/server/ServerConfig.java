package server;

import java.util.List;

import org.dom4j.DocumentException;

import config.AbstractConfig;
import config.GlobalConfig;


public class ServerConfig extends AbstractConfig{


	public ServerConfig()  {
		super.getDocumentByFileAddress(new GlobalConfig().getConfigResourceAddress("serverConfig"));	
	}

	public int getGameServerPort(){
		return Integer.parseInt(document.selectSingleNode("/serverConfig/port").getText());
	}
	
	public String getGameServerIp(){
		return document.selectSingleNode("/serverConfig/ip").getText();
	}
	
	public int getCenterServerPort(){
		return Integer.parseInt(document.selectSingleNode("/serverConfig/centerServer/port").getText());
	}
	public int getMaxUserNum(){
		return Integer.parseInt(document.selectSingleNode("/serverConfig/maxUserNum").getText());
	}
	public String getCenterServerIp(){
		return document.selectSingleNode("/serverConfig/centerServer/ip").getText();
	}
	public String getCenterServerUpdateDataTime(){
		return document.selectSingleNode("/serverConfig/centerServer/updateDataTime").getText();
	}
	
	public List getAllRoomNodes(){
		return document.selectNodes("/serverConfig/rooms/room");
	}
	
}
