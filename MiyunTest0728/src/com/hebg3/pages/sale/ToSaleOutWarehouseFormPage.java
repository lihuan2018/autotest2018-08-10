package com.hebg3.pages.sale;

import org.openqa.selenium.By;

/**
 * 销售出库单.
 * @author Young.
 *
 */
public class ToSaleOutWarehouseFormPage {
	/**客户*/
	public static final By TSOWFP_INPUT_COSTUMERNAME = By.id("customerName");
	public static final By TSOWEP_BUTTON_COSTUMERNAME = By.xpath("//*[@id='searchForm']/div[1]/section[1]/div/label[2]/span/span");
	/**经手人*/
	public static final By TSOWFP_SELECT_HANDLERUSER = By.id("handlerUser");
	/**送货日期*/
	public static final By TSOWFP_INPUT_DEALDATE = By.id("dealdate");
	/**填单日期*/
	public static final By TSOWFP_INPUT_ORDERDATE = By.id("orderdate");
	/**摘要*/
	public static final By TSOWFP_INPUT_REMARK = By.id("remark");
	/**草稿*/
	public static final By TSOWFP_BUTTON_DRAFT = By.xpath("//*[@id='toolbar']/button[1]");
	/**记账*/
	public static final By TSOWFP_BUTTON_SUBMIT = By.xpath("//*[@id='toolbar']/button[2]");
	/**历史*/
	public static final By TSOWFP_BUTTON_HISTORY = By.xpath("//*[@id='toolbar']/button[3]");
	//*[@id="salesTable"]/tbody/tr[1]/td[3]/span/div/form/div/div[1]/div/span[1]/input[2]
}
