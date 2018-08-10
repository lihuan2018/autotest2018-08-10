package com.hebg3.pageshelper.purchase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

import com.hebg3.pages.purchase.Document_PurchasePlant;
import com.hebg3.utils.SeleniumUtil;
import com.hebg3.utils.pagehelper.DialogPanel;
import com.hebg3.utils.pagehelper.JumpInto;
import com.hebg3.utils.pagehelper.PageHelperUtil;

/**采购订单*/
public class Document_PurchasePlantHelper {
	public static Logger logger = LogManager.getLogger(Document_PurchasePlantHelper.class.getName());
	
	/**
	 * 填写采购订单的参数.
	 * @param seleniumUtil
	 * @param supplier  供应商.
	 * @param orderDate  单据日期(建议写为YYYY-MM-DD格式).
	 * @param remarks  摘要内容.
	 */
	public static void selectParams(SeleniumUtil seleniumUtil , String supplier , String orderDate , String remarks) {
		if (StringUtils.isBlank(supplier) == false) {
			PageHelperUtil.selectByVisibleText(seleniumUtil, Document_PurchasePlant.TPP_SELECT_SUPPLIER, "supplierId", supplier);
		}
		if (StringUtils.isBlank(orderDate) == false) {
			PageHelperUtil.chooseDate(seleniumUtil, Document_PurchasePlant.TPP_DATA_ORDERDATE, "orderDate", orderDate);
		}
		if (StringUtils.isBlank(remarks) == false) {
			seleniumUtil.waitForElementToLoad(5, Document_PurchasePlant.TPP_INPUT_REMARKS);
			seleniumUtil.type(Document_PurchasePlant.TPP_INPUT_REMARKS, remarks);
		}
	}
	/**
	 * 添加商品.
	 * @param seleniumUtil
	 * @param goodsName 商品名称.
	 * @param goodsNum  商品数量.
	 * @param goodsUnit  采购单位.
	 * @param num  商品序号(要添加的第几个商品).
	 */
	public static void addProduct(SeleniumUtil seleniumUtil , String goodsName , String goodsNum , 
			String goodsUnit , String num) {
		//判断如果goodsNum >= 9，点击上一行的加号
		int number = Integer.valueOf(num);
		if (Integer.valueOf(num) >= 9) {
			seleniumUtil.findElementBy(By.xpath("//*[@id='tableDocumentDetail']/tbody/tr[" + String.valueOf(number - 1) + "]/td[2]/a[1]"));
		}
		
		//点击添加商品按钮(放大镜)
		String addProduct = "//*[@id='tableDocumentDetail']/tbody/tr[" + num + "]/td[3]/a/div/span[2]";
		seleniumUtil.waitForElementToLoad(5, By.xpath(addProduct));
		seleniumUtil.click(By.xpath(addProduct));
		
		DialogPanel.addProductByName(seleniumUtil, "purchaseForm", goodsName);
		seleniumUtil.inFrame("contentIFRAMEtab_采购订单");
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//修改商品采购单位
		if (StringUtils.isBlank(goodsUnit) == false) {
			By xpathOfUnit = By.xpath("//*[@id='tableDocumentDetail']/tbody/tr[" + num + "]/td[7]/a");
			By selectUnit = By.xpath("//*[@id='tableDocumentDetail']/tbody/tr[" + num + "]/td[7]/span/div/form/div/div[1]/div/select");
			seleniumUtil.waitForElementToLoad(5, xpathOfUnit);
			seleniumUtil.click(xpathOfUnit);
			seleniumUtil.waitForElementToLoad(5, selectUnit);
			seleniumUtil.selectByText(selectUnit, goodsUnit);
		} else {
			//商品采购单位为空，不作操作，选择默认采购单位
		}
		
		//添加商品数量
		By xpathOfProductNum = By.xpath("//*[@id='tableDocumentDetail']/tbody/tr[" + num + "]/td[8]/a");
		By xpathOfProductNumInput = By.xpath("//*[@id='tableDocumentDetail']/tbody/tr[" + num + "]/td[8]/span/div/form/div/div[1]/div/input");
		seleniumUtil.click(xpathOfProductNum);
		seleniumUtil.waitForElementToLoad(2, xpathOfProductNumInput);
		seleniumUtil.type(xpathOfProductNumInput, goodsNum);
		seleniumUtil.click(By.xpath("//*[@id='tableDocumentDetail']/tbody/tr[1]/td[1]"));
	}
	/**
	 * 保存订单.
	 * @param seleniumUtil
	 */
	public static void submit(SeleniumUtil seleniumUtil) {
		seleniumUtil.submit(Document_PurchasePlant.TPP_BUTTON_SAVE, "2");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 快速填写采购订单.
	 * @param seleniumUtil
	 * @param goodsName  采购商品名.
	 * @param goodsNum  商品数量.
	 * @param goodsUnit  商品采购单位.
	 * @return 单据内容(第一条商品).
	 * @throws InterruptedException
	 */
	public static Map<String , String> typeOrderQuickly(SeleniumUtil seleniumUtil , String goodsName , String goodsNum , 
			String goodsUnit) throws InterruptedException {
		JumpInto.jumpInto(seleniumUtil , "采购", "采购订单");
		addProduct(seleniumUtil , goodsName , goodsNum , goodsUnit , "1");
		Map<String , String> map = new HashMap<String , String>();
		PageHelperUtil.recordData(seleniumUtil , map , Document_PurchasePlant.TPP_TABLE_DATA , 3 , 11); //2018-07-13 15:30测试  2018-07-17 10:39修改
		submit(seleniumUtil);
		return map;
	}

	//临时添加，对比采购订单填写&采购订单详情，后续应统一 两页面表头thead，不用switch语句  2018-07-16 16:53
	public static void compareMap(SeleniumUtil seleniumUtil , Map<String, String> map_1, Map<String, String> map_2) {
		Iterator<String> iter1 = map_1.keySet().iterator();
		while (iter1.hasNext()) {
			String map_1_key = (String) iter1.next();
			String map_2_key = map_1_key;
			//根据表头取表格内容的值
			switch (map_1_key) {
			case "采购价(元)" :
				map_2_key = "采购价";
				break;
			case "基本计量单位" :
				map_2_key = "基本单位";
				break;
			case "基本计量数量" :
				map_2_key = "基本单位数量";
				break;
			case "采购总额(元)" :
				map_2_key = "总价";
				break;
			}
			String map_1_value = map_1.get(map_1_key);
			String map_2_value = map_2.get(map_2_key);
			//System.out.println(map_1_key + "----" + map_1.get(map_1_key) + "-----" + map_2.get(map_2_key));
			PageHelperUtil.compareMap(map_1_key, map_2_key, map_1_value, map_2_value);
		}//遍历结束
		logger.info("数据对比成功！");
	}
}

