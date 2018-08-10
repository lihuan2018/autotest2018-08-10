package com.hebg3.pageshelper.inventory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.Assert;

import com.hebg3.pages.inventory.ToSearchInventoryPage;
import com.hebg3.utils.SeleniumUtil;
import com.hebg3.utils.pagehelper.PageHelperUtil;

public class ToSearchInventoryPageHelper {
	
	public static Logger logger = LogManager.getLogger(ToSearchInventoryPageHelper.class.getName());
	
	/**
	 * 根据商品名、仓库名查询库存相关数据(数量、成本价、采购价…).
	 * @param seleniumUtil
	 * @param goodName  商品名称.
	 * @param warehouse  仓库名称.
	 * @param attribute  要查询数据所在的列名.
	 * @return  表单中的值(查询结果).
	 */
	public static String searchInventory(SeleniumUtil seleniumUtil , String goodName , String warehouse , 
			String attribute) {
		//返回值初始化
		String ret = "0";
		//勾选表单中所有显示列
		checkDisplayColumn(seleniumUtil);
		//根据goodName(商品名)、warehouse(仓库名)查询
		seleniumUtil.waitForElementToLoad(6, ToSearchInventoryPage.TSIP_INPUT_GOODNAME);
		seleniumUtil.type(ToSearchInventoryPage.TSIP_INPUT_GOODNAME , goodName);
		PageHelperUtil.selectByVisibleText(seleniumUtil , ToSearchInventoryPage.TSIP_SELECT_WAREHOUSE , "warehouse" , warehouse);
		seleniumUtil.click(ToSearchInventoryPage.TSIP_BUTTON_SEARCH);
		//强制等待+显式等待，防止出现元素失效问题
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		seleniumUtil.waitForElementToLoad(5, By.xpath("//*[@id='" + ToSearchInventoryPage.TSIP_TABLE_DATA +"']/tbody/tr[1]/td[9]"));
		if (attribute.contains("合计") == false) {
			//存储所有属性值(key：列名，value：表格中的值)
			Map<String , String> map = new HashMap<String , String>();
			PageHelperUtil.recordData(seleniumUtil, map , ToSearchInventoryPage.TSIP_TABLE_DATA , 1 , 13);
			//取出要查询的属性值，并赋值给返回值ret
			ret = map.get(attribute);
		} else if (attribute.contains("库存")) {
			ret = seleniumUtil.findElementBy(ToSearchInventoryPage.TSIP_DATA_INVENTORYSUM).getText();
		} else if (attribute.contains("成本")) {
			ret = seleniumUtil.findElementBy(ToSearchInventoryPage.TSIP_DATA_PRICESUM).getText();
		} else {
			logger.error("查询失败：查询的字段不存在，请检查查询字段是否正确！");
			Assert.fail("查询失败：查询的字段不存在，请检查查询字段是否正确！");
		}
		System.out.println(attribute + "---" + ret);
		return ret;
	}
	

	/**
	 * 根据商品名查询成本价总额，直接返回表单中的合计金额.
	 * @param seleniumUtil  SeleniumUtil的实例化对象，填入seleniumUtil即可.
	 * @param goodName  商品名称.
	 * @return  合计栏中的成本价总额.
	 */
	/**
	public static String getCostPrice(SeleniumUtil seleniumUtil , String goodName) {
		seleniumUtil.findElementBy(ToSearchInventoryPage.TSIP_INPUT_GOODNAME).clear();
		seleniumUtil.findElementBy(ToSearchInventoryPage.TSIP_INPUT_GOODNAME).sendKeys(goodName);
		seleniumUtil.click(ToSearchInventoryPage.TSIP_BUTTON_SEARCH);
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		seleniumUtil.waitForElementToLoad(5, By.xpath("//*[@id='sumg'"));
		//获取合计栏的“成本价总额”
		String data0 = seleniumUtil.findElementBy(By.xpath("//*[@id='sumg']")).getText();
		//如果是以“元”结尾，需要去除字符
		String data = data0.substring(0, data0.indexOf('元'));
		return data0;
	}*/
	
	/**
	 * 库存查询页面Bug：进入库存查询页面，前两列默认不显示(偶尔会显示，状态不一定)。
	 * 针对该Bug，做出判断，确保每次在库存查询页操作，表单中所有列均显示，保证查询时循环语句正确。  2018-07-24 17:25
	 * @param seleniumUtil
	 * @author Young
	 */
	private static void checkDisplayColumn(SeleniumUtil seleniumUtil) {
		//等待勾选显示列按钮加载，并点击
		seleniumUtil.waitForElementToLoad(6 , ToSearchInventoryPage.TSIP_BUTTON_DROPDOWNTOGGLE);
		seleniumUtil.click(ToSearchInventoryPage.TSIP_BUTTON_DROPDOWNTOGGLE);
		//判断前两列是否被勾选(显示)
		seleniumUtil.waitForElementToLoad(5, ToSearchInventoryPage.TSIP_CHECKBOX_WAREHOUSENAME);
		boolean isSelectName = seleniumUtil.findElementBy(ToSearchInventoryPage.TSIP_CHECKBOX_WAREHOUSENAME).isSelected();
		boolean isSelectType = seleniumUtil.findElementBy(ToSearchInventoryPage.TSIP_CHECKBOX_WAREHOUSETYPE).isSelected();
		//根据判断结果，勾选所有显示列
		if (isSelectName == false) {
			seleniumUtil.findElementBy(ToSearchInventoryPage.TSIP_CHECKBOX_WAREHOUSENAME).click();
		}
		if (isSelectType == false) {
			seleniumUtil.findElementBy(ToSearchInventoryPage.TSIP_CHECKBOX_WAREHOUSETYPE).click();
		}
	}

}
