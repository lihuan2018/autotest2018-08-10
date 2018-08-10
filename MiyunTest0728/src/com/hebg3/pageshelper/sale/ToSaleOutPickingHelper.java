package com.hebg3.pageshelper.sale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.Assert;

import com.hebg3.pages.sale.ToSaleOutPicking;
import com.hebg3.pages.sale.ToPrintPicking;
import com.hebg3.utils.SeleniumUtil;
import com.hebg3.utils.pagehelper.PageHelperUtil;

public class ToSaleOutPickingHelper {
	public static Logger logger = LogManager.getLogger(ToSaleOutPickingHelper.class.getName());

	/**
	 * 选择第一张销售单，生成拣货单，并指定配送人.
	 * @param seleniumUtil
	 * @param dispatchingUser  配送人.
	 */
	public static void picking(SeleniumUtil seleniumUtil , String dispatchingUser) {
		picking(seleniumUtil , dispatchingUser , 0);
	}
	/**
	 * 批量选择销售单生成拣货单，并指定配送人.
	 * @param seleniumUtil
	 * @param dispatchingUser  配送人.
	 * @param num  选择前num张销售单(目前支持1-10).
	 */
	public static void batchPicking(SeleniumUtil seleniumUtil , String dispatchingUser , int num) {
		if (num > 10 || num < 1) {
			logger.error("批量拣货选择销售单数量错误，num参数仅支持1-10");
			Assert.fail("批量拣货选择销售单数量错误，num参数仅支持1-10");
		}
		
		picking(seleniumUtil , dispatchingUser , 1);
	}
	/**
	 * 批量选择前10张(第1页全部)销售单，生成拣货单并指定配送人.
	 * @param seleniumUtil
	 * @param dispatchingUser  配送人.
	 */
	public static void pickingAll(SeleniumUtil seleniumUtil , String dispatchingUser) {
		picking(seleniumUtil , dispatchingUser , 2);
	}
	
	/**
	 * 内部调用，生成拣货单主流程.
	 * @param seleniumUtil
	 * @param dispatchingUser  配送人.
	 * @param num
	 */
	static void picking(SeleniumUtil seleniumUtil , String dispatchingUser , int num) {
		//等待表单加载
		String xpathOfFirstCheck = "//*[@id='saleHistoryTable']/tbody/tr[1]/td[1]/input";
		seleniumUtil.waitForElementToLoad(5, By.xpath(xpathOfFirstCheck));
		
		//根据业务类型，勾选销售出库单
		switch (num) {
		case 0 :
			logger.info("生成拣货单：勾选销售单");
			seleniumUtil.click(By.xpath(xpathOfFirstCheck));
			break;
		case 1 :
			for (int i = 1 ; i <= num ; i ++) {
				logger.info("生成拣货单：勾选第" + i + "张销售单(共" + num + "张)");
				String xpathOfCheck = "//*[@id='saleHistoryTable']/tbody/tr[" + i + "]/td[1]/input";
				seleniumUtil.click(By.xpath(xpathOfCheck));
			}
			break;
		case 2 :
			logger.info("生成拣货单：勾选第一页销售单");
			seleniumUtil.click(ToSaleOutPicking.TSOP_INPUT_SELECTALL);
			break;
		}
		
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		//点击“拣货”按钮
		logger.info("生成拣货单：生成拣货单");
		seleniumUtil.click(ToSaleOutPicking.TSOP_BUTTON_PICKING);
		//跳转iframe
		seleniumUtil.outFrame();
		seleniumUtil.waitForElementToLoad(5, By.id("contentIFRAMEtab_拣货单生成_预览"));
		seleniumUtil.inFrame("contentIFRAMEtab_拣货单生成_预览");
		//点击保存，选择配送人,点击保存按钮
		logger.info("生成拣货单：选择配送人，生成拣货单");
		seleniumUtil.waitForElementToLoad(5, ToPrintPicking.TPP_BUTTON_SAVE);
		seleniumUtil.click(ToPrintPicking.TPP_BUTTON_SAVE);
		PageHelperUtil.selectByVisibleText_model(seleniumUtil, ToPrintPicking.TPP_SELECT_USER, "dispatchingUser", dispatchingUser);
		String JS = "document.getElementById('pickingSave').click()";
		seleniumUtil.executeJS(JS);
		logger.info("生成拣货单：生成拣货单Success");
	}
}
