package com.hebg3.base;
/**
 * @Description 测试开始 和 测试结束 的操作
 * 
 * */
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import com.hebg3.pages.purchase.HomePage;
import com.hebg3.utils.LogConfiguration;
import com.hebg3.utils.PropertiesDataProvider;
import com.hebg3.utils.SeleniumUtil;
public class BaseParpare {
    //输出本页面日志 初始化
    static Logger logger = LogManager.getLogger(BaseParpare.class.getName());
    protected SeleniumUtil seleniumUtil = null;
    // 添加成员变量来获取beforeClass传入的context参数
    protected ITestContext testContext = null;
    protected String webUrl="";
    protected int timeOut = 100;
    protected String username;
    protected String password;
    
    @BeforeClass(alwaysRun=true)
    /**启动浏览器并打开测试页面*/
    public void startTest(ITestContext context) throws FileNotFoundException {
        LogConfiguration.initLog(this.getClass().getSimpleName());
        seleniumUtil = new SeleniumUtil();
        // 这里得到了context值
        this.testContext = context;
        //从testng.xml文件中获取浏览器的属性值
        String browserName = context.getCurrentXmlTest().getParameter("browserName");
        //从testng.xml文件中读取登录数据所在的文件路径
        String paramFilePath = context.getCurrentXmlTest().getParameter("paramFilePath");//"config/paramsProvider/csjxs.miyunplus.properties";
        //从.properties文件中读取登录数据
        webUrl = PropertiesDataProvider.getTestData(paramFilePath , "webUrl");
        username = PropertiesDataProvider.getTestData(paramFilePath , "username");
        password = PropertiesDataProvider.getTestData(paramFilePath , "password");
        try {
            //启动浏览器(打开浏览器，输入测试地址，并最大化窗口)
            seleniumUtil.launchBrowser(browserName , context , webUrl , timeOut);
        } catch (Exception e) {
            logger.error("浏览器不能正常工作，请检查是不是被手动关闭或者其他原因",e);
        }
        try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
        seleniumUtil.findElementBy(By.id("username")).sendKeys(username);
        seleniumUtil.findElementBy(By.id("password")).sendKeys(password);
        seleniumUtil.findElementBy(By.className("pull-right")).click();
        try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
        //判断登录是否成功
        String uName = seleniumUtil.findElementBy(HomePage.HP_TEXT_USERNAME).getText();
        if (uName.contains("你好")) {
        	logger.info("登录成功:" + uName);
        } else {
        	seleniumUtil.quit();
        	logger.error("登录失败,频繁登录有验证码，或因其他原因登录失败！");
        	Assert.fail("登录失败,频繁登录有验证码，或因其他原因登录失败！");
        }
        //设置一个testng上下文属性，将driver存起来，之后可以使用context随时取到，主要是提供arrow 获取driver对象使用的，因为arrow截图方法需要一个driver对象
        testContext.setAttribute("SELENIUM_DRIVER", seleniumUtil.driver);
    }
    

    @AfterClass(alwaysRun=true)
    /**结束测试关闭浏览器*/
    public void endTest() {
        if (seleniumUtil.driver != null) {
            //退出浏览器
            seleniumUtil.quit();
        } else {
            logger.error("浏览器driver没有获得对象,退出操作失败");
            Assert.fail("浏览器driver没有获得对象,退出操作失败");
        }
    }
}