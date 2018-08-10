package com.hebg3.pageshelper.purchase;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.Assert;

import com.hebg3.pages.purchase.Document_PurchasePlant;
import com.hebg3.pages.purchase.Document_PurchaseReturn;
import com.hebg3.utils.SeleniumUtil;
import com.hebg3.utils.pagehelper.DialogPanel;
import com.hebg3.utils.pagehelper.JumpInto;
import com.hebg3.utils.pagehelper.PageHelperUtil;

public class Document_PurchaseReturnHelper {
	
	public static Logger logger = LogManager.getLogger(Document_PurchaseReturnHelper.class.getName());
	
	/**
	 * 填写采购退货单参数.
	 * @param seleniumUtil
	 * @param supplier  供应商.
	 * @param warehouse  退货仓库(必填).
	 * @param handlerUser  经手人.
	 * @param orderDate  单据日期(YYYY-MM-DD格式).
	 * @param remarks  摘要.
	 */
	public static void selectParams(SeleniumUtil seleniumUtil , String supplier , String warehouse , String handlerUser , 
			String orderDate , String remarks) {
		if (StringUtils.isBlank(supplier) == false) {
			PageHelperUtil.selectByVisibleText(seleniumUtil, Document_PurchaseReturn.TPR_SELECT_SUPPLIER, "supplierId", supplier);
		}
		if (StringUtils.isBlank(warehouse) == false) {
			PageHelperUtil.selectByVisibleText(seleniumUtil, Document_PurchaseReturn.TPR_SELECT_WAREHOUSE, "warehouseId", warehouse);
		} else {
			logger.error("采购退货单未选择退货仓库，用例执行失败");
			Assert.fail("采购退货单未选择退货仓库，用例执行失败");
		}
		if (StringUtils.isBlank(handlerUser) == false) {
			PageHelperUtil.selectByVisibleText(seleniumUtil, Document_PurchaseReturn.TPR_SELECT_HANDLERUSER, "handlerUserId", handlerUser);
		}
		if (StringUtils.isBlank(orderDate) == false) {
			PageHelperUtil.chooseDate(seleniumUtil, Document_PurchaseReturn.TPR_INPUT_ORDERDATE, "orderDate", orderDate);
		}
		if (StringUtils.isBlank(remarks) == false) {
			//warehouse必填，所以不必设置元素等待
			seleniumUtil.type(Document_PurchaseReturn.TPR_INPUT_REMARKS, remarks);
		}
	}
	/**
	 * 添加商品.
	 * @param seleniumUtil
	 * @param goodsName  商品名称.
	 * @param goodsNum  退货数量.
	 * @param goodsUnit  退货单位.
	 * @param num  添加的商品序号(添加的第几个商品).
	 */
	public static void addProduct(SeleniumUtil seleniumUtil , String goodsName , String goodsNum , String goodsUnit , String num) {
		//判断num >= 9 ， 若不小于9，点击上一行加号
		int number = Integer.valueOf(num);
		if (Integer.valueOf(num) >= 9) {
			seleniumUtil.click(By.xpath("//*[@id='tableDocumentReturnDetail']/tbody/tr[" + String.valueOf(number - 1) + "]/td[2]/a[1]"));
		}
		
		String addProduct = "//*[@id='tableDocumentReturnDetail']/tbody/tr[" + num + "]/td[3]/a/div/span[2]";
		seleniumUtil.click(By.xpath(addProduct));
		
		DialogPanel.addProductByName(seleniumUtil , "purchaseReturnForm", goodsName);
		seleniumUtil.inFrame("contentIFRAMEtab_采购退货单");
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		//选择退货商品单位
		By xpathOfGoodUnit = By.xpath("//*[@id='tableDocumentReturnDetail']/tbody/tr[" + num + "]/td[9]/a");
		By selectGoodUnit = By.xpath("//*[@id='tableDocumentReturnDetail']/tbody/tr[" + num + "]/td[9]/span/div/form/div/div[1]/div/select");
		seleniumUtil.waitForElementToLoad(5, xpathOfGoodUnit);
		seleniumUtil.click(xpathOfGoodUnit);
		seleniumUtil.waitForElementToLoad(5, selectGoodUnit);
		seleniumUtil.selectByText(selectGoodUnit, goodsUnit);
		
		//选择退货商品数量
		By xpathOfProductNum = By.xpath("//*[@id='tableDocumentReturnDetail']/tbody/tr[" + num + "]/td[10]/a");
		By xpathOfProductNumInput = By.xpath("//*[@id='tableDocumentReturnDetail']/tbody/tr[" + num + "]/td[10]/span/div/form/div/div[1]/div/input");
		seleniumUtil.click(xpathOfProductNum);
		seleniumUtil.waitForElementToLoad(2, xpathOfProductNumInput);
		seleniumUtil.type(xpathOfProductNumInput, goodsNum);
		seleniumUtil.click(By.xpath("//*[@id='tableDocumentReturnDetail']/tbody/tr[1]/td[1]"));
	}
	
	public static void submit(SeleniumUtil seleniumUtil) {
		seleniumUtil.submit(Document_PurchaseReturn.TPR_BUTTON_SAVE, "4");
	}
	/**
	 * 快速填写采购退货单.
	 * @param seleniumUtil
	 * @param supplier  供应商.
	 * @param warehouse  退货仓库.
	 * @param handlerUser  经手人.
	 * @param goodsName  商品名称.
	 * @param goodsNum  商品数量.
	 * @param goodsUnit  商品采购单位.
	 * @return 单据内容(第一条商品).
	 * @throws InterruptedException 
	 */
	public static void typeOrderQuickly(SeleniumUtil seleniumUtil , String supplier , String warehouse , String handlerUser , 
			String goodsName , String goodsNum , String goodsUnit) throws InterruptedException {
		JumpInto.jumpInto(seleniumUtil, "采购", "采购退货单");
		selectParams(seleniumUtil , supplier , warehouse , handlerUser , "" , "");
		addProduct(seleniumUtil , goodsName , goodsNum , goodsUnit , "1");
		Map<String , String> map = new HashMap<String , String>();
		PageHelperUtil.recordData(seleniumUtil , map , Document_PurchaseReturn.TPR_TABLE_DATA , 3 , 11);
		submit(seleniumUtil);
	}
}
