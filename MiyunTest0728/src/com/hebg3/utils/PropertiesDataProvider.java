package com.hebg3.utils;

import java.io.File;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;

/**
 * 从.properties文件中读取相关测试数据<br>
 * @author Young
 *
 */
public class PropertiesDataProvider {
	public static String getTestData(String configFilePath , String key){
		
		Configurations configs = new Configurations();
		Configuration config = null;
		/**
		 * 对于PropertiesConfiguration()方法，源码中给出的注释为
		 * Creates an empty PropertyConfiguration object which can be
		 * used to synthesize a new Properties file by adding values and
		 * then saving().
		 * 参考资料中该处为propertiesConfiguration(String)方法。
		 * eclipse报错未定义此构造函数
		 * 以上，Configuration2对于初始化的变化，行注释中为Configuration1.X的方法
		 */
		//config = new PropertiesConfiguration(configFilePath);
		try {
			config = configs.properties(new File(configFilePath));
		} catch (org.apache.commons.configuration2.ex.ConfigurationException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return String.valueOf(config.getProperty(key));
	}
}
