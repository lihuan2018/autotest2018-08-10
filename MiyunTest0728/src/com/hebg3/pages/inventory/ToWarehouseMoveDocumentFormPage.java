package com.hebg3.pages.inventory;

import org.openqa.selenium.By;
/**移库单*/
public class ToWarehouseMoveDocumentFormPage {
	/**移出仓库*/
	public static final By TWMDFP_SELECT_OUTWAREHOUSE = By.xpath("//*[@id='documentForm']/div/section[1]/div/label[2]/span/span[1]/span");  //再次添加一个/span
	/**移入仓库*/
	public static final By TWMDFP_SELECT_INWAREHOUSE = By.xpath("//*[@id='documentForm']/div/section[2]/div/label[2]/span/span[1]/span");
	/**经手人*/
	public static final By TWMDFP_SELECT_HANDLERUSER = By.xpath("//*[@id='documentForm']/div/section[3]/div/label[2]/span/span[1]/span");
	/**存草稿*/
	public static final By TWMDFP_BUTTON_DRAFT = By.id("draft");
	/**记账*/
	public static final By TWMDFP_BUTTOM_SAVE = By.id("save");
	/**历史*/
	public static final By TWMDFP_BUTTON_HISTORY = By.id("history");
	/**数据表单*/
	public static final String TWMDFP_TABLE_DATA = "tableDocumentDetail";
}
