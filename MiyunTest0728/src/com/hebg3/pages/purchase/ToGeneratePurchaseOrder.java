package com.hebg3.pages.purchase;

import org.openqa.selenium.By;

/**生成采购入库单页面*/
public class ToGeneratePurchaseOrder {
	/**收货仓库*/
	public static final By TGPO_SELECT_WAREHOUSE = By.id("warehouseId");  //隐藏的Select元素
	/**经手人*/
	public static final By TGPO_SELECT_HANDLERUSER = By.id("handlerUserId");
	/**记账 按钮*/
	public static final By TGPO_BUTTON_SUBMIT = By.id("save");
}
