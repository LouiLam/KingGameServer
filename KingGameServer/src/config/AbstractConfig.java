package config;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

public class AbstractConfig {

	protected Document document;
	
	
	protected void getDocumentByFileAddress(String fileAddress){
		SAXReader saxReader = new SAXReader();
		try {
			document = saxReader.read(new File(fileAddress));
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
}
