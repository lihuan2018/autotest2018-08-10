package com.hebg3.pages.purchase;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
/**
* @description 公用元素定位声明,在JumpPageHelper.jumpInto(seleniumUtil,by,by1）中使用时，变量"HP_..."与"HP_...1"同时使用，其他地方不实用"HP_...1".
* @author young.
*/
public class HomePage {
	//*[@id="left-panel"]/nav/ul/li[2]/ul/li/div[1]/ul/li[3]/a
	/**
	* @param HP_TEXT_USERNAME 用户名显示.
	* @param HP_MENU_PURCHASE 采购模块.
	* @param HP_CG_LINK_tPurchaseDocumentAllHistoryPage 采购入库单查询.
	* @param HP_MENU_SALES 销售模块.
	* @param HP_XS_LINK_toSaleOutSearchListPage 销售出库单查询.
	*/
	/**用户名显示*/
	public static final By HP_TEXT_USERNAME = By.xpath("//*/div[3]/ul[6]/li/a/span");
	/**采购*/
	public static final By HP_MENU_PURCHASE = By.xpath("//*/nav/ul/li[1]/a");
	/**采购入库单查询*/
	public static final By HP_CG_LINK_tPurchaseDocumentAllHistoryPage = By.xpath("//*[@id='left-panel']/nav/ul/li[1]/ul/li/div[1]/ul/li[5]/a");
	public static final By HP_CG_LINK_tPurchaseDocumentAllHistoryPage1 = By.id("tab_tab_采购入库单查询");
	/**销售*/
	public static final By HP_MENU_SALES = By.xpath("//*/nav/ul/li[2]/a");
	/**销售出库单*/
	public static final By HP_XS_LINK_toSaleOutWarehouseFormPage = By.xpath("//*/nav/ul/li[2]/ul/li/div[1]/ul/li[1]/a");
	/**销售出库单查询*/
	public static final By HP_XS_LINK_toSaleOutSearchListPage = By.xpath("//*/nav/ul/li[2]/ul/li/div[1]/ul/li[3]/a");
	public static final By HP_XS_LINK_toSaleOutSearchListPage1 = By.id("tab_tab_销售出库单查询");
	
	public static String toPageName(By by) {
		Map<By,String> map = new HashMap<By,String>();
		map.put(HP_CG_LINK_tPurchaseDocumentAllHistoryPage, "HP_CG_LINK_tPurchaseDocumentAllHistoryPage");
		map.put(HP_XS_LINK_toSaleOutSearchListPage, "HP_XS_LINK_toSaleOutSearchListPage");
		map.put(HP_XS_LINK_toSaleOutWarehouseFormPage, "HP_XS_LINK_toSaleOutWarehouseFormPage");
		
		
		Object value = map.get(by);
		String ret = value.toString();
		return ret;
	}
	
}