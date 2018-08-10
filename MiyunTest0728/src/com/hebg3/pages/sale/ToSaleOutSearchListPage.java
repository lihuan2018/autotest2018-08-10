package com.hebg3.pages.sale;

import org.openqa.selenium.By;
/**
 * 销售出库单查询.
 * @author Young.
 *
 */
public class ToSaleOutSearchListPage {
	/**单号输入框*/
	public static final By TSOSLP_INPUT_ORDERNUM = By.id("orderno");
	/**结算状态*/
	public static final By TSOSLP_SELECT_ACCOUNTSTATUS = By.id("accountStatus");
	/**单据日期-开始时间*/
	public static final By TSOSLP_INPUT_STARTORDERDATE = By.id("startOrderDate");
	/**单据日期-结束时间*/
	public static final By TSOSLP_INPUT_ENDORDERDATE = By.id("endOrderDate");
	/**客户*/
	public static final By TSOSLP_INPUT_COSTUMERNAME = By.id("customerName");//客户分input与button，暂只考虑input
	/**经手人*/
	public static final By TSOSLP_SELECT_HANDLERUSER = By.id("handlerUser");
	/**单据状态*/
	public static final By TSOSLP_SELECT_DOCUMENTSTATUS = By.id("documentStatus");
	/**仓库*/
	public static final By TSOSLP_SELECT_WAREHOUSE = By.id("warehouse");
	/**查询按钮*/
	public static final By TSOSLP_BUTTON_SEARCH = By.xpath("//*[@id='toolbar']/div[2]/button[1]");
	/**数据表单table Id*/
	public static final String TSOSLP_TABLE_DATA = "saleHistoryTable";
	
}