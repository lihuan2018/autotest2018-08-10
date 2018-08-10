package com.hebg3.utils;

import java.util.Properties;
import com.hebg3.utils.PropertiesDataProvider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.ie.InternetExplorerDriver;
//import org.openqa.selenium.phantomjs.PhantomJSDriver;  //原文内容，未加载相关jar
//import org.openqa.selenium.phantomjs.PhantomJSDriverService;  //原文内容，未加载相关jar
//import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.ITestContext;

/**
 *在不同平台上选择对应的浏览器，系统平台程序自动判断是什么平台 
 *由于webdriver资源仅chromedriver_win，故仅限用于windows系统chrome浏览器
 * @author Administrator
 *
 */

public class SelectBrowser {
	static Logger logger = LogManager.getLogger(SelectBrowser.class.getName());
	
	public WebDriver selectExplorerByName(String browser,ITestContext context){
		Properties props = System.getProperties();  //获得系统属性集
		String currentPlatform = props.getProperty("os.name");
		logger.info("当前操作系统是：[" + currentPlatform + "]");
		logger.info("启动测试浏览器：[" + browser + "]");
		//从testNG的配置文件读取参数driverFilePath的值
		String driverFilePath = context.getCurrentXmlTest().getParameter("driverFilePath");
		/**声明驱动的路径*/
		//调用com.hebg3.utils.PropertiesDataProvider.java中的getTestData(String,String)方法，获取驱动
		//若有其他平台的浏览器驱动，同样方法获取
		String chromedriver_win = PropertiesDataProvider.getTestData(driverFilePath,"chromedriver_win");
		String firefoxdriver_win = PropertiesDataProvider.getTestData(driverFilePath,"chromedriver_win");
		String chromedriver_mac = PropertiesDataProvider.getTestData(driverFilePath, "chromedriver_mac");
		//不同平台不同浏览器，返回不同浏览器driver
		if (currentPlatform.toLowerCase().contains("win")){
			if (browser.equalsIgnoreCase("chrome")){
				System.setProperty("webdriver.chrome.driver",chromedriver_win);
				return new ChromeDriver();
				//返回chrome浏览器对象
			}else if (browser.equalsIgnoreCase("firefox")){
				System.setProperty("webdriver.firefox.driver", firefoxdriver_win);
				return new FirefoxDriver();
			}else{
				logger.error("The [" + browser + "]" + " explorer is not applicable  for  [" + currentPlatform + "] OS");
				Assert.fail("The [" + browser + "]" + " explorer does not apply to  [" + currentPlatform + "] OS");
				return null;
			}
		}else if (currentPlatform.toLowerCase().contains("linux")){
			logger.error("The [" + browser + "]" + " explorer is not applicable  for  [" + currentPlatform + "] OS");
			Assert.fail("The [" + browser + "]" + " explorer does not apply to  [" + currentPlatform + "] OS");
			return null;
		}else if (currentPlatform.toLowerCase().contains("mac")){
			if (browser.equalsIgnoreCase("mac")) {
				System.setProperty("webdriver.chrome.driver", chromedriver_mac);
				return new ChromeDriver();
			} else {
				logger.error("The [" + browser + "]" + " explorer is not applicable  for  [" + currentPlatform + "] OS");
				Assert.fail("The [" + browser + "]" + " explorer does not apply to  [" + currentPlatform + "] OS");
				return null;
			}
		}else{
			logger.error("The [" + browser + "]" + " explorer is not applicable  for  [" + currentPlatform + "] OS");
			Assert.fail("The [" + browser + "]" + " explorer does not apply to  [" + currentPlatform + "] OS");
			return null;
		}
		
	}
}
