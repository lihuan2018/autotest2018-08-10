package com.hebg3.pageshelper.purchase;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;

import com.hebg3.pages.purchase.ToSearchList_Purchase;
import com.hebg3.pages.purchase.Detail_Purchase;
import com.hebg3.utils.SeleniumUtil;
import com.hebg3.utils.pagehelper.JumpInto;
import com.hebg3.utils.pagehelper.PageHelperUtil;

public class ToSearchList_PurchaseHelper {
	/**
	 * 查询第一条采购入库单详情(目前仅支持已记账状态，红冲状态会报错)，返回表头和第一条商品数据.
	 * @param seleniumUtil
	 * @return单据内容(第一条商品).
	 * @throws InterruptedException
	 * @author Young 2018-07-17 11:35
	 */
	public static Map<String , String> viewDetail(SeleniumUtil seleniumUtil) throws InterruptedException {
		//跳转到采购入库单查询页面
		JumpInto.jumpInto(seleniumUtil, "采购", "采购入库单查询");
		//点击查询按钮
		seleniumUtil.waitForElementToLoad(5, ToSearchList_Purchase.TPSLP_BUTTON_SEARCH);
		seleniumUtil.click(ToSearchList_Purchase.TPSLP_BUTTON_SEARCH);
		//点击第一条采购入库单
		seleniumUtil.waitForElementToLoad(5, PageHelperUtil.tableCell(seleniumUtil, "采购入库单查询", ToSearchList_Purchase.TPSLP_TABLE_DATA, 1, 2));
		seleniumUtil.click(PageHelperUtil.tableCell(seleniumUtil, "采购入库单查询", ToSearchList_Purchase.TPSLP_TABLE_DATA, 1, 2));
		
		//跳转到采购入库单_已记账页面
		seleniumUtil.outFrame();
		seleniumUtil.waitForElementToLoad(5, By.id("tab_tab_采购入库单_已记账"));
		seleniumUtil.click(By.id("tab_tab_采购入库单_已记账"));
		seleniumUtil.inFrame("contentIFRAMEtab_采购入库单_已记账");
		
		//等待页面加载，获取采购入库单详情数据
		seleniumUtil.waitForPageLoading(5);
		Map<String , String> map = new HashMap<String , String>();
		PageHelperUtil.recordData(seleniumUtil, map, Detail_Purchase.TPDP_TABLE_DATA, 2, 13);
		
		return map;
	}
}
