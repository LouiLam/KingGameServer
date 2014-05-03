package user;

public class PKUser {
public String name;
public int Camp;//阵营 1发起 2应战
public int seatID;//座位号0~4
public long roomSqlID=-1;
public int uid=-1;
public PKUser(String name, int camp, int seatID,int uid) {
	super();
	this.name = name;
	this.Camp = camp;
	this.seatID = seatID;
	this.uid=uid;
}
public PKUser(String name) {
	super();
	this.name = name;
}

}
