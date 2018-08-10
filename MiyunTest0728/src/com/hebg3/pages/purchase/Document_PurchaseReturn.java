package com.hebg3.pages.purchase;

import org.openqa.selenium.By;

/**
 * 采购退货单.
 * @author Administrator
 *
 */
public class Document_PurchaseReturn {
	/**供应商*/
	public static final By TPR_SELECT_SUPPLIER = By.id("supplierId");
	/**退货仓库*/
	public static final By TPR_SELECT_WAREHOUSE = By.id("warehouseId");
	/**经手人*/
	public static final By TPR_SELECT_HANDLERUSER = By.id("handlerUserId");
	/**单据日期*/
	public static final By TPR_INPUT_ORDERDATE = By.id("orderDate");
	/**摘要*/
	public static final By TPR_INPUT_REMARKS = By.id("remarks");
	/**存草稿*/
	public static final By TPR_BUTTON_DRAFT = By.id("draft");
	/**记账*/
	public static final By TPR_BUTTON_SAVE = By.id("save");
	/**历史*/
	public static final By TPR_BUTTON_HISTORY = By.id("history");
	/**数据表单tableId*/
	public static final String TPR_TABLE_DATA = "tableDocumentReturnDetail";
}
