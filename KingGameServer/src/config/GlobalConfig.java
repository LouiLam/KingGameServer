package config;


import org.dom4j.DocumentException;

public class GlobalConfig extends AbstractConfig{

	
	private final String CONFIG_REF = "config/GlobalConfig.xml";
	
	
	public GlobalConfig() {
		super.getDocumentByFileAddress(CONFIG_REF);
	}
	
	public String getConfigResourceAddress(String configName){
		return document.selectSingleNode("/globalConfig/"+ configName).getText();
	}
		

}
