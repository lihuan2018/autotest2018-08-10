package com.hebg3.pageshelper.purchase;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.Assert;

import com.hebg3.pages.purchase.Document_Purchase;
import com.hebg3.utils.SeleniumUtil;
import com.hebg3.utils.pagehelper.DialogPanel;
import com.hebg3.utils.pagehelper.JumpInto;
import com.hebg3.utils.pagehelper.PageHelperUtil;

/**采购入库单*/
public class Document_PurchaseHelper {
	
	public static Logger logger = LogManager.getLogger(PageHelperUtil.class.getName());
	
	/**
	 * 填写采购入库单的参数.
	 * @param seleniumUtil 
	 * @param supplier 供应商.
	 * @param warehouse 收货仓库.
	 * @param handlerUser 经手人.
	 * @param orderDate 单据日期.
	 * @param remarks 摘要.
	 */
	public static void selectParams (SeleniumUtil seleniumUtil , String supplier , String warehouse , 
			String handlerUser , String orderDate , String remarks) {
		if (StringUtils.isBlank(supplier) == false) {
			logger.info("选择供应商：" + supplier);
			PageHelperUtil.selectByVisibleText(seleniumUtil, Document_Purchase.TPIW_SELECT_SUPPLIER, "supplierId", supplier);
		}
		if (StringUtils.isBlank(warehouse) == false) {
			logger.info("选择收货仓库：" + warehouse);
			PageHelperUtil.selectByVisibleText(seleniumUtil, Document_Purchase.TPIW_SELECT_WAREHOUSE, "warehouseId", warehouse);
		} else {
			logger.error("采购入库单未选择退货仓库，用例执行失败");
			Assert.fail("采购入库单未选择退货仓库，用例执行失败");
		}
		if (StringUtils.isBlank(handlerUser) == false) {
			logger.info("选择经手人：" + handlerUser);
			PageHelperUtil.selectByVisibleText(seleniumUtil, Document_Purchase.TPIW_SELECT_HANDLERUSER, "handlerUserId", handlerUser);
		}
		if (StringUtils.isBlank(orderDate) == false) {
			logger.info("选择单据日期：" + orderDate);
			PageHelperUtil.chooseDate(seleniumUtil, Document_Purchase.TPIW_INPUT_ORDERDATE, "orderDate", orderDate);
		}
		if (StringUtils.isBlank(remarks) == false) {
			logger.info("填写摘要：" + remarks);
			seleniumUtil.type(Document_Purchase.TPIW_INPUT_REMARKS, remarks);
		}
	}
	/**
	 * 添加商品.
	 * @param seleniumUtil
	 * @param goodsName 商品名.
	 * @param goodsNum  商品数量.
	 * @param goodsUnit  商品采购单位.
	 * @param num 商品序号(添加的第几个商品).
	 */
	public static void addProduct (SeleniumUtil seleniumUtil , String goodsName , String goodsNum ,
			String goodsUnit , String num) {
		//判断如果num >= 9，点击上一行的加号
		int number = Integer.valueOf(num);
		if (Integer.valueOf(num) >= 9) {
			logger.info("商品序号大于8，添加商品行");
			seleniumUtil.click(By.xpath("//*[@id='tableDocumentDetail']/tbody/tr[" + String.valueOf(number - 1) + "]/td[2]/a[1]"));
		}
		
		//点击添加商品按钮(放大镜)
		logger.info("添加商品：" + goodsName);
		String addProduct = "//*[@id='tableDocumentDetail']/tbody/tr[" + num + "]/td[3]/a/div/span[2]";
		seleniumUtil.waitForElementToLoad(5, By.xpath(addProduct));
		seleniumUtil.click(By.xpath(addProduct));
		
		DialogPanel.addProductByName(seleniumUtil, "purchaseForm", goodsName);
		logger.info("添加商品Success");
		seleniumUtil.inFrame("contentIFRAMEtab_采购入库单");
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//修改商品采购单位
		if (StringUtils.isBlank(goodsUnit) == false) {
			logger.info("修改商品采购单位为：" + goodsUnit);
			By xpathOfUnit = By.xpath("//*[@id='tableDocumentDetail']/tbody/tr[" + num + "]/td[9]/a");
			By selectUnit = By.xpath("//*[@id='tableDocumentDetail']/tbody/tr[" + num + "]/td[9]/span/div/form/div/div[1]/div/select");
			seleniumUtil.waitForElementToLoad(5, xpathOfUnit);
			seleniumUtil.click(xpathOfUnit);
			seleniumUtil.waitForElementToLoad(5, selectUnit);
			seleniumUtil.selectByText(selectUnit, goodsUnit);
		} else {
			//商品采购单位为空，不作操作，选择默认采购单位
		}
		
		//添加商品数量
		logger.info("商品数量改为：" + goodsNum);
		By xpathOfProductNum = By.xpath("//*[@id='tableDocumentDetail']/tbody/tr[" + num + "]/td[10]/a");
		By xpathOfProductNumInput = By.xpath("//*[@id='tableDocumentDetail']/tbody/tr[" + num + "]/td[10]/span/div/form/div/div[1]/div/input");
		seleniumUtil.click(xpathOfProductNum);
		seleniumUtil.waitForElementToLoad(2, xpathOfProductNumInput);
		seleniumUtil.type(xpathOfProductNumInput, goodsNum);
		seleniumUtil.click(By.xpath("//*[@id='tableDocumentDetail']/tbody/tr[1]/td[1]"));
	}
	/**
	 * 记账并点击“保存”.
	 * @param seleniumUtil
	 */
	public static void submit(SeleniumUtil seleniumUtil) {
		logger.info("采购入库单记账");
		seleniumUtil.submit(Document_Purchase.TPIW_BUTTON_SUBMIT, "4");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 保存草稿.
	 * @param seleniumUtil
	 */
	public static void draft(SeleniumUtil seleniumUtil) {
		logger.info("采购入库单保存草稿");
		seleniumUtil.click(Document_Purchase.TPIW_BUTTON_DRAFT);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 快速填写采购入库单.
	 * @param seleniumUtil
	 * @param warehouse  仓库.
	 * @param handlerUser  经手人.
	 * @param goodsName  商品名.
	 * @param goodsNum  商品数量.
	 * @param goodsUnit  商品单位.
	 * @return 单据内容(第一条商品).
	 * @throws InterruptedException
	 */
	public static Map<String , String> typeOrderQuickly(SeleniumUtil seleniumUtil , String warehouse , String handlerUser ,
			String goodsName) throws InterruptedException {
		//跳转到采购入库单页面
		JumpInto.jumpInto(seleniumUtil , "采购", "采购入库单");
		//选择仓库，经手人
		selectParams(seleniumUtil , "" , warehouse , handlerUser , "" , "");
		//添加商品(商品数量为1，单位为默认采购单位)
		addProduct(seleniumUtil , goodsName , "1" , "" , "1");
		//获取添加的商品数据
		Map<String , String> map = new HashMap<String , String>();
		PageHelperUtil.recordData(seleniumUtil, map, Document_Purchase.TPIW_TABLE_DATA, 3, 14);
		//记账保存
		submit(seleniumUtil);
		return map;
	}
	
	public static Map<String, String> typeOrderQuickly(SeleniumUtil seleniumUtil , String warehouse , String handlerUser ,
			String goodsName , String goodsNum , String goodsUnit) throws InterruptedException {
		//跳转到采购入库单页面
		JumpInto.jumpInto(seleniumUtil , "采购", "采购入库单");
		//选择仓库，经手人
		selectParams(seleniumUtil , "" , warehouse , handlerUser , "" , "");
		//添加商品,选择商品单位、数量
		addProduct(seleniumUtil , goodsName , goodsNum , goodsUnit , "1");
		//获取添加的商品数据
		Map<String , String> map = new HashMap<String , String>();
		PageHelperUtil.recordData(seleniumUtil, map, Document_Purchase.TPIW_TABLE_DATA, 3, 14);
		//记账保存
		submit(seleniumUtil);
		return map;
	}
	/**
	 * 对比采购入库单填写、订单生成入库单&采购入库单详情，后续应统一 两页面表头thead，不用switch语句  2018-07-16 17:48
	 * @param map_1
	 * @param map_2
	 */
	public static void compareMap(Map<String , String> map_1, Map<String , String> map_2) {
		Iterator<String> iter1 = map_1.keySet().iterator();
		//遍历map_1集合
		while (iter1.hasNext()) {
			String map_1_key = (String) iter1.next();
			String map_2_key = map_1_key;
			//map_1&map_2的key值，有个别表示相同内容的表头，取名不一致，因此增加判断
			switch (map_1_key) {
			case "主计量单位" :
				map_2_key = "基本单位单位";
				break;
			case "主计量数量" :
				map_2_key = "基本单位数量";
				break;
			case "成本总额(元)" :
				map_2_key = "成本总额";
				break;
			}
			//根据表头取表格内容的值
			String map_1_value = map_1.get(map_1_key);
			String map_2_value = map_2.get(map_2_key);
			
			PageHelperUtil.compareMap(map_1_key, map_2_key, map_1_value, map_2_value);
		} //遍历结束 while
		logger.info("数据对比成功！");
	}
}