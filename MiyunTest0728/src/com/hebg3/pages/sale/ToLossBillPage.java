package com.hebg3.pages.sale;

import org.openqa.selenium.By;

/**
 * 报损单.
 *
 */
public class ToLossBillPage {
	/**仓库*/
	public static final By TSOWFP_SELECT_WAREHOUSE = By.id("warehouse");
	//public static final By TSOWFP_SELECT_WAREHOUSE = By.xpath("//*[@id='searchForm']/div/section[1]/div/label[2]/span/span[1]/span");
	//public static final By TSOWFP_SELECT_WAREHOUSE = By.xpath("//*[@id='searchForm']/div/section[1]/div/label[2]/span");
	/**经手人*/
	public static final By TSOWFP_SELECT_HANDLERUSER = By.id("handlerUser");
	/**填单日期*/
	public static final By TSOWFP_INPUT_ORDERDATE = By.id("orderdate");
	/**摘要*/
	public static final By TSOWFP_INPUT_REMARK = By.id("remark");
	/**记账*/
	public static final By TSOWFP_BUTTON_SUBMIT = By.xpath("//*[@id='toolbar']/button[1]");
	/**历史*/
	public static final By TSOWFP_BUTTON_HISTORY = By.xpath("//*[@id='toolbar']/button[2]");

}
