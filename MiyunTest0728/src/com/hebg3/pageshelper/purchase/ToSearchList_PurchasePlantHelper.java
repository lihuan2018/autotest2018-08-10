package com.hebg3.pageshelper.purchase;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;

import com.hebg3.pages.purchase.ToGeneratePurchaseOrder;
import com.hebg3.pages.purchase.Detail_PurchasePlant;
import com.hebg3.pages.purchase.ToSearchList_PurchasePlant;
import com.hebg3.utils.SeleniumUtil;
import com.hebg3.utils.pagehelper.JumpInto;
import com.hebg3.utils.pagehelper.PageHelperUtil;

public class ToSearchList_PurchasePlantHelper {
	/**
	 * 采购订单查询.
	 * @param seleniumUtil
	 * @param orderNo  单号.
	 * @param creatBy  填单人.
	 * @param supplier  供应商.
	 * @param startDate  单据日期——起始日期(YYYY-MM-DD格式).
	 * @param finishDate  单据日期——结束日期(YYYY-MM-DD格式).
	 * @param remarks  摘要.
	 * @param documentFlag  单据状态.
	 */
	public static void search(SeleniumUtil seleniumUtil , String orderNo , String creatBy , 
			String supplier , String startDate , String finishDate , String remarks , String documentFlag) {
		if (StringUtils.isBlank(orderNo) == false) {
			seleniumUtil.waitForElementToLoad(5, ToSearchList_PurchasePlant.TPPP_INPUT_ORDERNO);
			seleniumUtil.type(ToSearchList_PurchasePlant.TPPP_INPUT_ORDERNO, orderNo);
		}
		if (StringUtils.isBlank(creatBy) == false) {
			PageHelperUtil.selectByVisibleText(seleniumUtil, ToSearchList_PurchasePlant.TPPP_SELECT_CREATBY, "creatById", creatBy);
		}
		if (StringUtils.isBlank(supplier) == false) {
			PageHelperUtil.selectByVisibleText(seleniumUtil, ToSearchList_PurchasePlant.TPPP_SELECT_SUPPLIER, "supplierId", supplier);
		}
		if (StringUtils.isBlank(startDate) == false) {
			PageHelperUtil.chooseDate(seleniumUtil, ToSearchList_PurchasePlant.TPPP_INPUT_STATRCREATDATE, "startDate", startDate);
		}
		if (StringUtils.isBlank(finishDate) == false) {
			PageHelperUtil.chooseDate(seleniumUtil, ToSearchList_PurchasePlant.TPPP_INPUT_FINISHCREATEDATE, "endDate", finishDate);
		}
		if (StringUtils.isBlank(finishDate) == false) {
			seleniumUtil.waitForElementToLoad(5, ToSearchList_PurchasePlant.TPPP_INPUT_REMARKS);
			seleniumUtil.type(ToSearchList_PurchasePlant.TPPP_INPUT_REMARKS, remarks);
		}
		if (StringUtils.isBlank(documentFlag) == false) {
			PageHelperUtil.selectByVisibleText(seleniumUtil, ToSearchList_PurchasePlant.TPPP_SELECT_DOCUMENTFLAG, "documentFlag", documentFlag);
		}
		seleniumUtil.click(ToSearchList_PurchasePlant.TPPP_BUTTON_SEARCH);
	}
	/**
	 * 点击采购订单号，查看订单详情(跳转页面).
	 * @param seleniumUtil
	 */
	public static void viewDetail(SeleniumUtil seleniumUtil) {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		seleniumUtil.waitForPageLoading(5);  //2018-07-17 11:02 或可修改为waitForElement  PageHelperUtil.tableCell
		PageHelperUtil.clickTableCell(seleniumUtil, "采购订单查询", ToSearchList_PurchasePlant.TPPP_TABLE_DATA, 1, 2);
	}
	/**
	 * 采购订单_详情页面，点击“生成采购入库单”按钮.
	 * @param seleniumUtil
	 */
	private static void submitOrder(SeleniumUtil seleniumUtil) {
		seleniumUtil.waitForElementToLoad(5, Detail_PurchasePlant.TPPDP_BUTTON_ORDERBTN);
		seleniumUtil.click(Detail_PurchasePlant.TPPDP_BUTTON_ORDERBTN);
	}
	/**
	 * 生成采购入库单页面，点击“记账”，生成采购入库单.
	 * @param seleniumUtil
	 * @param warehouse
	 * @param handlerUser
	 */
	private static void submit(SeleniumUtil seleniumUtil , String warehouse , String handlerUser) {
		PageHelperUtil.selectByVisibleText(seleniumUtil, ToGeneratePurchaseOrder.TGPO_SELECT_WAREHOUSE, "warehouseId", warehouse);
		PageHelperUtil.selectByVisibleText(seleniumUtil, ToGeneratePurchaseOrder.TGPO_SELECT_HANDLERUSER, "handlerUserId", handlerUser);
		seleniumUtil.submit(ToGeneratePurchaseOrder.TGPO_BUTTON_SUBMIT, "2");
	}
	/**
	 * 
	 * 获取采购订单详情(采购订单查询中第一条订单)的数据，包括表头和第一条商品数据.
	 * @param seleniumUtil
	 * @return 获取的订单数据，存入Map并返回.
	 * @throws InterruptedException
	 * @author Administrator Young 2018-07-17 11:05
	 */
	public static Map<String , String> searchOrder(SeleniumUtil seleniumUtil) throws InterruptedException {
		//跳转到采购订单查询页面
		JumpInto.jumpInto(seleniumUtil, "采购", "采购订单查询");
		//点击第一条采购订单
		viewDetail(seleniumUtil);
		
		//进入“采购订单_详情”页面
		seleniumUtil.outFrame();
		seleniumUtil.waitForElementToLoad(5, By.id("tab_tab_采购订单_详情"));
		seleniumUtil.click(By.id("tab_tab_采购订单_详情"));
		seleniumUtil.inFrame("contentIFRAMEtab_采购订单_详情");
		
		
		Map<String , String> map = new HashMap<String , String>();
		//跳转到采购订单详情页面，获取订单数据   2018-07-17 10:38
		seleniumUtil.waitForPageLoading(5);
		PageHelperUtil.recordData(seleniumUtil, map , Detail_PurchasePlant.TPPDP_TABLE_DATA , 2 , 10);
		return map;
	}
	/**
	 * 采购订单生成采购入库单.
	 * @param seleniumUtil
	 * @param warehouse  仓库名称.
	 * @param handlerUser  经手人.
	 * @throws InterruptedException
	 */
	public static void generatePurchaseOrder(SeleniumUtil seleniumUtil , String warehouse , 
			String handlerUser) throws InterruptedException {
		//进入“采购订单_详情”页面
		searchOrder(seleniumUtil);
		//点击“生成采购入库单"
		submitOrder(seleniumUtil);
		
		//进入“生成采购入库单”页面
		seleniumUtil.outFrame();
		seleniumUtil.waitForElementToLoad(5, By.id("tab_tab_生成采购入库单"));
		seleniumUtil.click(By.id("tab_tab_生成采购入库单"));
		seleniumUtil.inFrame("contentIFRAMEtab_生成采购入库单");
		//选择仓库、经手人，生成采购入库单
		submit(seleniumUtil , warehouse , handlerUser);
	}
}
