package user;

public class PKUser {
public String id;
public String roleName;
public int Camp;//阵营 1发起 2应战
public int seatID;//座位号0~4
public long roomSqlID=-1;
public int uid=-1;
public PKUser(String roleName, int camp, int seatID,int uid) {
	super();
	this.roleName = roleName;
	this.Camp = camp;
	this.seatID = seatID;
	this.uid=uid;
}
public PKUser(String id) {
	super();
	this.id = id;
}
@Override
	public String toString() {
		
		return "Camp:"+Camp+"--roleName:"+roleName+"--seatID:"+seatID+"--id:"+id+"--uid:"+uid;
	}
}
