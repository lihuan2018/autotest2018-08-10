package com.hebg3.pages.purchase;

import org.openqa.selenium.By;

/**
* 采购订单.
* @author Young.
* 
*/
public class Document_PurchasePlant{
	/**供应商*/
	public static final By TPP_SELECT_SUPPLIER = By.id("supplierId");
	/**单据日期*/
	public static final By TPP_DATA_ORDERDATE = By.id("orderDate");
	/**摘要*/
	public static final By TPP_INPUT_REMARKS = By.id("remarks");
	/**保存*/
	public static final By TPP_BUTTON_SAVE = By.id("save");
	/**数据表单tableId*/
	public static final String TPP_TABLE_DATA = "tableDocumentDetail";
}