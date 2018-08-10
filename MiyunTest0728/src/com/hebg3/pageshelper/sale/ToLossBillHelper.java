package com.hebg3.pageshelper.sale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.hebg3.pages.purchase.ToGeneratePurchaseOrder;
import com.hebg3.pages.sale.ToLossBillPage;
import com.hebg3.pages.sale.ToSaleOutWarehouseFormPage;
//import com.hebg3.utils.PageHelperUtil;
import com.hebg3.utils.SeleniumUtil;
import com.hebg3.utils.pagehelper.DialogPanel;
import com.hebg3.utils.pagehelper.JumpInto;
import com.hebg3.utils.pagehelper.PageHelperUtil;

/**
 * @author Li
 * 损益单
 */
public class ToLossBillHelper {
	
	public static Logger logger = LogManager.getLogger(ToLossBillHelper.class.getName());
	
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
	 * 报损单商品选择页面：通过商品名选择商品.<br>
	 * 
	 * @param pageName
	 * 			indexDialogPanel/divId中"_addProduct-index"的前缀.
	 * @param productName
	 * 			要添加的商品名，尽量填写全称.
	 */
	public static void addProductByToLossBill(SeleniumUtil seleniumUtil , String pageName , String productName) {
		//SeleniumUtil seleniumUtil = new SeleniumUtil();
		//商品名称输入框
		String xpathOfSearchGoodsName = "//*[@id='" + pageName + "_addProduct-index']/div/div/div[2]/div/div[2]/div[1]/div[1]/form/div/section/div/label[2]/input";
		//查询按钮
		String xpathOfSearchGoodsBtn = "//*[@id='" + pageName + "_addProduct-index']/div/div/div[2]/div/div[2]/div[1]/div[2]/button[1]";
		//表单中的商品名xpath路径
		//String xpathOfGoodsName = "//*[@id='" + pageName + "_addProduct-index']/div/div/div[2]/div/div[2]/div[2]/div[1]/div[2]/div[2]/table/tbody/tr[1]/td[2]";
		//复选框
		String xpathOfCheck = "//*[@id='goodsTable']/tbody/tr/td[1]/input";
		//跳转到默认frame,并等待选择客户页面加载出来,后期需改为显式等待
		seleniumUtil.outFrame();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		seleniumUtil.waitForElementToLoad(5, By.xpath(xpathOfSearchGoodsName));
		//输入商品名，点击查询按钮
		seleniumUtil.findElementBy(By.xpath(xpathOfSearchGoodsName)).clear();
		seleniumUtil.findElementBy(By.xpath(xpathOfSearchGoodsName)).sendKeys(productName);
		seleniumUtil.findElementBy(By.xpath(xpathOfSearchGoodsBtn)).click();
		//强制等待，之后等待元素加载，获取第一行的商品
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		seleniumUtil.findElementBy(By.xpath(xpathOfCheck)).click();
		//点击确认按钮，关闭页面
		seleniumUtil.findElementBy(By.id("inventoryConfirm")).click();
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
		logger.info("报损单：添加商品");
		//判断如果num >= 9，点击上一行的加号
		int number = Integer.valueOf(num);
		if (Integer.valueOf(num) >= 9) {
			seleniumUtil.findElementBy(By.xpath("//*[@id='salesTable']/tbody/tr[" + String.valueOf(number - 1) + "]/td[2]/a[1]"));
		}
		
		if (goodsName != "" && goodsName != null) {
			logger.info("报损单：添加商品——" + goodsName);
			//点击添加商品按钮(放大镜)
			String addProduct = "//*[@id='loseOverflowTable']/tbody/tr[" + num + "]/td[3]/a/div/span[2]";			
			//seleniumUtil.findElementBy(By.xpath(addProduct)).click();
			seleniumUtil.waitForElementToLoad(5, By.xpath(addProduct));
			seleniumUtil.click(By.xpath(addProduct));
			ToLossBillHelper.addProductByToLossBill(seleniumUtil, "tLoseForm", goodsName); 
			seleniumUtil.inFrame("contentIFRAMEtab_报损单");
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
				By xpathOfUnit = By.xpath("//*[@id='loseOverflowTable']/tbody/tr[" + num + "]/td[6]/a");
				By selectUnit = By.xpath("//*[@id='loseOverflowTable']/tbody/tr["+ num + "]/td[6]/span/div/form/div/div[1]/div/select");
				seleniumUtil.waitForElementToLoad(5, xpathOfUnit);
				seleniumUtil.click(xpathOfUnit);
				seleniumUtil.waitForElementToLoad(5, selectUnit);
				seleniumUtil.selectByText(selectUnit, goodsUnit);
			} else {
				//商品销售单位为空，不作操作
			}
			//选择商品数量
			logger.info("报损单：添加商品——修改商品数量为" + goodsNum);
			By xpathOfProductNum = By.xpath("//*[@id='loseOverflowTable']/tbody/tr["+ num + "]/td[7]/a");
			By xpathOfProductNumInput = By.xpath("//*[@id='loseOverflowTable']/tbody/tr[" + num + "]/td[7]/span/div/form/div/div[1]/div/input");
			seleniumUtil.click(xpathOfProductNum);
			seleniumUtil.waitForElementToLoad(2, xpathOfProductNumInput);
			seleniumUtil.type(xpathOfProductNumInput, goodsNum);
			seleniumUtil.click(By.xpath("//*[@id='loseOverflowTable']/tbody/tr[1]/td[1]"));
		} else {
			logger.info("报损单：商品名为空，未添加商品！");
		}
		logger.info("报损单：添加商品Success");
	}
	
	public static void submit(SeleniumUtil seleniumUtil){
		//数组下标从0开始  20180703
		seleniumUtil.submit(ToLossBillPage.TSOWFP_BUTTON_SUBMIT , "4");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	/**
	 * 选择仓库.
	 * @param seleniumUtil
	 * @param warehouse  仓库.
	 */
	public static void selectWareHouse(SeleniumUtil seleniumUtil , String warehouse) {
		PageHelperUtil.selectByVisibleText(seleniumUtil, ToLossBillPage.TSOWFP_SELECT_WAREHOUSE, "warehouse", warehouse);
	}	

	/**
	 * 手动收入添加商品
	 * @author Li
	 * @param seleniumUtil
	 * @param goodsName 	商品名.
	 * @param goodsNum		商品数量.
	 * @param goodsUnit		商品单位.
	 * @param num 			商品序号，添加的第几个商品.
	 */
	public static void addProductByText (SeleniumUtil seleniumUtil , String goodsName , String goodsNum , String goodsUnit , String num) {
		logger.info("报损单：添加商品");
		//判断如果num >= 9，点击上一行的加号
		int number = Integer.valueOf(num);
		if (Integer.valueOf(num) >= 9) {
			seleniumUtil.findElementBy(By.xpath("//*[@id='salesTable']/tbody/tr[" + String.valueOf(number - 1) + "]/td[2]/a[1]"));
		}
		
		if (goodsName != "" && goodsName != null) {
			logger.info("报损单：添加商品——" + goodsName);
			//点击输入商品名称
			By xpathOfProduct = By.xpath("//*[@id='loseOverflowTable']/tbody/tr["+ num + "]/td[3]/a");
			By xpathOfProductInput = By.xpath("//*[@id='loseOverflowTable']/tbody/tr[" + num + "]/td[3]/span/div/form/div/div[1]/div/span[1]/input[2]");
			seleniumUtil.click(xpathOfProduct);
			seleniumUtil.waitForElementToLoad(2, xpathOfProductInput);
			seleniumUtil.type(xpathOfProductInput, goodsName);
			seleniumUtil.click(By.xpath("//*[@id='loseOverflowTable']/tbody/tr[1]/td[3]/span/div/form/div/div[1]/div/span[1]/div/div/div/div[1]"));
			seleniumUtil.click(By.xpath("//*[@id='loseOverflowTable']/tbody/tr[1]/td[1]"));
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			//选择商品单位
			if (goodsUnit != "" && goodsUnit != null) {
				By xpathOfUnit = By.xpath("//*[@id='loseOverflowTable']/tbody/tr[" + num + "]/td[6]/a");
				By selectUnit = By.xpath("//*[@id='loseOverflowTable']/tbody/tr["+ num + "]/td[6]/span/div/form/div/div[1]/div/select");
				seleniumUtil.waitForElementToLoad(5, xpathOfUnit);
				seleniumUtil.click(xpathOfUnit);
				seleniumUtil.waitForElementToLoad(5, selectUnit);
				seleniumUtil.selectByText(selectUnit, goodsUnit);
			} else {
				//商品销售单位为空，不作操作
			}
			//选择商品数量
			logger.info("报损单：添加商品——修改商品数量为" + goodsNum);
			By xpathOfProductNum = By.xpath("//*[@id='loseOverflowTable']/tbody/tr["+ num + "]/td[7]/a");
			By xpathOfProductNumInput = By.xpath("//*[@id='loseOverflowTable']/tbody/tr[" + num + "]/td[7]/span/div/form/div/div[1]/div/input");
			seleniumUtil.click(xpathOfProductNum);
			seleniumUtil.waitForElementToLoad(2, xpathOfProductNumInput);
			seleniumUtil.type(xpathOfProductNumInput, goodsNum);
			seleniumUtil.click(By.xpath("//*[@id='loseOverflowTable']/tbody/tr[1]/td[1]"));
		} else {
			logger.info("报损单：商品名为空，未添加商品！");
		}
		logger.info("报损单：添加商品Success");
	}
	

	/**
	 * 快速填写报损单(包含商品数量、单位).
	 * @param seleniumUtil
	 * @param warehouse  仓库.
	 * @param handlerUser 经手人.
	 * @param goodsName_0  商品名称.
	 * @param goodsNum  报损数量.
	 * @param goodsUnit  单位.
	 * @param LoseoverFlag  1:报损    0:报溢.
	 * @throws InterruptedException
	 * 
	 */
	public static void typeOrderQuickly(SeleniumUtil seleniumUtil , String warehouse , String handlerUser , 
			String goodsName_0 , String goodsNum , String goodsUnit, int LoseoverFlag) throws InterruptedException {
		if (LoseoverFlag==1)
		{ 
			JumpInto.jumpInto(seleniumUtil, "库存", "报损单");
		}
		else
		{
			JumpInto.jumpInto(seleniumUtil, "库存", "报溢单");
		}
		selectWareHouse(seleniumUtil , warehouse);
		selectHandlerUser(seleniumUtil , handlerUser);
		/*点击放大镜添加商品*/
		//addProduct(seleniumUtil , "" , goodsName_0 , goodsNum , "" , "1");
		/*手动输入添加商品*/
		addProductByText(seleniumUtil , goodsName_0 , goodsNum , "" , "1");
		submit(seleniumUtil);
	}
	

}

