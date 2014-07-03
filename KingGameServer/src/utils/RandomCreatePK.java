package utils;

import java.util.Random;

import pk.PK;
import pk.PKManager;

public class RandomCreatePK {
	static Random r = new Random();
	private static String area[]={"艾欧尼亚","祖安","诺克萨斯","班德尔城","皮尔特沃夫","战争学院","巨神峰","雷瑟守备","裁决之地","黑色玫瑰","暗影岛","钢铁烈阳","均衡教派","水晶之痕","影流","守望之海","征服之海","卡拉曼达","皮城警备","比尔吉沃特","德玛西亚","弗雷尔卓德","无畏先锋","恕瑞玛","扭曲丛林","教育网专区"};
	private static String map[]={"扭曲丛林","召唤师峡谷","水晶之痕","嚎哭深渊"};
	private static int point[]={10,30,50,100,200};
	public static void create() {
		
		
		for (int i = 1; i <= 40; i++) {
			String title=r.nextInt(5)+"";
			int areaIndex=r.nextInt(area.length);
			int mapIndex=r.nextInt(map.length);
			int pointIndex=r.nextInt(point.length);
			PK pk=new PK(getID(),"",title, area[areaIndex], map[mapIndex],title, 1, point[pointIndex],-1,"");
			pk.addPKUser("", "", 2, -1);
			PKManager.getInstance().putNoRefresh(-i, pk);
		}
		for (int i = 41; i <= 47; i++) {
			String title=r.nextInt(5)+"";
			int areaIndex=r.nextInt(area.length);
			int mapIndex=r.nextInt(map.length);
			int pointIndex=r.nextInt(point.length);
			PK pk=new PK(getID(),"",title, area[areaIndex], map[mapIndex],title, 2, point[pointIndex],-1,"");
			pk.addPKUser("", "", 2, -1);
			pk.addPKUser("", "", 2, -1);
			pk.addPKUser("", "", 1, -1);
			PKManager.getInstance().putNoRefresh(-i, pk);
		}
		for (int i = 48; i <= 50; i++) {
			String title=r.nextInt(5)+"";
			int areaIndex=r.nextInt(area.length);
			int mapIndex=r.nextInt(map.length);
			int pointIndex=r.nextInt(point.length);
			PK pk=new PK(getID(),"",title,area[areaIndex], map[mapIndex],title, 3, point[pointIndex],-1,"");
			pk.addPKUser("", "", 2, -1);
			pk.addPKUser("", "", 2, -1);
			pk.addPKUser("", "", 2, -1);
			pk.addPKUser("", "", 1, -1);
			pk.addPKUser("", "", 1, -1);
			PKManager.getInstance().putNoRefresh(-i, pk);
		}
	}
	public  static void update1(long i)
	{
		String title=r.nextInt(5)+"";
		int areaIndex=r.nextInt(area.length);
		int mapIndex=r.nextInt(map.length);
		int pointIndex=r.nextInt(point.length);
		PK pk=new PK(getID(),"",title, area[areaIndex], map[mapIndex],title, 1, point[pointIndex],-1,"");
		pk.addPKUser("", "", 2, -1);
		PKManager.getInstance().putNoRefresh(-i, pk);
	}
	public  static void update2(long i)
	{
		String title=r.nextInt(5)+"";
		int areaIndex=r.nextInt(area.length);
		int mapIndex=r.nextInt(map.length);
		int pointIndex=r.nextInt(point.length);
		PK pk=new PK(getID(),"",title, area[areaIndex], map[mapIndex],title, 2, point[pointIndex],-1,"");
		pk.addPKUser("", "", 2, -1);
		pk.addPKUser("", "", 2, -1);
		pk.addPKUser("", "", 1, -1);
		PKManager.getInstance().putNoRefresh(-i, pk);
	}
	public  static void update3(long i)
	{
		String title=r.nextInt(5)+"";
		int areaIndex=r.nextInt(area.length);
		int mapIndex=r.nextInt(map.length);
		int pointIndex=r.nextInt(point.length);
		PK pk=new PK(getID(),"",title,area[areaIndex], map[mapIndex],title, 3, point[pointIndex],-1,"");
		pk.addPKUser("", "", 2, -1);
		pk.addPKUser("", "", 2, -1);
		pk.addPKUser("", "", 2, -1);
		pk.addPKUser("", "", 1, -1);
		pk.addPKUser("", "", 1, -1);
		PKManager.getInstance().putNoRefresh(-i, pk);
	}
	private static String getID()
	{
		int length=5+r.nextInt(5);
		String id="";
		for (int j = 0; j < length; j++) {
			int t= 97+r.nextInt(25);
			id+=(char)t;
		}
		int num=r.nextInt(4);
		for (int j = 0; j < num; j++) {
			int t= 49+r.nextInt(9);
			id+=(char)t;
		}
		return id;
	}
	public static void main(String[] args) {
		char a='1';
		System.out.println(getID());
//		create();
	}
}
