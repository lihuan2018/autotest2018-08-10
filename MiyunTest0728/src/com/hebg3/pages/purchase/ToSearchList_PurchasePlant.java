package com.hebg3.pages.purchase;

import org.openqa.selenium.By;

/**
 * 采购订单查询.
 * @author Administrator
 *
 */
public class ToSearchList_PurchasePlant {
	/**单号*/
	public static final By TPPP_INPUT_ORDERNO = By.id("orderno");
	/**填单人*/
	public static final By TPPP_SELECT_CREATBY = By.id("creatById");
	/**供应商*/
	public static final By TPPP_SELECT_SUPPLIER = By.id("supplierId");
	/**单据日期——起始日期*/
	public static final By TPPP_INPUT_STATRCREATDATE = By.id("startDate");
	/**单据日期——结束日期*/
	public static final By TPPP_INPUT_FINISHCREATEDATE = By.id("endDate");
	/**摘要*/
	public static final By TPPP_INPUT_REMARKS = By.id("remarks");
	/**单据状态*/
	public static final By TPPP_SELECT_DOCUMENTFLAG = By.id("documentFlag");
	/**查询*/
	public static final By TPPP_BUTTON_SEARCH = By.xpath("//*[@id='content']/div[1]/div[2]/button[1]");
	/**重置*/
	public static final By TPPP_BUTTON_RESET = By.xpath("//*[@id='content']/div[1]/div[2]/button[2]");
	/**导出*/
	public static final By TPPP_BUTTON_EXPORT = By.id("exportBtn");
	/**数据表单tableId*/
	public static final String TPPP_TABLE_DATA = "purchaseInWarehouseHistoryTable";
}
