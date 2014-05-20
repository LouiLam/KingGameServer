package database.client;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

public class LoginDao {

	
	
	public static List selectUserSessionKeyBySessionKey(String sessionKey){
		SqlSession sqlSession = IbatisClient.getInstance().getSqlSession();
		List list = null;
		try{
			list = sqlSession.selectList("selectSessionKey", sessionKey);
		}finally{
			sqlSession.close();
		}
		return list;
	}
	
	public static List selectUserInfoByUid(long uid){
		SqlSession sqlSession = IbatisClient.getInstance().getSqlSession();
		List list = null;
		try{
			list = sqlSession.selectList("selectUserInfoByUid", uid);
		}finally{
			sqlSession.close();
		}
		return list;
	}
}
