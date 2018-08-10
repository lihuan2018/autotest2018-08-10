package com.hebg3.pages.purchase;

import org.openqa.selenium.By;

/**
 * 采购退货单查询.
 * @author Administrator
 *
 */
public class ToSearchList_PurchaseReturn {
	/**查询*/
	public static final By TSLPR_BUTTON_SEARCH = By.xpath("//*[@id='content']/div[1]/div[2]/button[1]");
	/**数据表单tableId*/
	public static final String TSLPR_TABLE_DATA = "purchaseReturnHistoryTable";
}
