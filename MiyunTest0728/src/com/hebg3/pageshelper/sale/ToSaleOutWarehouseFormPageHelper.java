package com.hebg3.pageshelper.sale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.hebg3.pages.sale.ToSaleOutWarehouseFormPage;
import com.hebg3.utils.SeleniumUtil;
import com.hebg3.utils.pagehelper.DialogPanel;
import com.hebg3.utils.pagehelper.JumpInto;
import com.hebg3.utils.pagehelper.PageHelperUtil;

public class ToSaleOutWarehouseFormPageHelper {
	
	public static Logger logger = LogManager.getLogger(ToSaleOutWarehouseFormPageHelper.class.getName());
	
	/**
	 * 选择用户.
	 * @param customerName
	 * 			用户名.
	 */
	public static void selectCustomer(SeleniumUtil seleniumUtil , String customerName) {
		logger.info("销售出库单：选择客户");
		//等待元素加载，并点击"选择客户"按钮
		seleniumUtil.waitForElementToLoad(5, ToSaleOutWarehouseFormPage.TSOWEP_BUTTON_COSTUMERNAME);
		seleniumUtil.click(ToSaleOutWarehouseFormPage.TSOWEP_BUTTON_COSTUMERNAME);
		//跳转选择客户页面，并选择客户
		DialogPanel.selectCustomer(seleniumUtil , "tSaleOutWarehouseForm", customerName);
		seleniumUtil.inFrame("contentIFRAMEtab_销售出库单");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 选择经手人.
	 * @param seleniumUtil
	 * @param handlerUser  经手人.
	 */
	public static void selectHandlerUser(SeleniumUtil seleniumUtil , String handlerUser) {
		logger.info("销售出库单：选择经手人");
		PageHelperUtil.selectByVisibleText(seleniumUtil, ToSaleOutWarehouseFormPage.TSOWFP_SELECT_HANDLERUSER, "handlerUser", handlerUser);
	}
	/**
	 * 选择送货日期.
	 * @param seleniumUtil
	 * @param dealdate  送货日期,建议写成YYYY-MM-DD格式.
	 */
	public static void selectDealDate(SeleniumUtil seleniumUtil , String dealdate) {
		logger.info("销售出库单：选择送货日期");
		PageHelperUtil.chooseDate(seleniumUtil, ToSaleOutWarehouseFormPage.TSOWFP_INPUT_DEALDATE, "dealdate", dealdate);
	}
	/**
	 * 选择填单日期.
	 * @param seleniumUtil
	 * @param orderdate  填单日期，建议写成YYYY-MM-DD格式.
	 */
	public static void selectOrderDate(SeleniumUtil seleniumUtil , String orderdate) {
		logger.info("销售出库单：选择填单日期");
		PageHelperUtil.chooseDate(seleniumUtil, ToSaleOutWarehouseFormPage.TSOWFP_INPUT_DEALDATE, "orderdate", orderdate);
	}
	/**
	 * 填写摘要.
	 * @param seleniumUtil
	 * @param remark  摘要内容.
	 */
	public static void remark(SeleniumUtil seleniumUtil , String remark) {
		logger.info("销售出库单：填写摘要");
		seleniumUtil.waitForElementToLoad(5, ToSaleOutWarehouseFormPage.TSOWFP_INPUT_REMARK);
		seleniumUtil.findElementBy(ToSaleOutWarehouseFormPage.TSOWFP_INPUT_REMARK).sendKeys(remark);
	}
	/**
	 * 添加商品，当前仅支持添加不超过13个商品.
	 * @param warehouse
	 * 			仓库名.
	 * @param goodsName
	 * 			商品名.
	 * @param goodsNum
	 * 			商品数量.
	 * @param goodsUnit
	 * 			商品销售单位.
	 * @param num
	 * 			商品序号，添加的第几个商品.
	 */
	public static void addProduct (SeleniumUtil seleniumUtil , String warehouse , String goodsName , 
			String goodsNum , String goodsUnit , String num) {
		logger.info("销售出库单：添加商品");
		//判断如果num >= 9，点击上一行的加号
		int number = Integer.valueOf(num);
		if (Integer.valueOf(num) >= 9) {
			seleniumUtil.findElementBy(By.xpath("//*[@id='salesTable']/tbody/tr[" + String.valueOf(number - 1) + "]/td[2]/a[1]"));
		}
		
		if (goodsName != "" && goodsName != null) {
			logger.info("销售出库单：添加商品——" + goodsName);
			//点击添加商品按钮(放大镜)
			String addProduct = "//*[@id='salesTable']/tbody/tr[" + num
					+ "]/td[3]/a/div/span[2]";
			seleniumUtil.findElementBy(By.xpath(addProduct)).click();
			DialogPanel.addProductByName(seleniumUtil,
					"tSaleOutWarehouseForm", goodsName);
			seleniumUtil.inFrame("contentIFRAMEtab_销售出库单");
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			//选择仓库
			if (warehouse != "" && warehouse != null) {
				logger.info("销售出库单：添加商品——选择仓库");
				By xpathOfWarehouse = By.xpath("//*[@id='salesTable']/tbody/tr[" + num+ "]/td[6]/a");
				By selectWarehouse = By.xpath("//*[@id='salesTable']/tbody/tr["+ num + "]/td[6]/span/div/form/div/div[1]/div/select");
				seleniumUtil.waitForElementToLoad(5, xpathOfWarehouse);
				seleniumUtil.click(xpathOfWarehouse);
				seleniumUtil.waitForElementToLoad(5, selectWarehouse);
				seleniumUtil.selectByText(selectWarehouse, warehouse);
			} else {
				//仓库名为空，不作操作
			}
			//选择商品销售单位
			if (goodsUnit != "" && goodsUnit != null) {
				By xpathOfUnit = By.xpath("//*[@id='salesTable']/tbody/tr[" + num + "]/td[8]/a");
				By selectUnit = By.xpath("//*[@id='salesTable']/tbody/tr["+ num + "]/td[8]/span/div/form/div/div[1]/div/select");
				seleniumUtil.waitForElementToLoad(5, xpathOfUnit);
				seleniumUtil.click(xpathOfUnit);
				seleniumUtil.waitForElementToLoad(5, selectUnit);
				seleniumUtil.selectByText(selectUnit, goodsUnit);
			} else {
				//商品销售单位为空，不作操作
			}
			//选择商品数量
			logger.info("销售出库单：添加商品——修改商品数量为" + goodsNum);
			By xpathOfProductNum = By.xpath("//*[@id='salesTable']/tbody/tr["+ num + "]/td[9]/a");
			By xpathOfProductNumInput = By.xpath("//*[@id='salesTable']/tbody/tr[" + num
							+ "]/td[9]/span/div/form/div/div[1]/div/input");
			seleniumUtil.click(xpathOfProductNum);
			seleniumUtil.waitForElementToLoad(2, xpathOfProductNumInput);
			seleniumUtil.type(xpathOfProductNumInput, goodsNum);
			seleniumUtil.click(By.xpath("//*[@id='salesTable']/tbody/tr[1]/td[1]"));
		} else {
			logger.info("销售出库单：商品名为空，未添加商品！");
		}
		logger.info("销售出库单：添加商品Success");
	}
	
	public static void submit(SeleniumUtil seleniumUtil){
		//数组下标从0开始  20180703
		seleniumUtil.submit(ToSaleOutWarehouseFormPage.TSOWFP_BUTTON_SUBMIT , "4");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	/**
	 * 快速填写销售出库单(不包含商品数量、单位).
	 * @param seleniumUtil
	 * @param customerName  客户名称.
	 * @param warehouse  仓库.
	 * @param goodsName_0    商品名称.
	 * @throws InterruptedException
	 */
	public static void typeOrderQuickly(SeleniumUtil seleniumUtil , String customerName , String warehouse , 
			String goodsName_0) throws InterruptedException {
		JumpInto.jumpInto(seleniumUtil, "销售", "销售出库单");
		selectCustomer(seleniumUtil, customerName);
		addProduct(seleniumUtil , warehouse , goodsName_0 , "1" , "" , "1");
		submit(seleniumUtil);
	}
	/**
	 * 快速填写销售出库单(包含商品数量、单位).
	 * @param seleniumUtil
	 * @param customerName  客户名称.
	 * @param warehouse  仓库.
	 * @param goodsName_0  商品名称.
	 * @param goodsNum  商品数量.
	 * @param goodsUnit  商品销售单位.
	 * @throws InterruptedException
	 */
	public static void typeOrderQuickly(SeleniumUtil seleniumUtil , String customerName , String warehouse , 
			String goodsName_0 , String goodsNum , String goodsUnit) throws InterruptedException {
		seleniumUtil.refresh();  //2018-07-09 19:30
		Thread.sleep(2000);
		JumpInto.jumpRefresh(seleniumUtil, "销售", "销售出库单");
		selectCustomer(seleniumUtil, customerName);
		addProduct(seleniumUtil , warehouse , goodsName_0 , goodsNum , goodsUnit , "1");
		submit(seleniumUtil);
	}
	public static void typeOrderQuickly(SeleniumUtil seleniumUtil , String customerName , String warehouse , 
			String goodsName_0 , String goodsName_1 , String goodsName_2 , String goodsName_3 , 
			String goodsName_4 , String goodsName_5 , String goodsName_6 , String goodsName_7) 
					throws InterruptedException {
		JumpInto.jumpRefresh(seleniumUtil, "销售", "销售出库单");
		selectCustomer(seleniumUtil, customerName);
		addProduct(seleniumUtil , warehouse , goodsName_0 , "1" , "" , "1");
		addProduct(seleniumUtil , warehouse , goodsName_1 , "1" , "" , "2");
		addProduct(seleniumUtil , warehouse , goodsName_2 , "1" , "" , "3");
		addProduct(seleniumUtil , warehouse , goodsName_3 , "1" , "" , "4");
		addProduct(seleniumUtil , warehouse , goodsName_4 , "1" , "" , "5");
		addProduct(seleniumUtil , warehouse , goodsName_5 , "1" , "" , "6");
		addProduct(seleniumUtil , warehouse , goodsName_6 , "1" , "" , "7");
		addProduct(seleniumUtil , warehouse , goodsName_7 , "1" , "" , "8");

		submit(seleniumUtil);
	}
}

