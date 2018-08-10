package com.hebg3.pageshelper.inventory;

import org.openqa.selenium.By;

import com.hebg3.pages.inventory.ToWarehouseMoveDocumentFormPage;
import com.hebg3.utils.SeleniumUtil;
import com.hebg3.utils.pagehelper.PageHelperUtil;

public class ToWarehouseMoveDocumentFormPageHelper {
	/**
	 * 填写移库单并记账.
	 * @param seleniumUtil  SeleniumUtil的实例化对象，填入seleniumUtil即可.
	 * @param goodName  商品名称.
	 * @param outWarehouse  移出仓库.
	 * @param inWarehouse  移入仓库.
	 */
	public static void moveGoodTo(SeleniumUtil seleniumUtil , String goodName , String outWarehouse , String inWarehouse) {
		ToWarehouseMoveDocumentFormPage tw = new ToWarehouseMoveDocumentFormPage();
		seleniumUtil.selectByText(tw.TWMDFP_SELECT_OUTWAREHOUSE, outWarehouse);
		seleniumUtil.selectByText(tw.TWMDFP_SELECT_INWAREHOUSE, inWarehouse);
		
		seleniumUtil.click(tw.TWMDFP_BUTTOM_SAVE);
	}
	/**
	 * 填写移库单并保存草稿.
	 * @param seleniumUtil  SeleniumUtil的实例化对象，填入seleniumUtil即可.
	 * @param goodName  商品名称.
	 * @param outWarehouse  移出仓库.
	 * @param inWarehouse  移入仓库.
	 */
	public static void draftForm(SeleniumUtil seleniumUtil , String goodName , String outWarehouse , String inWarehouse) {
		ToWarehouseMoveDocumentFormPage tw = new ToWarehouseMoveDocumentFormPage();
		seleniumUtil.selectByText(tw.TWMDFP_SELECT_OUTWAREHOUSE, outWarehouse);
		seleniumUtil.selectByText(tw.TWMDFP_SELECT_INWAREHOUSE, inWarehouse);
		
		seleniumUtil.click(tw.TWMDFP_BUTTON_DRAFT);
	}
}
