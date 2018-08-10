package com.hebg3.pageshelper.sale;

import com.hebg3.pages.sale.ToSaleOutSearchListPage;
import com.hebg3.utils.SeleniumUtil;
import com.hebg3.utils.pagehelper.PageHelperUtil;

public class ToSaleOutSearchListPageHelper {
	/**
	 * 按单号查询操作，单号可为空，表示直接查询.
	 * @param seleniumUtil  SeleniumUtil的实例化对象，填入seleniumUtil即可.
	 * @param orderNum  销售出库单单号.
	 */
	public static void searchDataByOrderNum(SeleniumUtil seleniumUtil , String orderNum) {
		//输入单号,点击查询
		seleniumUtil.findElementBy(ToSaleOutSearchListPage.TSOSLP_INPUT_ORDERNUM).clear();
		seleniumUtil.findElementBy(ToSaleOutSearchListPage.TSOSLP_INPUT_ORDERNUM).sendKeys(orderNum);
		seleniumUtil.click(ToSaleOutSearchListPage.TSOSLP_BUTTON_SEARCH);
	}
	/**
	 * 按单号查询操作，并提取相应数据，单号可为空，表示直接查询.
	 * @param seleniumUtil  SeleniumUtil的实例化对象，填入seleniumUtil即可.
	 * @param orderNum  销售出库单单号.
	 * @param row  指定数据位于第几行.
	 * @param column  指定数据位于第几列.
	 * @return  数据表单中指定位置的数据.
	 * @throws InterruptedException 
	 */
	public static String searchDataByOrderNum(SeleniumUtil seleniumUtil , String orderNum , int row , int column) throws InterruptedException {
		//查询.
		seleniumUtil.findElementBy(ToSaleOutSearchListPage.TSOSLP_INPUT_ORDERNUM).clear();
		seleniumUtil.findElementBy(ToSaleOutSearchListPage.TSOSLP_INPUT_ORDERNUM).sendKeys(orderNum);
		seleniumUtil.click(ToSaleOutSearchListPage.TSOSLP_BUTTON_SEARCH);
		Thread.sleep(2000);
		//提取相应数据.
		String data = PageHelperUtil.getTableCell(seleniumUtil, "销售出库单查询", ToSaleOutSearchListPage.TSOSLP_TABLE_DATA, row, column);
		return data;
	}
	//复合查询方法
	/**
	 * 封装了常用查询条件的复合查询方法，不选择的查询条件输入空字符串"".
	 * @param seleniumUtil
	 * @param orderNum  单号(必填).
	 * @param accountStatus  结算状态.
	 * @param startOrderDate  单据日期-开始时间,建议填写格式为"YYYY-MM-DD",可填写为"YYYY/MM/DD"或"YYYYMMDD".
	 * @param endOrderDate  单据日期-结束时间,建议填写格式为"YYYY-MM-DD",可填写为"YYYY/MM/DD"或"YYYYMMDD".
	 * @param customerName  客户.
	 * @param handlerUser  经手人.
	 * @param documentStatus  单据状态.
	 * @param warehouse  仓库.
	 */
	public void searchData(SeleniumUtil seleniumUtil , String orderNum , String accountStatus , String startOrderDate , String endOrderDate , String customerName , String handlerUser , String documentStatus , String warehouse) {
		//清空并输入单号.
		seleniumUtil.findElementBy(ToSaleOutSearchListPage.TSOSLP_INPUT_ORDERNUM).clear();
		seleniumUtil.findElementBy(ToSaleOutSearchListPage.TSOSLP_INPUT_ORDERNUM).sendKeys(orderNum);
		//其他查询条件选择.
		if (accountStatus != null){
			seleniumUtil.selectByText(ToSaleOutSearchListPage.TSOSLP_SELECT_ACCOUNTSTATUS, accountStatus);
		} else {  };
		if (startOrderDate != null){
			seleniumUtil.executeJS("document.getElementById('startOrderDate').removeAttribute('readonly')");
			seleniumUtil.findElementBy(ToSaleOutSearchListPage.TSOSLP_INPUT_STARTORDERDATE).clear();
			seleniumUtil.findElementBy(ToSaleOutSearchListPage.TSOSLP_INPUT_STARTORDERDATE).sendKeys(startOrderDate);
		} else {  };
		if (endOrderDate != null){
			seleniumUtil.executeJS("document.getElementById('endOrderDate').removeAttribute('readonly')");
			seleniumUtil.findElementBy(ToSaleOutSearchListPage.TSOSLP_INPUT_ENDORDERDATE).clear();
			seleniumUtil.findElementBy(ToSaleOutSearchListPage.TSOSLP_INPUT_ENDORDERDATE).sendKeys(endOrderDate);
		} else {  };
		if (customerName != null){
			seleniumUtil.findElementBy(ToSaleOutSearchListPage.TSOSLP_INPUT_COSTUMERNAME).clear();
			seleniumUtil.findElementBy(ToSaleOutSearchListPage.TSOSLP_INPUT_COSTUMERNAME).sendKeys(customerName);;
		} else {  };
		if (handlerUser != null){
			seleniumUtil.selectByText(ToSaleOutSearchListPage.TSOSLP_SELECT_HANDLERUSER, handlerUser);
		} else {  };
		if (documentStatus != null){
			seleniumUtil.selectByText(ToSaleOutSearchListPage.TSOSLP_SELECT_DOCUMENTSTATUS, documentStatus);
		} else {  };
		if (warehouse != null){
			seleniumUtil.selectByText(ToSaleOutSearchListPage.TSOSLP_SELECT_WAREHOUSE, warehouse);
		} else {  };
		//查询方法输入完毕后，点击查询
		seleniumUtil.click(ToSaleOutSearchListPage.TSOSLP_BUTTON_SEARCH);
	}
}
