package pk;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import user.UserManager;

public class PKManager {

	
	private static Logger logger = Logger.getLogger(UserManager.class);

	private static PKManager myself;
	private ArrayList<PK> pkList ;
	public HashMap<Long,PK> pkMap ;
	private PKManager(){
		pkList=new ArrayList<PK>();
		pkMap=new HashMap<Long, PK>();
	}
	
	public static PKManager getInstance(){
		if(myself == null){
			myself = new PKManager();
		}
		return myself;
	}
	
	
	public void init(){
//		for (int i = 0; i < 10; i++) {
//			PK pk= new PK("标题1", "区域"+i, "地图名字", i, 50);
//			pkList.add(pk);
//		}
//		List roomListNodes = new ServerConfig().getAllRoomNodes(); 
//		for (int j = 0; j < roomListNodes.size(); j++) {
//			Element roomElement = (Element) roomListNodes.get(j);	
//			String name = roomElement.attributeValue("name");
//			byte id = Byte.parseByte(roomElement.attributeValue("id"));			
//			long minUchip = Long.parseLong(roomElement.attributeValue("minUchip"));
//			long minGameChip = Long.parseLong(roomElement.attributeValue("minGameChip"));
//			long defaultGameChip = Long.parseLong(roomElement.attributeValue("defaultGameChip"));
//			long maxGameChip = Long.parseLong(roomElement.attributeValue("maxGameChip"));
//			byte maxTableUserNum = Byte.parseByte(roomElement.attributeValue("maxTableUserNum"));
//			byte gameUserNum = Byte.parseByte(roomElement.attributeValue("gameUserNum"));
//			roomIdRoomMap.put(id, new PK(id, minUchip, name, minGameChip, defaultGameChip, maxGameChip, maxTableUserNum, gameUserNum));
//		}
//		logger.info("roomIdRoomMap ===" + roomIdRoomMap.size());
		
	}
	public void add(PK pk)
	{
		pkList.add(pk);
	}
	/**
	 * 获取挑战条目数量 
	 * @return
	 */
	public int getPKNum(){
		return pkList.size();
	}

	public PK removePK(int index)
	{
		return pkList.remove(index);
	}
	public boolean removePK(PK pk)
	{
		return pkList.remove(pk);
	}
	/**
	 * 获取挑战条目
	 * @return
	 */
	public PK getPKByIndex(int index) {
		return pkList.get(index);
	}
}
