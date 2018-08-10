package com.hebg3.pages.sale;

import org.openqa.selenium.By;


/**
 * 销售退货单.
 * @author Administrator
 *
 */
public class ToSaleOutReturnPage {
	/**选择客户按钮*/
	public static final By TSORWFP_BUTTON_CUSTOMERNAME = By.xpath("//*[@id='searchForm']/div[1]/section[1]/div/label[2]/span/span");
	/**仓库*/
	public static final By TSORWFP_SELECT_WAREHOUSE = By.id("warehouse");
	/**经手人*/
	public static final By TSORWFP_SELECT_HANDLERUSER = By.id("handlerUser");
	/**收货日期*/
	public static final By TSORWFP_INPUT_DEALDATE = By.id("dealdate");
	/**填单日期*/
	public static final By TSORWFP_INPUT_ORDERDATE = By.id("orderdate");
	/**摘要*/
	public static final By TSORWFP_INPUT_REMARK = By.id("remark");
	/**草稿*/
	public static final By TSORWFP_BUTTON_DRAFT = By.xpath("//*[@id='toolbar']/button[1]");
	/**记账*/
	public static final By TSORWFP_BUTTON_SUBMIT = By.xpath("//*[@id='toolbar']/button[2]");
	/**历史*/
	public static final By TSORWFP_BUTTON_HISTORY = By.xpath("//*[@id='toolbar']/button[3]");
	/**数据表单tableId*/
	public static final String TSORWFP_TABLE_DATA = "salesTable";
}
