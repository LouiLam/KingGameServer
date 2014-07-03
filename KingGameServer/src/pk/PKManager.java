package pk;

import java.util.HashMap;
import java.util.Iterator;

import netty.MessageHandler;

import org.apache.log4j.Logger;

import client.msg.send.RoomPKBeginMessage2013;
import client.msg.send.RoomPKFinishMessage2008;
import client.msg.send.RoomPKMessage2001;

import user.UserManager;

public class PKManager {

	private static Logger logger = Logger.getLogger(UserManager.class);

	private static PKManager myself;
	// private HashMap<Integer,PK> pkList ;
	private HashMap<Long, PK> pkMap;

	private PKManager() {
		// pkList=new ArrayList<PK>();
		pkMap = new HashMap<Long, PK>();
	}

	public static PKManager getInstance() {
		if (myself == null) {
			myself = new PKManager();
		}
		return myself;
	}

	public HashMap<Long, PK> getPKMap() {
		return pkMap;
	}

	public void init() {
		// for (int i = 0; i < 10; i++) {
		// PK pk= new PK("标题1", "区域"+i, "地图名字", i, 50);
		// pkList.add(pk);
		// }
		// List roomListNodes = new ServerConfig().getAllRoomNodes();
		// for (int j = 0; j < roomListNodes.size(); j++) {
		// Element roomElement = (Element) roomListNodes.get(j);
		// String name = roomElement.attributeValue("name");
		// byte id = Byte.parseByte(roomElement.attributeValue("id"));
		// long minUchip =
		// Long.parseLong(roomElement.attributeValue("minUchip"));
		// long minGameChip =
		// Long.parseLong(roomElement.attributeValue("minGameChip"));
		// long defaultGameChip =
		// Long.parseLong(roomElement.attributeValue("defaultGameChip"));
		// long maxGameChip =
		// Long.parseLong(roomElement.attributeValue("maxGameChip"));
		// byte maxTableUserNum =
		// Byte.parseByte(roomElement.attributeValue("maxTableUserNum"));
		// byte gameUserNum =
		// Byte.parseByte(roomElement.attributeValue("gameUserNum"));
		// roomIdRoomMap.put(id, new PK(id, minUchip, name, minGameChip,
		// defaultGameChip, maxGameChip, maxTableUserNum, gameUserNum));
		// }
		// logger.info("roomIdRoomMap ===" + roomIdRoomMap.size());

	}

	public void refreshPK() {
		// 刷新列表
		Iterator<Long> it = pkMap.keySet().iterator();
		MessageHandler.channelGroup.write(new RoomPKBeginMessage2013().pack());
		while (it.hasNext()) {
			long key = it.next();
			PK pk1 = pkMap.get(key);
			MessageHandler.channelGroup
					.write(new RoomPKMessage2001(pk1).pack());
		}
		MessageHandler.channelGroup.write(new RoomPKFinishMessage2008().pack());
	}

	public void put(long sqlid, PK pk) {
		pkMap.put(sqlid, pk);
		refreshPK();
	}
	public void putNoRefresh(long sqlid, PK pk) {
		pkMap.put(sqlid, pk);
	}
	public PK removePKNoRefres(long sqlid) {
		// 刷新列表
		PK pk = pkMap.remove(sqlid);
		return pk;
	}
	/**
	 * 获取挑战条目数量
	 * 
	 * @return
	 */
	public int getPKNum() {
		return pkMap.size();
	}

	public PK removePK(long sqlid) {
		// 刷新列表
		PK pk = pkMap.remove(sqlid);
		refreshPK();
		return pk;
	}

	// public boolean removePK(PK pk)
	// {
	// return pkMap.remove(key))remove(pk);
	// }
	/**
	 * 获取挑战条目
	 * 
	 * @return
	 */
	public PK getPKBySqlID(long sqlid) {
		return pkMap.get(sqlid);
	}
}
