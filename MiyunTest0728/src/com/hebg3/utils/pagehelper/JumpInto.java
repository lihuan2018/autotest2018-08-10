package com.hebg3.utils.pagehelper;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;

import com.hebg3.utils.SeleniumUtil;

public class JumpInto {
	
	public static void jumpInto(SeleniumUtil seleniumUtil , String moduleName , String pageName) throws InterruptedException {
		By tabElement = By.id("tab_tab_" + pageName);
		String frameId = "contentIFRAMEtab_" + pageName;
		
		try {
			//跳转到指定页面
			JumpInto.jump(seleniumUtil , moduleName , pageName , tabElement , frameId);
						
			seleniumUtil.inFrame(frameId);
		} catch (NoSuchElementException e) {
			//跳转失败后，浏览器刷新，再次尝试跳转，再次失败后，用例执行失败
			PageHelperUtil.logger.warn("未找到该页面，浏览器刷新:" + pageName);
			seleniumUtil.refresh();
			try {
				JumpInto.jump(seleniumUtil , moduleName , pageName , tabElement , frameId);
			} catch (NoSuchElementException e1) {
				Assert.fail("未找到" + pageName + "页面");
				PageHelperUtil.logger.error("未找到" + pageName + "页面,用例执行失败");
			}//嵌套try catch结束
		}//try catch结束
	}

	public static void jumpRefresh(SeleniumUtil seleniumUtil , String moduleName , String pageName) throws InterruptedException {
		By tabElement = By.id("tab_tab_" + pageName);
		String frameId = "contentIFRAMEtab_" + pageName;
		int refreshCount = 1;
		try {
			//跳转并刷新页面
			jump(seleniumUtil , moduleName , pageName , tabElement , frameId);
			//以上，若改为jumpInto()，会出现错误，下面部分无法执行，原因未知 2018-07-05 23:15
			//jump()代码在该处添加，使用正常，若使用jumpInto()，会出现错误，无法查找到元素tabElement. 2018-07-06 09:02
			
				seleniumUtil.mouseRightClick(tabElement);
				seleniumUtil.waitForElementToLoad(5, By.id("refreshSelf"));
				seleniumUtil.click(By.id("refreshSelf"));
				PageHelperUtil.logger.info("刷新页面:" + refreshCount);
			
			seleniumUtil.inFrame(frameId);
		} catch (NoSuchElementException e) {
			//跳转失败后，浏览器刷新，再次尝试跳转，再次失败后，用例执行失败    2018-07-13
			PageHelperUtil.logger.warn("未找到该页面，浏览器刷新:" + pageName);
			seleniumUtil.refresh();
			
			try {
				jump(seleniumUtil , moduleName , pageName , tabElement , frameId);
			} catch (NoSuchElementException e1) {
				Assert.fail("未找到" + pageName + "页面");
				PageHelperUtil.logger.error("未找到" + pageName + "页面,用例执行失败");
			}//嵌套try catch结束
		}//try catch结束
	}
	
	
	/**
	 * 内部调用，用于跳转页面.
	 * @param seleniumUtil
	 * @param moduleName
	 * @param pageName
	 * @param tabElement
	 * @param frameId
	 */
	static void jump(SeleniumUtil seleniumUtil , String moduleName , String pageName , By tabElement , 
			String frameId) {
		seleniumUtil.outFrame();
		PageHelperUtil.logger.info("跳转到" + pageName + "页面");
		seleniumUtil.waitForElementToLoad(5, By.linkText(moduleName));
		seleniumUtil.mouseMoveToElement(By.linkText(moduleName));
		seleniumUtil.waitForElementToLoad(5, By.linkText(pageName));
		seleniumUtil.click(By.linkText(pageName));
		seleniumUtil.mouseMoveToElement(By.id("logo"));
		//Thread.sleep(500);
		seleniumUtil.waitForElementToLoad(4, tabElement);
		PageHelperUtil.logger.info("跳转页面成功");
	}

}
