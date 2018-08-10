package com.hebg3.pages.purchase;

import org.openqa.selenium.By;

/**采购入库单查询*/
public class ToSearchList_Purchase {
	/**单号*/
	public static final By TPSLP_INPUT_ORDERNO = By.id("orderno");
	/**移入仓库*/
	public static final By TPSLP_SELECT_INWAREHOUSE = By.id("inWarehouse");
	/**供应商*/
	public static final By TPSLP_SELECT_SUPPLIER = By.id("supplierId");
	/**经手人*/
	public static final By TPSLP_SELECT_HANDLERUSER = By.id("handlerUserId");
	/**单据日期——起始日期*/
	public static final By TPSLP_INPUT_STARTDATE = By.id("startCreatDate");
	/**单据日期——结束日期*/
	public static final By TPSLP_INPUT_FINISHDATE = By.id("finishCreatDate");
	/**单据状态*/
	public static final By TPSLP_SELECT_STATUS = By.id("documentStatusId");
	/**结账状态*/
	public static final By TPSLP_SELECT_ACCOUNTS = By.id("settleAccountsId");
	/**摘要*/
	public static final By TPSLP_INPUT_REMARKS = By.id("remarks");
	/**填单人*/
	public static final By TPSLP_SELECT_CREATEBY = By.id("createById");
	/**查询按钮*/
	public static final By TPSLP_BUTTON_SEARCH = By.xpath("//*[@id='content']/div[1]/div[2]/button[1]");
	/**重置按钮*/
	
	/**数据表单tableId*/
	public static final String TPSLP_TABLE_DATA = "purchaseInWarehouseHistoryTable";
}
