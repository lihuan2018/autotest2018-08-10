package com.hebg3.pages.purchase;

import org.openqa.selenium.By;

/**
* 采购入库单.
* @author Young.
* 
*/
public class Document_Purchase {
	/**供应商*/
	public static final By TPIW_SELECT_SUPPLIER = By.id("supplierId");
	/**收货仓库*/
	public static final By TPIW_SELECT_WAREHOUSE = By.id("warehouseId");
	/**经手人*/
	public static final By TPIW_SELECT_HANDLERUSER = By.id("handlerUserId");
	/**单据日期*/
	public static final By TPIW_INPUT_ORDERDATE = By.id("orderDate");
	/**摘要*/
	public static final By TPIW_INPUT_REMARKS = By.id("remarks");
	/**存草稿*/
	public static final By TPIW_BUTTON_DRAFT = By.id("draft");
	/**记账*/
	public static final By TPIW_BUTTON_SUBMIT = By.id("save");
	/**历史*/
	public static final By TPIW_BUTTON_HISTORY = By.id("history");
	/**数据表单的tableId*/
	public static final String TPIW_TABLE_DATA = "tableDocumentDetail";
}