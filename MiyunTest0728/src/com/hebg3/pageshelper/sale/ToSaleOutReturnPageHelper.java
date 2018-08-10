package com.hebg3.pageshelper.sale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.Assert;

import com.hebg3.pages.sale.ToSaleOutReturnPage;
import com.hebg3.utils.SeleniumUtil;
import com.hebg3.utils.pagehelper.DialogPanel;
import com.hebg3.utils.pagehelper.JumpInto;
import com.hebg3.utils.pagehelper.PageHelperUtil;

public class ToSaleOutReturnPageHelper {
	public static Logger logger = LogManager.getLogger(ToSaleOutReturnPageHelper.class.getName());
	
	/**
	 * 填写销售退货单的参数.
	 * @param seleniumUtil
	 * @param customer  客户名称(必填项).
	 * @param warehouse  仓库名(必填项).
	 * @param handlerUser  经手人.
	 * @param dealdate  收货日期(YYYY-MM-DD格式).
	 * @param orderdate  填单日期(YYYY-MM-DD格式).
	 * @param remarks  摘要.
	 */
	public static void selectParams(SeleniumUtil seleniumUtil , String customer , String warehouse , 
			String handlerUser , String dealdate , String orderdate , String remarks) {
		if (customer != "" && customer != null) {
			seleniumUtil.waitForElementToLoad(5, ToSaleOutReturnPage.TSORWFP_BUTTON_CUSTOMERNAME);
			seleniumUtil.click(ToSaleOutReturnPage.TSORWFP_BUTTON_CUSTOMERNAME);
			DialogPanel.selectCustomer(seleniumUtil, "tSaleReturnWarehouseForm", customer);
			seleniumUtil.inFrame("contentIFRAMEtab_销售退货单");
		} else {
			logger.warn("销售退货单——客户名称为必填项！");
			Assert.fail("销售退货单——客户名称为必填项！");
		}
		if (warehouse != "" && warehouse != null) {
			PageHelperUtil.selectByVisibleText(seleniumUtil, ToSaleOutReturnPage.TSORWFP_SELECT_WAREHOUSE, "warehouse", warehouse);
		} else {
			logger.warn("销售退货单——退货仓库为必填项！");
			Assert.fail("销售退货单——退货仓库为必填项！");
		}
		if (handlerUser != "" && handlerUser != null) {
			PageHelperUtil.selectByVisibleText(seleniumUtil, ToSaleOutReturnPage.TSORWFP_SELECT_HANDLERUSER, "handlerUser", handlerUser);
		}
		if (dealdate != "" && dealdate != null) {
			PageHelperUtil.chooseDate(seleniumUtil, ToSaleOutReturnPage.TSORWFP_INPUT_DEALDATE, "dealdate", dealdate);
		}
		if (orderdate != "" && orderdate != null) {
			PageHelperUtil.chooseDate(seleniumUtil, ToSaleOutReturnPage.TSORWFP_INPUT_ORDERDATE, "orderdate", orderdate);
		}
		if (remarks != "" && remarks != null) {
			seleniumUtil.type(ToSaleOutReturnPage.TSORWFP_INPUT_REMARK, remarks);
		}
	}
	/**
	 * 添加商品.
	 * @param seleniumUtil
	 * @param goodsName  商品名.
	 * @param goodsNum  商品数量.
	 * @param goodsUnit  商品销售单位.
	 * @param num  商品序号(添加的第几个商品).
	 */
	public static void addProduct(SeleniumUtil seleniumUtil , String goodsName , String goodsNum , String goodsUnit , String num) {
		//判断goodsNum，如果商品序号大于9，点击上一行加号
		int number = Integer.valueOf(num);
		if (Integer.valueOf(num) >= 9) {
			seleniumUtil.click(By.xpath("//*[@id='salesTable']/tbody/tr[" + (num+1) + "]/td[2]/a[1]"));
		}
		
		//点击添加商品按钮(放大镜)
		String addProduct = "//*[@id='salesTable']/tbody/tr[" + num + "]/td[3]/a/div/span[2]";
		seleniumUtil.click(By.xpath(addProduct));
		
		DialogPanel.addProductByName(seleniumUtil, "tSaleReturnWarehouseForm", goodsName);
		seleniumUtil.inFrame("contentIFRAMEtab_销售退货单");
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//修改商品销售单位
		By xpathOfUnit = By.xpath("//*[@id='salesTable']/tbody/tr[" + num + "]/td[7]/a");
		By selectUnit = By.xpath("//*[@id='salesTable']/tbody/tr[" + num + "]/td[7]/span/div/form/div/div[1]/div/select");
		seleniumUtil.waitForElementToLoad(2, xpathOfUnit);
		seleniumUtil.click(xpathOfUnit);
		seleniumUtil.waitForElementToLoad(2, selectUnit);
		seleniumUtil.selectByText(selectUnit, goodsUnit);
		
		//添加商品数量
		By xpathOfProductNum = By.xpath("//*[@id='salesTable']/tbody/tr[" + num + "]/td[8]/a");
		By xpathOfProductNumInput = By.xpath("//*[@id='salesTable']/tbody/tr[" + num + "]/td[8]/span/div/form/div/div[1]/div/input");
		seleniumUtil.click(xpathOfProductNum);
		seleniumUtil.waitForElementToLoad(2, xpathOfProductNumInput);
		seleniumUtil.type(xpathOfProductNumInput, String.valueOf(goodsNum));
		seleniumUtil.click(By.xpath("//*[@id='salesTable']/tbody/tr[1]/td[1]"));
	}
	public static void submit(SeleniumUtil seleniumUtil) {
		seleniumUtil.submit(ToSaleOutReturnPage.TSORWFP_BUTTON_SUBMIT, "4");
	}
	public static void draft(SeleniumUtil seleniumUtil) {
		
	}
	/**
	 * 快速填写销售退货单.
	 * @param seleniumUtil
	 * @param customer  客户名称.
	 * @param warehouse  退货仓库.
	 * @param handlerUser  经手人.
	 * @param goodsName  商品名称.
	 * @param goodsNum  商品数量.
	 * @param goodsUnit  商品销售单位.
	 * @throws InterruptedException
	 */
	public static void typeOrderQuickly(SeleniumUtil seleniumUtil , String customer , String warehouse , String handlerUser , 
			String goodsName , String goodsNum , String goodsUnit) throws InterruptedException{
		JumpInto.jumpInto(seleniumUtil, "销售", "销售退货单");
		selectParams(seleniumUtil , customer , warehouse , handlerUser , "" , "" , "");
		addProduct(seleniumUtil , goodsName , goodsNum , goodsUnit , "1");
		submit(seleniumUtil);
	}
}
