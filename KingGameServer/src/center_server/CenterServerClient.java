package center_server;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import server.GameServer;
import server.ServerConfig;


public class CenterServerClient {

	
	private static Logger logger = Logger.getLogger(CenterServerClient.class);
	
	private final String gameServerIp, centerServerIp;
	private int gameServerPort, centerServerPort;
	private GameServer server;
	private HttpClient httpClient;
	private long getCenterServerUpdateDataTime;
	private static CenterServerClient myself;
	
	public static CenterServerClient getInstance(){
		if(myself == null){
			myself = new CenterServerClient();
		}
		return myself;
	}
	
	private CenterServerClient(){
		ServerConfig serverConfig = new ServerConfig();
		gameServerIp = serverConfig.getGameServerIp();
		gameServerPort = serverConfig.getGameServerPort();
		centerServerIp = serverConfig.getCenterServerIp();
		centerServerPort = serverConfig.getCenterServerPort();
		getCenterServerUpdateDataTime = Long.parseLong(serverConfig.getCenterServerUpdateDataTime());
		
		httpClient = new DefaultHttpClient();
		server = GameServer.getInstance();
	}
	
	public void start() {
		
		httpPost("http://" + centerServerIp + ":" + centerServerPort + "/v1/gameServer/new");
//		new GameServerDataUpDateTask(getCenterServerUpdateDataTime).start();
		
	}

	public class GameServerDataUpDateTask extends AbstractTimerTask{
		
		GameServerDataUpDateTask(long getCenterServerUpdateDataTime){
			time = getCenterServerUpdateDataTime;
		}
		
		@Override
		public void run() {
			httpPost("http://" + centerServerIp + ":" + centerServerPort + "/v1/gameServer/update");
		}

	}
	
	public void httpPost(String url){
		try {
			StringEntity requestEntity = new StringEntity(server.getInfo(), "utf-8");
			HttpPost httppost = new HttpPost(url);
			httppost.setEntity(requestEntity);
			httppost.setHeader("Content-Type", "text/plain;charset=UTF-8");
			HttpResponse response = httpClient.execute(httppost);
			HttpEntity responseEntity = response.getEntity();
			String responseString = EntityUtils.toString(responseEntity);
			EntityUtils.consume(responseEntity);
			logger.info("responseString === " + responseString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
