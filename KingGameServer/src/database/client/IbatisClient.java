package database.client;


import java.io.IOException;
import java.io.Reader;
import java.util.List;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

import config.GlobalConfig;

public class IbatisClient {

	private static Logger logger = Logger.getLogger(IbatisClient.class);
	
	private SqlSessionFactory sqlSessionFactory;
	private static IbatisClient myself = new IbatisClient();
	
	public static IbatisClient getInstance(){
		return myself;
	}
	
	private IbatisClient(){
		
	}
	
	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	public SqlSession getSqlSession() {
		return sqlSessionFactory.openSession();
	}

	public List test() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		int result = 0;
		List test = null;
		try{
			test = sqlSession.selectList("test");
			logger.info(test.size());
		}finally{
			sqlSession.close();
		}
		return test;
	}

	public void init() {
		String resource = new GlobalConfig().getConfigResourceAddress("databaseConfig");
		Reader reader = null;
		try {
			reader = Resources.getResourceAsReader(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}  
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        sqlSessionFactory = builder.build(reader);
        try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	
	
}
