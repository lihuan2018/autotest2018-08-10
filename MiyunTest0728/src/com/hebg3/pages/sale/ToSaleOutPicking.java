package com.hebg3.pages.sale;

import org.openqa.selenium.By;


/**
 * 生成拣货单.
 * @author Administrator
 *
 */
public class ToSaleOutPicking {
	/**单号*/
	public static final By TSOP_INPUT_ORDERNO = By.id("orderno");
	/**结算状态*/
	public static final By TSOP_SELECT_ACCOUNT = By.id("accountStatus");
	/**单据日期——起始日期*/
	public static final By TSOP_INPUT_STARTDATE = By.id("startOrderDate");
	/**单据日期——结束日期*/
	public static final By TSOP_INPUT_FINISHDATE = By.id("endOrderDate");
	/**仓库*/
	public static final By TSOP_SELECT_WAREHOUSE = By.id("warehouse");
	/**配送人*/
	public static final By TSOP_SELECT_USER = By.id("dispatchingUser");
	/**查询按钮*/
	public static final By TSOP_BUTTON_SEARCH = By.xpath("//*[@id='toolbar']/div[2]/button[1]");
	/**拣货按钮*/
	public static final By TSOP_BUTTON_PICKING = By.id("pickingPrint");
	/**忽略按钮*/
	public static final By TSOP_BUTTON_IGNORE = By.id("ignore");
	/**复选框(全选)*/
	public static final By TSOP_INPUT_SELECTALL = By.name("btSelectAll");
	/**数据表单tableId*/
	public static final String TSOP_TABLE_DATA = "saleHistoryTable";
}
